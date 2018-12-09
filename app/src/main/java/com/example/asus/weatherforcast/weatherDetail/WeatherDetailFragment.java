package com.example.asus.weatherforcast.weatherDetail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaExtractor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.asus.weatherforcast.R;
import com.example.asus.weatherforcast.Utils;
import com.example.asus.weatherforcast.Weather;
import com.example.asus.weatherforcast.datebase.WeatherLab;

import java.util.UUID;

public class WeatherDetailFragment extends Fragment {
    private Weather mWeather;
    private TextView mDetailDay;
    private TextView mDetailDate;
    private TextView mDetailMaxTemp;
    private TextView mDetailMinTemp;
    private TextView mDetailWeatherCondition;
    private ImageView mDetailConditionImage;
    private TextView mDetailHumidity;
    private TextView mDetailAirPressure;
    private TextView mDetailWindSpeed;
    private TextView mDetailWindDirection;
    private TextView mDetailMaxTempUnit;
    private TextView mDetailMinTempUnit;
    private TextView mDetailSpeedUnit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_weather_detail,container,false);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        setHasOptionsMenu(true);
        boolean mIsBritishSystem=sharedPreferences.getBoolean(getString(R.string.KEY_SETTING_TEMPERATUREUNITS),false);
        mDetailDay=(TextView)view.findViewById(R.id.detail_day);
        mDetailDate=(TextView)view.findViewById(R.id.detail_date);
        mDetailMaxTemp=(TextView)view.findViewById(R.id.detail_max_temp);
        mDetailMinTemp=(TextView)view.findViewById(R.id.detail_min_temp);
        mDetailWeatherCondition=(TextView)view.findViewById(R.id.detail_weather_condition);
        mDetailConditionImage=(ImageView) view.findViewById(R.id.detail_condition_image);
        mDetailHumidity=(TextView)view.findViewById(R.id.detail_humidity);
        mDetailAirPressure=(TextView)view.findViewById(R.id.detail_pressure);
        mDetailWindDirection=(TextView)view.findViewById(R.id.detail_wind_direction);
        mDetailWindSpeed=(TextView)view.findViewById(R.id.detail_wind_speed);
        mDetailMaxTempUnit=(TextView)view.findViewById(R.id.detail_max_temp_unit);
        mDetailMinTempUnit=(TextView)view.findViewById(R.id.detail_min_temp_unit);
        mDetailSpeedUnit=(TextView)view.findViewById(R.id.detail_speed_unit);

        mDetailDay.setText(mWeather.getWDay());
        mDetailDate.setText(mWeather.getMounthDay());
        mDetailMaxTemp.setText(mWeather.getTmp_max());
        mDetailMinTemp.setText(mWeather.getTmp_min());
        mDetailWeatherCondition.setText(mWeather.getCondition_day());
        mDetailHumidity.setText(mWeather.getHumidity());
        mDetailAirPressure.setText(mWeather.getPressure());
        mDetailWindDirection.setText(mWeather.getWindDirection());
        mDetailWindSpeed.setText(mWeather.getWindSpeed());
        mDetailMaxTempUnit.setText(mIsBritishSystem?getString(R.string.british_fahrenheit_scale):getString(R.string.metric_celsius_scale));
        mDetailMinTempUnit.setText(mIsBritishSystem?getString(R.string.british_fahrenheit_scale):getString(R.string.metric_celsius_scale));
        mDetailSpeedUnit.setText(mIsBritishSystem?getString(R.string.british_speed):getString(R.string.metric_speed));
        if(mIsBritishSystem){
            mDetailMaxTempUnit.setTextSize(50);
            mDetailMaxTemp.setTextSize(50);
            mDetailMinTempUnit.setTextSize(25);
            mDetailMinTemp.setTextSize(25);
        }
        String imageFileName="cond"+mWeather.getCondition_code_day();
        int imageResId= Utils.getResourceByReflect(imageFileName);
        mDetailConditionImage.setImageResource(imageResId);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID weatherId=(UUID)getActivity().getIntent().getSerializableExtra(WeatherDetailActivity.EXTRA_WEATHER_ID);
        mWeather= WeatherLab.get(getActivity()).getWeather(weatherId);
    }

    private String getWeatherReport(){
        String report=getString(R.string.share_format,mWeather.getWDay(),mWeather.getCondition_day(),mWeather.getTmp_max(),mWeather.getTmp_min(),mWeather.getHumidity(),mWeather.getPressure(),mWeather.getWindDirection());
        return report;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_weather_detail,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_icon_share:
                //todo
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,getWeatherReport());
                intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.share_subject));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
