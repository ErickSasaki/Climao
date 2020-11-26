package com.uniso.lpdm.climao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;

public class LoadingScreen extends AppCompatActivity {

    private static int loadingTime = 3000;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        // Após um tempo definido na variavel loadingTime muda para a atividade "home".
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent navigateToHome = new Intent(LoadingScreen.this, NextDaysWeather.class);
                startActivity(navigateToHome);
                finish();
            }
        }, loadingTime);
    }
}