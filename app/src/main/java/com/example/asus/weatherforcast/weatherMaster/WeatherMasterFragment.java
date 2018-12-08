package com.example.asus.weatherforcast.weatherMaster;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.weatherforcast.R;
import com.example.asus.weatherforcast.Utils;
import com.example.asus.weatherforcast.Weather;
import com.example.asus.weatherforcast.datebase.WeatherLab;
import com.example.asus.weatherforcast.weatherDetail.WeatherDetailActivity;
import com.example.asus.weatherforcast.weatherfetcher.WeatherFetcher;

import java.util.ArrayList;
import java.util.List;

public class WeatherMasterFragment extends Fragment {
    private static final String TAG="WeatherMasterFragment";
    private TextView mTodayDate;
    private TextView mTodayMaxTemp;
    private TextView mTodayMinTemp;
    private TextView mTodayWeatherCondition;
    private ImageView mTodayConditionImage;
    private RecyclerView mWeatherRecyclerView;
    private WeatherAdapter mAdapter;
    private List<Weather>mWeathers=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_weather_master,container,false);
        mTodayDate=(TextView)v.findViewById(R.id.master_today_date);
        mTodayMaxTemp=(TextView)v.findViewById(R.id.master_highest_temperature);
        mTodayMinTemp=(TextView)v.findViewById(R.id.master_lowest_temperature);
        mTodayWeatherCondition=(TextView)v.findViewById(R.id.master_weather_condition);
        mTodayConditionImage=(ImageView)v.findViewById(R.id.master_condition_image);
        mWeatherRecyclerView=(RecyclerView)v.findViewById(R.id.master_weather_recyclerview);
        mWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new FetchWeatherTask().execute();

        return v;
    }

    private void setupAdapter(){
        if(isAdded()){
            mAdapter=new WeatherAdapter(mWeathers);
            mWeatherRecyclerView.setAdapter(mAdapter);
        }
    }

    private void storeWeather(){
        WeatherLab weatherLab=WeatherLab.get(getActivity());
        for(Weather weather:mWeathers){
            weatherLab.addWeather(weather);
        }
    }

    private class WeatherHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTime;
        private TextView mHighestTemp;
        private TextView mLowestTemp;
        private TextView mWeatherConditionDay;
        private ImageView mConditionImage;

        private Weather mWeather;

        @Override
        public void onClick(View v) {
            Intent intent=WeatherDetailActivity.newIntent(getActivity(),mWeather.getID());
            startActivity(intent);
        }

        public WeatherHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.item_weather_list,parent,false));
            mTime=(TextView)itemView.findViewById(R.id.listitem_time);
            mHighestTemp=(TextView)itemView.findViewById(R.id.listitem_highest_temperature);
            mLowestTemp=(TextView)itemView.findViewById(R.id.listitem_lowest_temp);
            mWeatherConditionDay=(TextView)itemView.findViewById(R.id.listitem_weather_condition);
            mConditionImage=(ImageView)itemView.findViewById(R.id.listitem_condtion_image);
            itemView.setOnClickListener(this);
        }

        public void bind(Weather weather){
            mWeather=weather;
            mTime.setText(weather.getWDay());
            mHighestTemp.setText(weather.getTmp_max());
            mLowestTemp.setText(weather.getTmp_min());
            mWeatherConditionDay.setText(weather.getCondition_day());
            Log.d("Condition Code:",weather.getCondition_code_day());
            String imageFileName="cond"+weather.getCondition_code_day();
            Log.d("ImageFileName:",imageFileName);
            int imageResId= Utils.getResourceByReflect(imageFileName);
            Log.d("imageResourceId:",""+imageResId);
            mConditionImage.setImageResource(imageResId);
        }

    }

    private class WeatherAdapter extends RecyclerView.Adapter<WeatherHolder>{
        private List<Weather> mWeathers;
        public WeatherAdapter(List<Weather> weathers){
            mWeathers=weathers;
        }

        @Override
        public WeatherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            return new WeatherHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(WeatherHolder holder, int position) {
            Weather weather=mWeathers.get(position);
            holder.bind(weather);
        }

        @Override
        public int getItemCount() {
            return mWeathers.size();
        }
    }

    private class FetchWeatherTask extends AsyncTask<Void,Void,List<Weather>>{
        @Override
        protected List<Weather> doInBackground(Void... voids) {
            return new WeatherFetcher().fetchWeather("长沙");
        }

        @Override
        protected void onPostExecute(List<Weather> weathers) {
            Weather todayWeather=weathers.get(0);
            weathers.remove(0);
            String imageFileName="cond"+todayWeather.getCondition_code_day();
            int imageResId=Utils.getResourceByReflect(imageFileName);
            mTodayConditionImage.setImageResource(imageResId);
            mTodayConditionImage.setColorFilter(Color.WHITE);
            mTodayDate.setText(todayWeather.getFormattedDate());
            mTodayMaxTemp.setText(todayWeather.getTmp_max());
            mTodayMinTemp.setText(todayWeather.getTmp_min());
            mTodayWeatherCondition.setText(todayWeather.getCondition_day());
            mWeathers=weathers;
            setupAdapter();
            storeWeather();
        }
    }
}

