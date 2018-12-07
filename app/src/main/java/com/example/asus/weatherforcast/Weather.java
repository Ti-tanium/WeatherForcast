package com.example.asus.weatherforcast;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class Weather {
    private UUID mID;
    private Date mWDate;
    private String mWDay;
    private String  tmp_max;
    private String  tmp_min;
    private String condition_day;
    private String condition_night;
    private String condition_code_day;
    private String condition_code_night;
    private String mHumidity;
    private String mPressure;
    private String mWindSpeed;
    private String mWindDirection;

    private static final String TAG="Weather";
    private static final String[] DayofWeek=new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    public Weather(){
        mID=UUID.randomUUID();
    }

    public String getWDate() {
        DateFormat format=new SimpleDateFormat("MMM d");
        return mWDay+","+format.format(mWDate);
    }

    public void setWDate(String date) {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd");
        try {
            mWDate = format.parse(date);
            setWDay(DayofWeek[mWDate.getDay()]);
        }catch (ParseException pe){
            Log.e(TAG,"Date parsing error");
        }
    }

    public String getWDay() {
        return mWDay;
    }

    public void setWDay(String WDay) {
        mWDay = WDay;
    }

    public String getTmp_max() {
        return tmp_max;
    }

    public void setTmp_max(String tmp_max) {
        this.tmp_max = tmp_max+"°";
    }

    public String  getTmp_min() {
        return tmp_min;
    }

    public void setTmp_min(String  tmp_min) {
        this.tmp_min = tmp_min+"°";
    }

    public String getCondition_day() {
        return condition_day;
    }

    public void setCondition_day(String condition_day) {
        this.condition_day = condition_day;
    }

    public String getCondition_night() {
        return condition_night;
    }

    public void setCondition_night(String condition_night) {
        this.condition_night = condition_night;
    }

    public UUID getID() {
        return mID;
    }

    public void setID(UUID ID) {
        mID = ID;
    }

    public String getCondition_code_day() {
        return condition_code_day;
    }

    public void setCondition_code_day(String condition_code_day) {
        this.condition_code_day = condition_code_day;
    }

    public String getCondition_code_night() {
        return condition_code_night;
    }

    public void setCondition_code_night(String condition_code_night) {
        this.condition_code_night = condition_code_night;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public void setHumidity(String humidity) {
        mHumidity = humidity+"%";
    }

    public String getPressure() {
        return mPressure;
    }

    public void setPressure(String pressure) {
        mPressure = pressure+"hPa";
    }

    public String getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        mWindSpeed = windSpeed+"km/h";
    }

    public String getWindDirection() {
        return mWindDirection;
    }

    public void setWindDirection(String windDirection) {
        mWindDirection = windDirection;
    }
}
