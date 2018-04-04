package com.example.andresito.foodstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnIngresar;
    WSConnection login = new WSConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnIngresar=(Button)findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread tr=new Thread(){
                    @Override
                    public void run() {
                        final String resultado = login.enviarDatos();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> lista = login.arrayList(resultado);
                                Intent i=new Intent(getApplicationContext(),RegistroPHP.class);
                                i.putExtra("username",lista);
                                startActivity(i);
                            }
                        });
                    }
                };
                tr.start();
            }
        });
    }
}
