package com.example.asus.weatherforcast.weatherfetcher;

import android.net.Uri;
import android.util.Log;

import com.example.asus.weatherforcast.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherFetcher {
    private static final String TAG="WeatherFetcher";
    private static final String API_KEY="26dab2c043f84f98b83a875761a14169";
    private static final String HEFENG_API_URL="https://free-api.heweather.com/s6/weather";

    public byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url=new URL(urlSpec);
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            InputStream in=connection.getInputStream();

            if(connection.getResponseCode()!= HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage()+":with "+urlSpec);
            }

            int bytesRead=0;
            byte[] buffer=new byte[1024];
            while ((bytesRead=in.read(buffer))>0){
                out.write(buffer,0,bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec)throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

    public void parseJSON(List<Weather>weathers,JSONObject jsonBody) throws JSONException,IOException{
        JSONArray HeWeather6=jsonBody.getJSONArray("HeWeather6");
        JSONObject WeatherJSON=HeWeather6.getJSONObject(0);
        JSONArray dailyForecastJSONArray=WeatherJSON.getJSONArray("daily_forecast");

        for (int i=0;i<dailyForecastJSONArray.length();i++){
            JSONObject dailyForecastObject =dailyForecastJSONArray.getJSONObject(i);
            Weather weather=new Weather();
            weather.setCondition_day(dailyForecastObject.getString("cond_txt_d"));
            weather.setCondition_night(dailyForecastObject.getString("cond_txt_n"));
            weather.setCondition_code_day(dailyForecastObject.getString("cond_code_d"));
            weather.setCondition_code_night(dailyForecastObject.getString("cond_code_n"));
            weather.setTmp_max(dailyForecastObject.getString("tmp_max"));
            weather.setTmp_min(dailyForecastObject.getString("tmp_min"));
            weather.setWDate(dailyForecastObject.getString("date"));
            weather.setHumidity(dailyForecastObject.getString("hum"));
            weather.setPressure(dailyForecastObject.getString("pres"));
            weather.setWindSpeed(dailyForecastObject.getString("wind_spd"));
            weather.setWindDirection(dailyForecastObject.getString("wind_dir"));
            if(i==0){
                weather.setWDay("Today");
            }
            if(i==1){
                weather.setWDay("Tomorrow");
            }
            weathers.add(weather);
        }
    }

    public List<Weather> fetchWeather(String location){
        List<Weather>weathers=new ArrayList<>();
        try{
            String url= Uri.parse(HEFENG_API_URL)
                    .buildUpon()
                    .appendQueryParameter("location",location)
                    .appendQueryParameter("key",API_KEY)
                    .build().toString();
            String jsonString=getUrlString(url);
            Log.i(TAG,"JSON Received:"+jsonString);
            JSONObject jsonBody=new JSONObject(jsonString);
            parseJSON(weathers,jsonBody);
        }catch (JSONException je){
            Log.e(TAG,"Failed to parse JSON",je);
        }catch (IOException ioe){
            Log.e(TAG,"Failed to fetch items.",ioe);
        }
        return weathers;
    }

}



