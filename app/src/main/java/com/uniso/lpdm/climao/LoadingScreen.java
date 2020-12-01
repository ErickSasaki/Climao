package com.uniso.lpdm.climao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import android.util.Log;

import com.uniso.lpdm.climao.api.RetrofitConfig;
import com.uniso.lpdm.climao.seven_days_weather.SevenDaysWeather;
import com.uniso.lpdm.climao.utils.Storage;
import com.uniso.lpdm.climao.weather.Sys;
import com.uniso.lpdm.climao.weather.WeatherByCity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingScreen extends AppCompatActivity {

    private static int loadingTime = 5000;
    FusedLocationProviderClient fusedLocationProviderClient;

    static {
      AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

    }

    @Override
    protected void onStart() {
        super.onStart();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(LoadingScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(LoadingScreen.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void callNextDaysWeather(Float lat, Float lon) {
        // Configura a request passando o parâmetro da cidade.
        Call<SevenDaysWeather> call = new RetrofitConfig().endpoints().getSevenDaysWeather(lat, lon);

        // Chama a request que foi configurada anteriormente.
        call.enqueue(new Callback<SevenDaysWeather>() {

            // Caso a request de OK, irá popular alguns textViews com os atributos vindo da resposta.
            @Override
            public void onResponse(Call<SevenDaysWeather> call, Response<SevenDaysWeather> response) {
                Storage.getInstance().setDaily(response.body().getDaily());
            }

            // Caso a request de erro mostra no log.
            @Override
            public void onFailure(Call<SevenDaysWeather> call, Throwable t) {
                Log.d("error", t.toString());
            }

        });
    }

    private void callHourlyWeather(double lat, double lon) {
        // Configura a request passando o parâmetro da cidade.
        Call<SevenDaysWeather> call = new RetrofitConfig().endpoints().getNextHoursWeather(lat, lon);

        // Chama a request que foi configurada anteriormente.
        call.enqueue(new Callback<SevenDaysWeather>() {

            // Caso a request de OK, irá popular alguns textViews com os atributos vindo da resposta.
            @Override
            public void onResponse(Call<SevenDaysWeather> call, Response<SevenDaysWeather> response) {
                Storage.getInstance().setHourly(response.body().getHourly());
            }

            // Caso a request de erro mostra no log.
            @Override
            public void onFailure(Call<SevenDaysWeather> call, Throwable t) {
                Log.d("error", t.toString());
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            Toast.makeText(this, "Clima ficou tenso, permita localização para acessar o aplicativo!", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ActivityCompat.requestPermissions(LoadingScreen.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }, loadingTime);
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(LoadingScreen.this, Locale.getDefault());

                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        // Separa uma string que contem os dados da localização do usuário
                        String[] locations = addresses.get(0).getAddressLine(0).replace("-", ",").replace(" ", "").split(",");

                        // Configura a request passando o parâmetro da cidade.
                        Call<WeatherByCity> call = new RetrofitConfig().endpoints().getWeather(locations[3].toLowerCase());

                        // Chama a request que foi configurada anteriormente.
                        call.enqueue(new Callback<WeatherByCity>() {
                            // Caso a request de OK, irá popular alguns textViews com os atributos vindo da resposta.
                            @Override
                            public void onResponse(Call<WeatherByCity> call, Response<WeatherByCity> response) {
                                // Salva os dados no stotage.
                                Storage.getInstance().setWeatherByCity(response.body());

                                Float lat = response.body().getCoord().getLat();
                                Float lon = response.body().getCoord().getLon();

                                // Chama a próxima request que usa os dados de latitude e longitude.
                                callNextDaysWeather(lat, lon);
                                callHourlyWeather(lat, lon);
                            }

                            // Caso a request de erro mostra no log.
                            @Override
                            public void onFailure(Call<WeatherByCity> call, Throwable t) {
                                Log.d("error", t.toString());
                            }
                        });

                        // Enviar o usuário para o Home
                        Intent navigateToHome = new Intent(LoadingScreen.this, Home.class);
                        startActivity(navigateToHome);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    // Enviar o usuário para a tela de localização
                    Intent navigateToLocationEmpty = new Intent(LoadingScreen.this, LocationEmpty.class);
                    startActivity(navigateToLocationEmpty);
                }
            }
        });

    }
}