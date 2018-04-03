package com.example.andresito.foodstore;

import android.content.Context;
import android.text.Editable;
import android.widget.Toast;

/**
 * Created by andresito on 3/29/18.
 */

public class FStoreException extends Exception{

    private static int numberObtained;

    public FStoreException() {
        super();
    }

    public FStoreException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public FStoreException(String arg0) {
        super(arg0);
    }

    public FStoreException(Throwable arg0) {
        super(arg0);
    }

    public void stinException(String nameField, Editable stringAnalize, int max) throws FStoreException {
        if(stringAnalize.toString().isEmpty()){
            throw new FStoreException("You need to insert the "+nameField);
        }
        if(stringAnalize.length()>max){
            throw new FStoreException("You need to insert less than "+max+" characters");
        }
        try{
            if(Double.parseDouble(stringAnalize.toString())!=321312.123) {
                throw new FStoreException("The " + nameField + " could not be a number");
            }
        }catch(FStoreException fs){
            throw new FStoreException("The " + nameField + " could not be a number");
        }catch(Exception e){
        }
    }

    public static void stinException(String nameField,Editable number) throws FStoreException {
        if(nameField.equals("stock") && number.toString().isEmpty()){
            numberObtained=0;
        }
        if(nameField.equals("stock") && number.toString().isEmpty()==false) {
            try {
                if (Integer.parseInt(number.toString()) < 0) {
                    throw new FStoreException("You need to insert positive integer numbers");
                }
            } catch (Exception e) {
                throw new FStoreException("Insert correctly the number of the stock");
            }
        }
        try {
            if (nameField.equals("cost") && Double.parseDouble(number.toString()) <= 0) {
                throw new FStoreException("You need to insert positive decimal numbers");
            }
        }catch (Exception e){
                throw new FStoreException("The cost is a decimal number");
        }


    }

}




