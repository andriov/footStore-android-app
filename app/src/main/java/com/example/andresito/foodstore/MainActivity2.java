package com.example.andresito.foodstore;

import android.content.DialogInterface;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    /*/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

    //Here I create an array list of string which will store the names of the products readed from the GET Method
    ArrayList<String> productos = new ArrayList<>();
    //This variable will allow me to add the products to the ListView in activity main
    ListView list;


    //The button that will allow us to select the products we want to delete
    Button deleteProductPrincipalButton;
    //To save the list of products that can be deleted
    String[] listOfProductsThatCanBeDeleted;
    //Here we declare an array that will tell me which products are going to be deleted
    boolean[] checkedItems;
    //Here we save the position of the selected items for deletion
    ArrayList<Integer> positionOfProductsForElimination = new ArrayList<>();
    /*/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //This is the list view in the activity_home that will show us all the products as a list
        list = (ListView) findViewById(R.id.listView);
        //Here we adapt our list of products to an ArrayAdapter in order to could carry them to our listview in our activity_home
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,productos);
        //Here we carry our products to our List View in our activity_home
        list.setAdapter(arrayAdapter);
        //We make a request to the server and present the data in the list view
        getData();
        //this is the delete button present in the activity home
        deleteProductPrincipalButton = (Button) findViewById(R.id.buttonDeleteProduct);
        //Here we set what happend when we click on delete product in our list of products
        deleteProductPrincipalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //First we create an alerrt dialog in the activity where we are
                AlertDialog.Builder selectProductsAlert = new AlertDialog.Builder(MainActivity2.this);
                //We set the tittle we want for our alert dialog, in our case a message to ask a question to the user
                selectProductsAlert.setTitle("Which Products You Want To Delete?");
                //We set our listOfProductThatCanBeDeleted to an array of size zero
                listOfProductsThatCanBeDeleted = new String[0];
                //We assign the number of products we really have to this array, because maybe we eliminate products one time etc
                listOfProductsThatCanBeDeleted = new String[productos.size()];
                //We assign the available products to the array of products that can be deleted
                listOfProductsThatCanBeDeleted = (String[]) productos.toArray(listOfProductsThatCanBeDeleted);
                //To save the state of the products in the delete alert dialog, selected or not selected
                checkedItems = new boolean[listOfProductsThatCanBeDeleted.length];
                //We allow the user to select several items from a list we will show, in this case the list is a copy of our arraylist but in an array of strings
                selectProductsAlert.setMultiChoiceItems(listOfProductsThatCanBeDeleted, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    //This part is a little bit confusing but is easy if you try ;)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        //We create a method that checks the state of each element of our list of products that can be deleted
                        if(isChecked){
                            //If the item is selected to eliminate, then we save the position of the element to eliminate, and if we don't have it saved in our positions to eliminate
                            if(!positionOfProductsForElimination.contains(Integer.valueOf(position))){
                                //then we added, this is just for avoid duplicate values, when people check several times and the press delete
                                positionOfProductsForElimination.add(Integer.valueOf(position));
                            }

                        }//Else, if we push but the item where we push is not selected, then, check if that position is saved in our positions to eliminate, if it is saved
                        else if(positionOfProductsForElimination.contains(Integer.valueOf(position))){
                            //remove the position from our values to eliminate, this is to assure that people maybe push an option but not for selecting it, just for uncheck it
                            positionOfProductsForElimination.remove(Integer.valueOf(position));

                        }
                    }
                });
                //The next line is commented in order to make the dialog accept no options selected, and close this when we push outside it
                //but if we uncomment the line, then we need to a botton dissmis to get out of the dialog without make deshacer or remove
                //selectProductsAlert.setCancelable(false);
                selectProductsAlert.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //First we create an alerrt dialog in the activity where we are in order to ask if the user is sure to delete selected products
                        AlertDialog.Builder productsAlertDialog = new AlertDialog.Builder(MainActivity2.this);
                        //We set the tittle we want for our alert dialog, in our case a message to ask a question to the user
                        productsAlertDialog.setTitle("¿Está Seguro de Querer Borrar Estos Productos?");
                        //we set actions to take if the user is sure to delete selected products
                        productsAlertDialog.setPositiveButton("Eliminar Permanentemente", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface alertDialog, int which) {
                                //To save the product that we will remove from our list
                                String product = "";
                                //iterate in all positions we save to delete
                                for (int i=0; i<positionOfProductsForElimination.size(); i++){
                                    //delete each product from our list that is showed in our listview
                                    productos.remove(listOfProductsThatCanBeDeleted[positionOfProductsForElimination.get(i)]);
                                }
                                //the next four lines are just for refresh the list of products after eliminate the selected
                                //Here we adapt our list of products to an ArrayAdapter in order to could carry them to our listview in our activity_home
                                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity2.this,android.R.layout.simple_list_item_1,productos);
                                //Here we carry our products to our List View in our activity_home
                                list.setAdapter(arrayAdapter);
                            }
                        });
                        //We set the actions to take if the user cancel the order
                        productsAlertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface alertDialog, int which) {
                                //We dissapear the dialog
                                alertDialog.dismiss();
                            }
                        });
                        //Finally we create our dialog from the dialog builder and its methods
                        AlertDialog alertDialogDos = productsAlertDialog.create();
                        //we show our dialog
                        alertDialogDos.show();
                    }
                });
                //A button that allow us to get out of the dialog without choose eliminar or deshacer
                selectProductsAlert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //desaparece el dialogo
                        dialog.dismiss();
                    }
                });
                //A button that allow me to go to read again all products and mark all the items as unchecked again
                selectProductsAlert.setNeutralButton("Deshacer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //mark the checked items as uncheked again
                        for(int i=0; i< checkedItems.length; i++){
                            checkedItems[i]=false;
                        }
                        //we remove all from our arraylist
                        productos.clear();
                        //we refres our products list and also the activity that handle it
                        //We make a request to the server and present the data in the list view
                        getData();
                        //Here we adapt our list of products to an ArrayAdapter in order to could carry them to our listview in our activity_home
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity2.this,android.R.layout.simple_list_item_1,productos);
                        //Here we carry our products to our List View in our activity_home
                        list.setAdapter(arrayAdapter);

                    }
                });
                //After set all configuration of our dialog, then we create it
                AlertDialog alertDialog = selectProductsAlert.create();
                //And the we show it
                alertDialog.show();
            }
        });
    }



    //This method allow me to read the products from the server
    public void getData(){
        //Here I give the direction from where we going to read the products data
        String linkToJson = "https://api.myjson.com/bins/rhmaz";
        //These 2 lines are necessary in order to allow us work for some smartphones
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Url variable to allow us to manage an internet url where is stored our json file
        URL url;
        //This variable will help us to make the reading or the connection to the server
        HttpURLConnection conn;

        try {
            //We create our link with the string passed before
            url = new URL(linkToJson);
            //we open a connection to the url of our json file
            conn = (HttpURLConnection) url.openConnection();
            //We made a request to get all in the file
            conn.setRequestMethod("GET");
            //We connect us to the url
            conn.connect();
            //We safe the readed json
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //This variable will help us to save line by line of the json file
            String inputLine;
            //to safe our lines that are stored in a BufferReader variable
            StringBuffer response = new StringBuffer();
            //While to read all lines in the BufferReader
            while ((inputLine = in.readLine()) != null){
                //We save all in our variable
                response.append(inputLine);
            }
            //In order to store all the Buffer as a string
            String json = "";
            //We parse our response to a String
            json = response.toString();
            //To store the string json
            JSONArray jsonArr = null;
            //We create a JsonArray from the string that contains Json Object from all the response inside the GET response
            jsonArr = new JSONArray(json);
            //To store a message that we will present in our ListView
            String mensage = "";
            //Where we will store our Json Objects which are in the JSon Array
            JSONObject jsonObject;
            //For each Json Object in the JsonArray that we created
            for(int i=0; i<jsonArr.length(); i++){
                //Take each Json Object inside the JsonArray
                jsonObject = jsonArr.getJSONObject(i);
                //Print the key of the name field of the Json object in the LogCat, this is just for testing the response
                Log.d("Salida", jsonObject.optString("name"));
                //the next line is just because we want to print the number of the product, but we want to begin in 1, not in zero
                int numero = i+1;
                //Save the result of the key name of our json Object in a customized message
                mensage = /*"Producto " + numero + ": " + */ jsonObject.optString("name") + "\n";
                //Add the customized Message to the Array list of strings that will be printed at the end in the ListView
                productos.add(mensage);
            }

        } catch (MalformedURLException e) {
            Log.d("error numero 1", "problema numero 1 no se pudo conectar");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("error numero 2", "problema numero 2 no se pudo conectar");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.d("error numero 3", "problema numero 3 no se pudo conectar");
            e.printStackTrace();
        }
    }


}
