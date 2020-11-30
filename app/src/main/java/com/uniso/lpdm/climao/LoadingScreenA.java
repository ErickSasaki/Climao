package com.uniso.lpdm.climao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.util.Log;

import com.uniso.lpdm.climao.api.RetrofitConfig;
import com.uniso.lpdm.climao.seven_days_weather.SevenDaysWeather;
import com.uniso.lpdm.climao.utils.Storage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingScreenA extends AppCompatActivity {

    private static int loadingTime = 5000;
    FusedLocationProviderClient fusedLocationProviderClient;

    //static {
        // AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //}

    // Serão feitas requests na atividade de loading e salvos na classe storage, esses dados serão usados nas próximas atividades.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        /*/ Configura a request passando o parâmetro da cidade.
        Call<WeatherByCity> call = new RetrofitConfig().endpoints().getWeather("sorocaba");

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
        });*/

        if (ActivityCompat.checkSelfPermission(LoadingScreenA.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(LoadingScreenA.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
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

    // Após um tempo definido na variavel loadingTime muda para a atividade "home".
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent navigateToHome = new Intent(LoadingScreen.this, Home.class);
                startActivity(navigateToHome);
                finish();
            }
        }, loadingTime);*/

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
                    ActivityCompat.requestPermissions(LoadingScreenA.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }, loadingTime);
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        /*final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        alert.show();*/
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    // Location location = task.getResult();
/*
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(LoadingScreen.this, Locale.getDefault());

                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        //new LocationUtils();

                        System.out.println(addresses.get(0).getLocality());
                        System.out.println(addresses.get(0).getLongitude());
                        System.out.println(addresses.get(0).getLatitude());
                        System.out.println(addresses.get(0).getCountryName());
                        System.out.println(addresses.get(0).getLocale());

                        String[] locations = addresses.get(0).getAddressLine(0).replace('-', ' ').replace(" ", "").split(",");


                        System.out.println(locations);

                        Call<WeatherByCity> call = new RetrofitConfig().endpoints().getWeather("sorocaba");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/
                }
            });

    }
}