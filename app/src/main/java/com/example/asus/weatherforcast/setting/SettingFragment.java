package com.example.asus.weatherforcast.setting;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.example.asus.weatherforcast.Notification.PollService;
import com.example.asus.weatherforcast.R;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;


public class SettingFragment extends PreferenceFragmentCompat implements BDLocationListener {
    private Preference mLocationPreference;
    private Preference mUnitPreference;
    private Preference mNotificationPreference;
    private List<HotCity> hotCities;

    private SharedPreferences mSharedPreferences;
    private Activity mActivity;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_settings,rootKey);
        mSharedPreferences=PreferenceManager.getDefaultSharedPreferences(getActivity());
        mActivity=getActivity();

        // initiate

        mLocationPreference=findPreference(getString(R.string.KEY_SETTING_LOCATION));
        mUnitPreference=findPreference(getString(R.string.KEY_SETTING_TEMPERATUREUNITS));
        mNotificationPreference=findPreference(getString(R.string.KEY_SETTING_WEATHERNOTIFICATION));
        hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100")); //code为城市代码
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));

        // set summary
        boolean isBritishSystem=mSharedPreferences.getBoolean(getString(R.string.KEY_SETTING_TEMPERATUREUNITS),false);
        mUnitPreference.setSummary(isBritishSystem?"British":"Metric");
        mLocationPreference.setSummary(mSharedPreferences.getString(getString(R.string.KEY_SETTING_LOCATION),"长沙"));
        mNotificationPreference.setSummary(mSharedPreferences.getBoolean(getString(R.string.KEY_SETTING_WEATHERNOTIFICATION),false)?getString(R.string.enabled):getString(R.string.disabled));


        mLocationPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                pickCity(preference);
                return true;
            }
        });

        mUnitPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((boolean)newValue?"British":"Metric");
                return true;
            }
        });


        mNotificationPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean isNotificationOn=(boolean)newValue;
                preference.setSummary(isNotificationOn?getString(R.string.enabled):getString(R.string.disabled));
                return true;
            }
        });


    }




    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void pickCity(final Preference preference){
        CityPicker.from(SettingFragment.this) //activity或者fragment
                .enableAnimation(true)	//启用动画效果，默认无
                .setLocatedCity(null)//APP自身已定位的城市，传null会自动定位（默认）
                .setHotCities(hotCities)	//指定热门城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        Toast.makeText(getContext().getApplicationContext(), data.getName(), Toast.LENGTH_SHORT).show();
                        Log.d("AAAonPickCity:",data.getCode().toString()+data.getName().toString()+data.getPinyin().toString()+data.getProvince().toString()+data.getSection().toString());
                        mSharedPreferences.edit().putString(getString(R.string.KEY_SETTING_LOCATION),data.getName()).apply();
                        preference.setSummary(data.getName());
                    }

                    @Override
                    public void onCancel(){
                        Toast.makeText(getContext().getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLocate() {
                        Log.i("AAACityLocate","called");
                        List<String >permissionList=new ArrayList<>();
                        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
                        }
                        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
                            permissionList.add(Manifest.permission.READ_PHONE_STATE);
                        }
                        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }
                        if(!permissionList.isEmpty()){
                            String [] permissions=permissionList.toArray(new String[permissionList.size()]);
                            ActivityCompat.requestPermissions(getActivity(),permissions,1);
                        }else {
                            Log.i("AAA else","requestLocation called");
                            requestLocation();
                        }
                        //定位接口，需要APP自身实现，这里模拟一下定位
                    }
                })
                .show();
    }
}
