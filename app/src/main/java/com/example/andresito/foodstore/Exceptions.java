package com.example.andresito.foodstore;

import android.text.Editable;

/**
 * Created by andresito on 3/29/18.
 */

public class Exceptions extends Exception{

    private static int numberObtained;

    public Exceptions() {
        super();
    }

    public Exceptions(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public Exceptions(String arg0) {
        super(arg0);
    }

    public Exceptions(Throwable arg0) {
        super(arg0);
    }

    public void stinException(String nameField, Editable stringAnalize, int max) throws Exceptions {
        if(stringAnalize.toString().isEmpty()){
            throw new Exceptions("You need to insert the "+nameField);
        }
        if(stringAnalize.length()>max){
            throw new Exceptions("You need to insert less than "+max+" characters");
        }
        try{
            if(Double.parseDouble(stringAnalize.toString())!=321312.123) {
                throw new Exceptions("The " + nameField + " could not be a number");
            }
        }catch(Exceptions fs){
            throw new Exceptions("The " + nameField + " could not be a number");
        }catch(Exception e){
        }
    }

    public static void stinException(String nameField,Editable number) throws Exceptions {
        if(nameField.equals("stock") && number.toString().isEmpty()){
            numberObtained=0;
        }
        if(nameField.equals("stock") && number.toString().isEmpty()==false) {
            try {
                if (Integer.parseInt(number.toString()) < 0) {
                    throw new Exceptions("You need to insert positive integer numbers");
                }
            } catch (Exception e) {
                throw new Exceptions("Insert correctly the number of the stock");
            }
        }
        try {
            if (nameField.equals("cost") && Double.parseDouble(number.toString()) <= 0) {
                throw new Exceptions("You need to insert positive decimal numbers");
            }
        }catch (Exceptions fs){
                throw new Exceptions("You need to insert positive decimal numbers");
        }catch (Exception e){
                throw new Exceptions("The cost is a decimal number");
        }


    }

}




