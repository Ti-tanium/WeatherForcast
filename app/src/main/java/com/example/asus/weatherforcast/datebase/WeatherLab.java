package com.example.asus.weatherforcast.datebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asus.weatherforcast.Weather;
import com.example.asus.weatherforcast.datebase.WeatherDbSchema.WeatherTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WeatherLab {
    private static WeatherLab sWeatherLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private static final String TAG="WeatherLab";

    public static WeatherLab get(Context context){
        if(sWeatherLab==null){
            sWeatherLab=new WeatherLab(context);
        }
        return sWeatherLab;
    }

    private WeatherLab(Context context){
        mContext=context.getApplicationContext();
        mDatabase=new WeatherBaseOpenHelper(mContext).getWritableDatabase();
    }

    public List<Weather> getWeathers() {
        Log.i("DBDEBUG-getWeathers:","called");
        List<Weather> weathers=new ArrayList<>();
        WeatherCursorWrapper cursorWrapper=queryWeather(null,null);
        try{
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                weathers.add(cursorWrapper.getWeather());
                cursorWrapper.moveToNext();
            }
        }catch (Exception e){
            Log.e(TAG,e.getStackTrace().toString());
        }
        finally {
            cursorWrapper.close();
        }
        Log.i("DBDEBUG-getWeathersIsEP",weathers.isEmpty()+"");
        return weathers;
    }

    public Weather getWeather(UUID id){
        WeatherCursorWrapper cursor=queryWeather(WeatherTable.Cols.UUID+" = ?",new String[]{id.toString()});
        try{
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToNext();
            return cursor.getWeather();
        }finally {
            cursor.close();
        }

    }

    private static ContentValues getContentValues(Weather weather){
        ContentValues values=new ContentValues();
        values.put(WeatherTable.Cols.UUID,weather.getID().toString());
        values.put(WeatherTable.Cols.DATE,weather.getWDate());
        values.put(WeatherTable.Cols.DAY,weather.getWDay());
        values.put(WeatherTable.Cols.MAX_TEMP,weather.getTmp_max());
        values.put(WeatherTable.Cols.MIN_TEMP,weather.getTmp_min());
        values.put(WeatherTable.Cols.CONDITION_DAY,weather.getCondition_day());
        values.put(WeatherTable.Cols.CONDITION_NIGHT,weather.getCondition_night());
        values.put(WeatherTable.Cols.CONDITION_CODE_DAY,weather.getCondition_code_day());
        values.put(WeatherTable.Cols.CONDITION_CODE_NIGHT,weather.getCondition_code_night());
        values.put(WeatherTable.Cols.HUMIDITY,weather.getHumidity());
        values.put(WeatherTable.Cols.AIRPRESSURE,weather.getPressure());
        values.put(WeatherTable.Cols.WINDSPEED,weather.getWindSpeed());
        values.put(WeatherTable.Cols.WINDDIRECTION,weather.getWindDirection());
        return values;
    }

    public void addWeather(Weather weather){
        ContentValues values=getContentValues(weather);
        mDatabase.insert(WeatherTable.NAME,null,values);
        Log.i(TAG,"weather:"+weather.getID()+" inserted.");
    }

    public void updateWeather(Weather weather){
        String uuidString=weather.getID().toString();
        ContentValues values=getContentValues(weather);
        mDatabase.update(WeatherTable.NAME,values,WeatherTable.Cols.UUID+" = ?",new String[]{uuidString});
    }

    private WeatherCursorWrapper queryWeather(String whereClause,String[] whereArgs){
        Cursor cursor=mDatabase.query(
                WeatherTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new WeatherCursorWrapper(cursor);
    }

}
