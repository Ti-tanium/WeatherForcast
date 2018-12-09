package com.example.asus.weatherforcast.weatherMaster;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.asus.weatherforcast.SingleFragmentActivity;

public class WeatherMasterActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new WeatherMasterFragment();
    }

    // for user to start this activity from notification drawer
    public  static Intent newIntent(Context context){
        return  new Intent(context,WeatherMasterActivity.class);
    }
}
