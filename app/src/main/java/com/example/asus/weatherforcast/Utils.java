package com.example.asus.weatherforcast;

import android.util.Log;

import java.lang.reflect.Field;

public class Utils {
    public static int getResourceByReflect(String imageName){
        Class drawable  =  R.drawable.class;
        Field field = null;
        int r_id ;
        try {
            field = drawable.getField(imageName);
            r_id = field.getInt(field.getName());
        } catch (Exception e) {
            r_id=-1;
            Log.e("ERROR", "PICTURE NOT　FOUND！");
        }
        return r_id;
    }
}
