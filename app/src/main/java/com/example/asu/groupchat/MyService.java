package com.example.asu.groupchat;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Binder;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyService extends Service {


   private List<ParseObject> ob;
   private String[] DateTimeArray =  new String[1000];
   private String[] readUsername =  new String[1000];
   private String[] messages = new String[1000];

    private final IBinder buckysBinder = new MyLocalBinder();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return buckysBinder;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    // your code starts from here






    int NoOfRowsInDB() {

        // Reading data from parse.com database

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "Obb1");
        query.orderByDescending("_created_at");
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        int a = -1;

        for (ParseObject country : ob) {
            a++;
        }

        return a;
    }




    String[] getDateTimeFromParseCloudDB() {

        // Reading data from parse.com database

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "Obb1");
        query.orderByDescending("_created_at");
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        int i = 0;

        for (ParseObject country : ob) {
            DateTimeArray[i] = (String) country.get("DateTime");
            i++;

        }

        return DateTimeArray;
    }



    String[] getUsernameFromParseCloudDB() {

        // Reading data from parse.com database

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "Obb1");
        query.orderByDescending("_created_at");
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        int i = 0;

        for (ParseObject country : ob) {
            readUsername[i] = (String) country.get("Username");
            i++;

        }

        return readUsername;
    }



    String[] getMessagesFromParseCloudDB() {

        // Reading data from parse.com database

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "Obb1");
        query.orderByDescending("_created_at");
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        int i = 0;

        for (ParseObject country : ob) {
            messages[i] = (String) country.get("Message");
            i++;

        }

        return messages;
    }




    // your code ends here

    ////////////////////////////////////////////////////////////////////////////////////////////

    public class MyLocalBinder extends Binder {
        MyService getService(){
            return MyService.this;
        }
    }


}
