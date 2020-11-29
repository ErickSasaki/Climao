package com.uniso.lpdm.climao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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

        // Após um tempo definido na variavel loadingTime muda para a atividade "home".
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent navigateToHome = new Intent(LoadingScreen.this, NextDaysWeather.class);
                startActivity(navigateToHome);
                finish();
            }
        }, loadingTime);*/

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LoadingScreen.this);

        if (ActivityCompat.checkSelfPermission(LoadingScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(LoadingScreen.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
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

    private void getLocation() {

        /*final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();*/

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location = task.getResult();

                System.out.println(location);

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
                        System.out.println(addresses.get(0).getAddressLine(0));
                        System.out.println(addresses.get(0).getAddressLine(1));
                        System.out.println(addresses.get(0).getAddressLine(2));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}