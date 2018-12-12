package com.example.asus.weatherforcast.datebase;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.example.asus.weatherforcast.Weather;

import java.util.UUID;

public class WeatherCursorWrapper extends CursorWrapper {
    public WeatherCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Weather getWeather(){
        Log.i("DBDEBUG-CsWrpr.getWet:","called");
        String uuidString=getString(getColumnIndex(WeatherDbSchema.WeatherTable.Cols.UUID));
        String date=getString(getColumnIndex(WeatherDbSchema.WeatherTable.Cols.DATE));
        String day=getString(getColumnIndex(WeatherDbSchema.WeatherTable.Cols.DAY));
        String tmp_max=getString(getColumnIndex(WeatherDbSchema.WeatherTable.Cols.MAX_TEMP));
        String tmp_min=getString(getColumnIndex(WeatherDbSchema.WeatherTable.Cols.MIN_TEMP));
        String condition_day=getString(getColumnIndex(WeatherDbSchema.WeatherTable.Cols.CONDITION_DAY));
        String condition_night=getString(getColumnIndex(WeatherDbSchema.WeatherTable.Cols.CONDITION_NIGHT));
        String condition_code_day=getString(getColumnIndex(WeatherDbSchema.WeatherTable.Cols.CONDITION_CODE_DAY));
        String condition_code_night=getString(getColumnIndex(WeatherDbSchema.WeatherTable.Cols.CONDITION_CODE_NIGHT));
        String humidity=getString(getColumnIndex(WeatherDbSchema.WeatherTable.Cols.HUMIDITY));
        String pressure=getString(getColumnIndex(WeatherDbSchema.WeatherTable.Cols.AIRPRESSURE));
        String windspeed=getString(getColumnIndex(WeatherDbSchema.WeatherTable.Cols.WINDSPEED));
        String winddir=getString(getColumnIndex(WeatherDbSchema.WeatherTable.Cols.WINDDIRECTION));
        Log.i("DBDEBUG-Wrapp.getWetr:","ID:"+uuidString);
        Weather weather=new Weather(UUID.fromString(uuidString));
        weather.setWDate(date);
        weather.setWDay(day);
        weather.setTmp_max(tmp_max);
        weather.setTmp_min(tmp_min);
        weather.setCondition_day(condition_day);
        weather.setCondition_night(condition_night);
        weather.setCondition_code_day(condition_code_day);
        weather.setCondition_code_night(condition_code_night);
        weather.setHumidity(humidity);
        weather.setPressure(pressure);
        weather.setWindSpeed(windspeed);
        weather.setWindDirection(winddir);
        return weather;
    }

}
