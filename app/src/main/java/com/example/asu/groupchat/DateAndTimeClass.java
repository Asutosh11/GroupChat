package com.example.asu.groupchat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by asu on 28-05-2015.
 */
public class DateAndTimeClass {

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a, dd MMMM, yyyy");
    String strDate = sdf.format(c.getTime());

    String DateTime(){
        String strDate = sdf.format(c.getTime());
        return strDate;
    }

}