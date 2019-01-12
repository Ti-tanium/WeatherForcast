package com.example.asus.weatherforcast.Notification;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.asus.weatherforcast.R;
import com.example.asus.weatherforcast.weatherMaster.WeatherMasterActivity;

import java.util.concurrent.TimeUnit;

public class PollService extends IntentService {
    private static final String TAG="PollService";
    private static final long POLL_INTERVAL_MS= TimeUnit.MINUTES.toMillis(1);

    public static Intent newIntent(Context context){
        return  new Intent(context,PollService.class);
    }

    public static void setServiceAlarm(Context context,boolean isOn){
        Intent intent=PollService.newIntent(context);
        PendingIntent pendingIntent=PendingIntent.getService(context,0,intent,0);

        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(isOn){
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),POLL_INTERVAL_MS,pendingIntent);
        }else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }

    }

    public static  boolean isServiceAlarmOn(Context context){
        Intent intent=PollService.newIntent(context);
        PendingIntent pendingIntent=PendingIntent.getService(context,0,intent,PendingIntent.FLAG_NO_CREATE);
        return pendingIntent!=null;

    }

    public boolean isNetWorkAvailableAndConnected(){
        ConnectivityManager cm=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable=cm.getActiveNetworkInfo()!=null;
        boolean isNetworkConnected=isNetworkAvailable&&cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(!isNetWorkAvailableAndConnected()){
            return;
        }
        Intent i= WeatherMasterActivity.newIntent(this);
        PendingIntent pi=PendingIntent.getActivity(this,0,i,0);
        String id = "my_channel_01";
        String name="Weather Forecast";
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            Log.i(TAG, mChannel.toString());
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(this,id)
                    .setChannelId(id)
                    .setContentTitle(getString(R.string.notification_title))
                    .setContentText(getText(R.string.notification_detail))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,id)
                    .setContentTitle(getString(R.string.notification_title))
                    .setContentText(getString(R.string.notification_detail))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pi)
                    .setAutoCancel(true);
            notification = notificationBuilder.build();
        }
        notificationManager.notify(111123, notification);
    }

    public PollService(){
        super(TAG);
    }
}
