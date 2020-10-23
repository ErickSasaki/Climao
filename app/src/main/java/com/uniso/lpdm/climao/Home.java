package com.uniso.lpdm.climao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import retrofit2.Retrofit;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final TextView textView = (TextView) findViewById(R.id.test);
        textView.setText("new text!");

        String cep = "18066007";
        final String url = "http://viacep.com.br/ws/" + cep + "/json/";

        Retrofit retrofit = NetworkUtils.getRetrofitInstance(url);


    }
}