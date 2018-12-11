package com.example.asus.weatherforcast.map;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.asus.weatherforcast.R;
import com.example.asus.weatherforcast.Weather;

import static android.content.ContentValues.TAG;


public class MapFragment extends Fragment {
    private MapView mMapView = null;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_map,container,false);
        mMapView=(MapView)v.findViewById(R.id.map);

        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.setMyLocationEnabled(true);

        // get latitude and longitude
        GeocodeSearch geocodeSearch = new GeocodeSearch(getActivity());
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                Log.d(TAG, "onGeocodeSearched: ");
                if (geocodeResult != null
                        && geocodeResult.getGeocodeAddressList() != null
                        && geocodeResult.getGeocodeAddressList().size() > 0) {
                    GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                    double latitude = geocodeAddress.getLatLonPoint().getLatitude();
                    double longitude = geocodeAddress.getLatLonPoint().getLongitude();
                    aMap.moveCamera(
                            CameraUpdateFactory.changeLatLng(new LatLng(latitude, longitude)));
                }
            }
        });
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String cityName=sharedPreferences.getString(getString(R.string.KEY_SETTING_LOCATION),"长沙");
        GeocodeQuery geocodeQuery = new GeocodeQuery(cityName, cityName);
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
