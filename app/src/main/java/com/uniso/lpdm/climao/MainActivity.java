package com.uniso.lpdm.climao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent navigateTo = new Intent(this, LoadingScreen.class);
        startActivity(navigateTo);
    }
}