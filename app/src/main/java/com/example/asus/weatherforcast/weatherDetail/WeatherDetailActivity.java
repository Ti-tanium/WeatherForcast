package com.example.asus.weatherforcast.weatherDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.asus.weatherforcast.SingleFragmentActivity;

import java.util.UUID;

public class WeatherDetailActivity extends SingleFragmentActivity {
    public static final String EXTRA_WEATHER_ID="com.example.asus.weatherforecast.weatherDetail.WeatherDetailActivity";


    @Override
    protected Fragment createFragment() {
        return new WeatherDetailFragment();
    }

    public static Intent newIntent(Context packageContext, UUID weatherID){
        Intent intent=new Intent(packageContext,WeatherDetailActivity.class);
        intent.putExtra(EXTRA_WEATHER_ID,weatherID);
        return intent;
    }


}
