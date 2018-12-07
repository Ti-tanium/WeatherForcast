package com.example.asus.weatherforcast.weatherDetail;

import android.media.MediaExtractor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_weather_detail,container,false);
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

        mDetailDay.setText(mWeather.getWDay());
        mDetailDate.setText(mWeather.getMounthDay());
        mDetailMaxTemp.setText(mWeather.getTmp_max());
        mDetailMinTemp.setText(mWeather.getTmp_min());
        mDetailWeatherCondition.setText(mWeather.getCondition_day());
        mDetailHumidity.setText(mWeather.getHumidity());
        mDetailAirPressure.setText(mWeather.getPressure());
        mDetailWindDirection.setText(mWeather.getWindDirection());
        mDetailWindSpeed.setText(mWeather.getWindSpeed());

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
}
