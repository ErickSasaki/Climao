
// Erick Batista da Silva  RA: 00097990
// Christopher Estanislau Camargo RA: 00097074
// Frank Sussumu Kozaki RA: 00097959
// Gabriel Dilso Tomasine RA: 00096949
// Vinicius Bezerra Fernandes RA: 00056469
// Fabricio Haruo Odaka RA: 00095262

package com.uniso.lpdm.climao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.uniso.lpdm.climao.utils.Storage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onStart() {
        super.onStart();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, 44);
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
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, 44);
                }
            }, 3000);
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task < Location > task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                        List < Address > addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        // Separa uma string que contem os dados da localização do usuário
                        String[] locations = addresses.get(0).getAddressLine(0).replace("-", ",").replace(" ", "").split(",");

                        Storage.getInstance().setLocation(locations[3].toLowerCase());

                        Intent navigateToHome = new Intent(MainActivity.this, LoadingScreen.class);
                        startActivity(navigateToHome);
                        finish();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    // Enviar o usuário para a tela de localização
                    Intent navigateToLocationEmpty = new Intent(MainActivity.this, LocationEmpty.class);
                    startActivity(navigateToLocationEmpty);
                    finish();
                }
            }
        });
    }
}