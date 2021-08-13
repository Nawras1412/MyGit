package com.example.smartremindersapp2;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MyBackgroundService extends Service {
    private static final String CHANNEL_ID ="location_channel";
    private static final String EXTRA_STARTED_FROM_NOTIFICATION ="com.example.smartremindersapp2"+"started_from_notification";
    private final IBinder mBinder = new LocalBinder();
    private static final long UPDATE_INTERVAL_IN_MIL = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MUL = UPDATE_INTERVAL_IN_MIL/2;
    private static final int NOTI_ID  = 1223;
    private Boolean mChangingConfigiration=false;
    private NotificationManager mNotificationManager;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private Handler mServiceHandler;
    private Location mLocation;

    public MyBackgroundService(){}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean startedFromNotification =intent.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION,false);
        if (startedFromNotification) {
            removeLocationUpdates();
            stopSelf();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mChangingConfigiration=true;
    }


    //if no more location reminders?
    private void removeLocationUpdates() {
        try{
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            CommonL.setRequestingLocationUpdates(this,false);
            stopSelf();;
        }catch (SecurityException ex){
            CommonL.setRequestingLocationUpdates(this,true);
            Log.e("SmartReminders2","Lost location permission.Could not remove updates. "+ex);
        }
    }

    @Override
    public void onCreate(){
        //super.onCreate();
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        locationCallback =new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult){
                super.onLocationResult(locationResult);
                onNewLocation(locationResult.getLastLocation());
            }
        };
        createLocationRequest();
        getLastLocation();
        HandlerThread handlerThread=new HandlerThread("SmartReminders2");
        handlerThread.start();
        mServiceHandler=new Handler(handlerThread.getLooper());
        mNotificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID,getString(R.string.app_name),NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    private void getLastLocation() {
        try {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        mLocation = task.getResult();
                    }
                    else
                        Log.e("SmartReminders2", "Failed to get location");
                }
            });
        } catch (SecurityException ex){
            Log.e("SmartReminders2", "Lost location permission"+ex);
        }
    }

    private void createLocationRequest() {
        locationRequest=new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MIL);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MUL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    private void onNewLocation(Location lastLocation) {
        mLocation=lastLocation;
        HomePage.getInstance().backgroundLocation(mLocation);
        if (serviceIsRunningInForeGround(this))
            mNotificationManager.notify(NOTI_ID,getNotification());
    }

    private Notification getNotification() {
        HomePage.getInstance().backgroundLocation(mLocation);
        Intent intent =new Intent(this,MyBackgroundService.class);
        String text = CommonL.getLocationText(mLocation);
        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION,true);
        PendingIntent servicePendingIntent =PendingIntent.getService(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this,0,new Intent(this,HomePage.class),0);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
                .setContentTitle("SmartReminders Location usage")
                .setContentText("the app is using location services in the background")
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_LOW)
                .setTicker(text)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setWhen(System.currentTimeMillis());
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
            builder.setChannelId(CHANNEL_ID);
        return builder.setNotificationSilent().build();
    }

    private boolean serviceIsRunningInForeGround(Context context) {
        ActivityManager manager=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service:manager.getRunningServices(Integer.MAX_VALUE))
            if(getClass().getName().equals((service.service.getClassName())))
                if(service.foreground)
                    return true;

        return false;
    }

    public void requestLocationUpdates() {
        CommonL.setRequestingLocationUpdates(this,true);
        startService(new Intent(getApplicationContext(),MyBackgroundService.class));
        try {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback,Looper.myLooper());
        }catch (SecurityException ex) {
            Log.e("SmartReminders2","Lost location permission. Could not request it "+ex);
        }
    }

    public class LocalBinder extends Binder {
        MyBackgroundService getService(){
            return MyBackgroundService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        stopForeground(true);
        mChangingConfigiration=false;
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        stopForeground(true);
        mChangingConfigiration=false;
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if(!mChangingConfigiration && CommonL.requestionLocationUpdates(this))
            //notify in background when closed
        startForeground(NOTI_ID,getNotification());
        return true;
    }

    @Override
    public void onDestroy() {
        mServiceHandler.removeCallbacks(null );
        super.onDestroy();
    }
}