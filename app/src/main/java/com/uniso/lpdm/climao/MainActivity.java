// Erick Batista da Silva  RA: 00097990
// Christopher Estanislau Camargo RA: 00097074
// Frank Sussumu Kozaki RA: 00097959
// Gabriel Dilso Tomasine RA: 00096949
// Vinicius Bezerra Fernandes RA: 00056469
// Fabricio Haruo Odaka RA: 00095262

package com.uniso.lpdm.climao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent navigateTo = new Intent(MainActivity.this, LoadingScreenA.class);
        startActivity(navigateTo);
    }
}