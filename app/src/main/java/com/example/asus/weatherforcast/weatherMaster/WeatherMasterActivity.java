package com.example.asus.weatherforcast.weatherMaster;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.asus.weatherforcast.R;
import com.example.asus.weatherforcast.SingleFragmentActivity;
import com.example.asus.weatherforcast.Weather;
import com.example.asus.weatherforcast.weatherDetail.WeatherDetailActivity;
import com.example.asus.weatherforcast.weatherDetail.WeatherDetailFragment;

public class WeatherMasterActivity extends SingleFragmentActivity implements WeatherMasterFragment.CallBacks{
    @Override
    protected Fragment createFragment() {
        return new WeatherMasterFragment();
    }

    // for user to start this activity from notification drawer
    public  static Intent newIntent(Context context){
        return  new Intent(context,WeatherMasterActivity.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.mainLayout;
    }

    @Override
    public void onWeatherSelected(Weather weather) {
        if(findViewById(R.id.detail_fragment_container)==null){
            Intent intent= WeatherDetailActivity.newIntent(this,weather.getID());
            startActivity(intent);
        }else{
            Fragment newDetail= WeatherDetailFragment.newInstance(weather.getID());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container,newDetail)
                    .commit();
        }

    }
}
