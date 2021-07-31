
package com.example.smartremindersapp2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;

public class NotifierLocationRemind extends Service {
    private static final String CHANNEL_ID = "MyNotificationChannelID";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Calendar calendar=Calendar.getInstance();
        Date date = calendar.getTime();
        String ContentTitle;
        String ContentText;
        if(intent.getStringExtra("type").equals("DateRemind")){
            date = new Date(intent.getExtras().getLong("date", -1));
            ContentTitle="My Date Reminder";
            ContentText="Show your reminder";
        }
        else{
            ContentTitle="My Location Reminder";
            ContentText="You are a distance of less than 3000 from the destination";
        }
        String key=intent.getStringExtra("key");
        Integer pendingKey=intent.getIntExtra("Pending_key",0);
        Intent notificationIntent = new Intent(this, HomePage.class);
        System.out.println("the pending key is: "+pendingKey);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, pendingKey , notificationIntent, 0);

        if(intent.getStringExtra("type").equals("DateRemind")) {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .addAction(R.drawable.ic_cancel, "remove", pendingIntent)
                    .setContentTitle(ContentTitle)
                    .setContentText(ContentText)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)
                    .build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            startForeground(1, notification);
        }

        Intent intent2 = new Intent(this, ReminderReceiver.class);
        intent2.putExtra("key",key);
        intent2.putExtra("userName",intent.getStringExtra("userName"));
        intent2.putExtra("title",intent.getStringExtra("title"));
        if(intent.getStringExtra("type").equals("LocationRemind")){
            intent2.putExtra("locationType",intent.getStringExtra("locationType"));
        }

        intent2.putExtra("ContentTitle",ContentTitle);
        intent2.putExtra("ContentText",ContentText);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext()
                , pendingKey, intent2, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent2);
        return super.onStartCommand(intent, flags, startId);
    }
}














//package com.example.smartremindersapp2;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.location.Location;
//import android.media.Ringtone;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.IBinder;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.core.app.NotificationCompat;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class NotifierRemind extends Service {
//
//    private static Ringtone ringtone;
//    private Timer t = new Timer();
//    private static final String CHANNEL_ID = "MyNotificationChannelID2";
//    private String key;
//    private static boolean stopring;
//    private static boolean killTimer;
//    private int k;
//    private double lat, lng;
//
//    private Timer t2 = new Timer();
//    private Location location;
//    private boolean state;
//    private String name;
//    private SharedPreferences msharedPreferences;
//    private SharedPreferences.Editor editor;
//    //   private static final String CHANNEL_ID = "MyNotificationChannelID";
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        //reminderHour = intent.getIntExtra("hour", 0);
//        //reminderMinute = intent.getIntExtra("minutes", 0);
//        msharedPreferences = getSharedPreferences("U", MODE_PRIVATE);
//        editor = msharedPreferences.edit();
//
//        lat = intent.getDoubleExtra("lat", 0);
//        lng = intent.getDoubleExtra("lng", 0);
//        state = intent.getBooleanExtra("state", false);
//        key = intent.getStringExtra("key");
//        name = intent.getStringExtra("name");
//        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//
////        NotificationCompat.Builder builder =
////                new NotificationCompat.Builder(this)
////                        .setSmallIcon(R.drawable.ic_launcher)
////                        .setContentTitle("Notifications Example")
////                        .setContentText("This is a test notification");
////
//
//        //  Intent notificationIntent = new Intent(this, MenuScreen.class);
//
//        //  PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//        //        PendingIntent.FLAG_UPDATE_CURRENT);
//
//        //  builder.setContentIntent(contentIntent);
//        // builder.setAutoCancel(true);
//        //builder.setLights(Color.BLUE, 500, 500);
//        //long[] pattern = {500,500,500,500,500,500,500,500,500};
//        // builder.setVibrate(pattern);
//        // builder.setStyle(new NotificationCompat.InboxStyle());
//// Add as notification
//        int importance = NotificationManager.IMPORTANCE_HIGH;
//
//
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//
////
//        Intent notificationIntent = new Intent(this, HomePage.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("My location clock").setAutoCancel(true)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)//.addAction(R.drawable.ic_cancel, "remove",removeIntent)
//                .setContentIntent(pendingIntent).setSound(alarmSound)
//                .setContentText("it works")
//                .setPriority(NotificationCompat.PRIORITY_HIGH).build();
//        notification.flags =  Notification.FLAG_AUTO_CANCEL;
//        //.build();
//
//        //startForeground(1,notification);
//
////
////        Intent notificationIntent = new Intent(this, HomePage.class);
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
////        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
////                .setContentTitle("My Reminder")
////                .addAction(R.drawable.ic_cancel,"remove",pendingIntent)
////                .setSmallIcon(R.drawable.ic_launcher_foreground)
////                .setContentIntent(pendingIntent)
////                .build();
//        try {
//            //startForeground(1, notification);
//            //stopForeground(true);
//            NotificationChannel notificationChannel = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//
//                notificationChannel = new NotificationChannel(CHANNEL_ID, "My Reminder Service", NotificationManager.IMPORTANCE_HIGH);
//            }
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                notificationManager.createNotificationChannel(notificationChannel);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
////        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////        manager.notify(1, notification);
//
//        //stopForeground(true);
//        t2.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                float[] distance = new float[1];
//                double lat1 = 32.7614296;
//                double lng1 = 35.0195184;
//                String userName = getSharedPreferences("U", MODE_PRIVATE).
//                        getString("username", null);
//
//                Location.distanceBetween(lat, lng, lat1, lng1, distance);
//                // distance[0] is now the distance between these lat/lons in meters
//                System.out.println("Distanceee");
//                System.out.println(distance[0]);
//
//                if ((distance[0] < 3000) && (state == true)) {
//                    System.out.println("><><><><><><>< gottt innnnnnnnnnnnnn");
//                    startForeground(1, notification);
//                    if (state == false) {
//                        stopForeground(true);
//                    }
//                }
//            }
//        }, 0, 10);
//
//        return super.onStartCommand(intent, flags, startId);
//        //return 0;
//    }
//}