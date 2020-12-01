package com.uniso.lpdm.climao;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.uniso.lpdm.climao.api.RetrofitConfig;
import com.uniso.lpdm.climao.seven_days_weather.SevenDaysWeather;
import com.uniso.lpdm.climao.utils.Storage;
import com.uniso.lpdm.climao.weather.WeatherByCity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationEmpty extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    Button btLocation;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_location);

        btLocation = findViewById(R.id.bt_location);

        hasLocation();

        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasLocation();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        new Timer().scheduleAtFixedRate(new TimerTask(){
            @SuppressLint("MissingPermission")
            @Override
            public void run(){

                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location != null) {
                            try {
                                Geocoder geocoder = new Geocoder(LocationEmpty.this, Locale.getDefault());

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
                                Intent navigateToHome = new Intent(LocationEmpty.this, Home.class);
                                startActivity(navigateToHome);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        },5000,5000);
    }

    private void hasLocation() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("É necessário o GPS para o funcionamento do aplicativo, deseja habilita-lo?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
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

}
