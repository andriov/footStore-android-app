package com.example.andresito.foodstore;

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

/**
 * Created by andresito on 3/29/18.
 */

public class WSConnection {

    private String ip_connection="192.168.1.3";

    public WSConnection() {
    }

    public String getIp_connection() {
        return ip_connection;
    }

    public void setIp_connection(String ip_connection) {
        this.ip_connection = ip_connection;
    }

    public ArrayList<String> arrayList(String usuarios){
        ArrayList<String> lista = new ArrayList<String>();
        try {
            JSONArray json = new JSONArray (usuarios);
            for(int i=0;i<json.length();i++){
                JSONObject object=json.getJSONObject(i);
                lista.add(object.getString("username"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public String enviarDatos(){
        URL url; //url donde me voy a conectar
        String linea;
        int respuesta;
        StringBuilder result=new StringBuilder();
        try {
            url = new URL("http://"+this.ip_connection+"/webser/conectar.php");
            HttpURLConnection conection=(HttpURLConnection)url.openConnection();
            respuesta = conection.getResponseCode();//guarda 200 si esq hay respues
            result=new StringBuilder();
            if(respuesta==HttpURLConnection.HTTP_OK){
                InputStream in=new BufferedInputStream(conection.getInputStream());
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                while((linea=reader.readLine())!=null){
                    result.append(linea);
                }
            }
        }catch (Exception e){
        }
        return result.toString();
    }
}
