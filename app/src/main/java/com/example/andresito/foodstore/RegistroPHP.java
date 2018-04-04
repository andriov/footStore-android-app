package com.example.andresito.foodstore;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import java.util.ArrayList;

public class RegistroPHP extends AppCompatActivity {
    LinearLayout contenedor;
    ScrollView scroll;
    LinearLayout scroll_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_php);
        contenedor = (LinearLayout) findViewById(R.id.contenedor);
        scroll = (ScrollView) findViewById(R.id.scroll_button);
        scroll_layout = (LinearLayout) findViewById(R.id.scroll_layout);
        ArrayList<String> lista = getIntent().getStringArrayListExtra("username");

        for(int i=0;i<lista.size();i++){
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(lista.get(i));
            cb.setTextColor(Color.BLACK);
            scroll_layout.addView(cb);
        }
    }
}
