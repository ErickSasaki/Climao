package com.uniso.lpdm.climao;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.uniso.lpdm.climao.utils.Storage;
import com.uniso.lpdm.climao.weather.Sys;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

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

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            Geocoder geocoder = new Geocoder(LocationEmpty.this, Locale.getDefault());

            try {
                List<Address> addresses = geocoder.getFromLocation(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude(), 1);

                // Separa uma string que contem os dados da localização do usuário
                String[] locations = addresses.get(0).getAddressLine(0).replace("-", ",").replace(" ", "").split(",");

                Storage.getInstance().setLocation(locations[3].toLowerCase());

                Intent navigateToHome = new Intent(LocationEmpty.this, LoadingScreen.class);
                startActivity(navigateToHome);
                finish();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);

        fusedLocationProviderClient = getFusedLocationProviderClient(this);

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());



        /*fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(LocationEmpty.this, Locale.getDefault());

                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        // Separa uma string que contem os dados da localização do usuário
                        String[] locations = addresses.get(0).getAddressLine(0).replace("-", ",").replace(" ", "").split(",");

                        Storage.getInstance().setLocation(locations[3].toLowerCase());

                        Intent navigateToHome = new Intent(LocationEmpty.this, LoadingScreen.class);
                        startActivity(navigateToHome);
                        finish();

                    } catch (IOException e) {
                        Log.d("test", "deu ruim!!!");
                        e.printStackTrace();
                        finish();
                    }
                }
            }
        }); */
    }

    @Override
    protected void onPause() {
        super.onPause();

        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
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



}
