package com.example.asus.weatherforcast.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.asus.weatherforcast.datebase.WeatherDbSchema.WeatherTable;

public class WeatherBaseOpenHelper extends SQLiteOpenHelper {
    private static  final int VERSION=1;
    private static final String DATABASE_NAME="weatherBase.db";

    public WeatherBaseOpenHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ WeatherTable.NAME+"("+
                "_id integer primary key autoincrement, "+
                WeatherTable.Cols.UUID+","+
                WeatherTable.Cols.DATE+","+
                WeatherTable.Cols.DAY+","+
                WeatherTable.Cols.MAX_TEMP+","+
                WeatherTable.Cols.MIN_TEMP+","+
                WeatherTable.Cols.CONDITION_CODE_DAY+","+
                WeatherTable.Cols.CONDITION_CODE_NIGHT+","+
                WeatherTable.Cols.CONDITION_NIGHT+","+
                WeatherTable.Cols.CONDITION_DAY+","+
                WeatherTable.Cols.HUMIDITY+","+
                WeatherTable.Cols.AIRPRESSURE+","+
                WeatherTable.Cols.WINDSPEED+","+
                WeatherTable.Cols.WINDDIRECTION
                +")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
