   //
////the problem that i cant save two alarms, i think that after all alarm we initial the channel and i will be an empty
//
//package com.example.smartremindersapp2;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.media.Ringtone;
//import android.media.RingtoneManager;
//import android.os.Build;
//import android.os.IBinder;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.core.app.NotificationCompat;
//import java.util.Calendar;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class NotifierAlarm extends Service {
//    private Integer alarmHour;
//    private Integer alarmMinute;
//    private Ringtone ringtone;
//    private Timer t = new Timer();
//    private static final String CHANNEL_ID = "MyNotificationChannelID";
//
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        System.out.println("vvvvvvvvvvvvvvvvvvvvvvv");
//        return null;
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        alarmHour = intent.getIntExtra("hour", 0);
//        alarmMinute = intent.getIntExtra("minutes", 0);
//        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
//        Intent notificationIntent = new Intent(this, all_alarms.class);
////        notificationIntent.putExtra("User Name",intent.getStringExtra("User Name"));
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("My Alarm clock")
//                .setContentText("Alarm time - " + alarmHour.toString() + " : " + alarmMinute.toString())
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentIntent(pendingIntent)
//                .build();
//
//
//
//        t.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                if (Calendar.getInstance().getTime().getHours() == alarmHour &&
//                        Calendar.getInstance().getTime().getMinutes() == alarmMinute){
//                    ringtone.play();
//                    try {
//                        startForeground(1, notification);
//                        NotificationChannel notificationChannel = null;
//                        if (android.os.Build..VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                            System.out.println("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
//                            notificationChannel = new NotificationChannel(CHANNEL_ID, "My Alarm clock Service", NotificationManager.IMPORTANCE_DEFAULT);
//                        }
//                        NotificationManager notificationManager = getSystemService(NotificationManager.class);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            notificationManager.createNotificationChannel(notificationChannel);
//                        }
//
//                    }
//                    catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                }
//                else {
//                    ringtone.stop();
//                }
//
//            }
//        }, 0, 1000);
//
//        return super.onStartCommand(intent, flags, startId);
//    }
//
////    @Override
////    public void onDestroy() {
////        ringtone.stop();
////        t.cancel();
////        super.onDestroy();
////    }
//}
//
//
//

package com.example.smartremindersapp2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class NotifierRemind extends Service {

    private static Ringtone ringtone;
    private Timer t = new Timer();
    private static final String CHANNEL_ID = "MyNotificationChannelID2";
    private String key;
    private static boolean stopring;
    private static boolean killTimer;
    private int k;
    private double lat, lng;

    private Timer t2 = new Timer();
    private Location location;
    private boolean state;
    private String name;
    private SharedPreferences msharedPreferences;
    private SharedPreferences.Editor editor;
    //   private static final String CHANNEL_ID = "MyNotificationChannelID";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //reminderHour = intent.getIntExtra("hour", 0);
        //reminderMinute = intent.getIntExtra("minutes", 0);
        msharedPreferences = getSharedPreferences("U", MODE_PRIVATE);
        editor = msharedPreferences.edit();

        lat = intent.getDoubleExtra("lat", 0);
        lng = intent.getDoubleExtra("lng", 0);
        state = intent.getBooleanExtra("state", false);
        key = intent.getStringExtra("key");
        name = intent.getStringExtra("name");
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_launcher)
//                        .setContentTitle("Notifications Example")
//                        .setContentText("This is a test notification");
//

        //  Intent notificationIntent = new Intent(this, MenuScreen.class);

        //  PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
        //        PendingIntent.FLAG_UPDATE_CURRENT);

        //  builder.setContentIntent(contentIntent);
        // builder.setAutoCancel(true);
        //builder.setLights(Color.BLUE, 500, 500);
        //long[] pattern = {500,500,500,500,500,500,500,500,500};
        // builder.setVibrate(pattern);
        // builder.setStyle(new NotificationCompat.InboxStyle());
// Add as notification
        int importance = NotificationManager.IMPORTANCE_HIGH;


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


//
        Intent notificationIntent = new Intent(this, HomePage.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("My location clock").setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)//.addAction(R.drawable.ic_cancel, "remove",removeIntent)
                .setContentIntent(pendingIntent).setSound(alarmSound)
                .setContentText("it works")
                .setPriority(NotificationCompat.PRIORITY_HIGH).build();
        notification.flags =  Notification.FLAG_AUTO_CANCEL;
        //.build();

        //startForeground(1,notification);

//
//        Intent notificationIntent = new Intent(this, HomePage.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("My Reminder")
//                .addAction(R.drawable.ic_cancel,"remove",pendingIntent)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentIntent(pendingIntent)
//                .build();
        try {
            //startForeground(1, notification);
            //stopForeground(true);
            NotificationChannel notificationChannel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                notificationChannel = new NotificationChannel(CHANNEL_ID, "My Reminder Service", NotificationManager.IMPORTANCE_HIGH);
            }
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(notificationChannel);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(1, notification);

        //stopForeground(true);
        t2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                float[] distance = new float[1];
                double lat1 = 32.7614296;
                double lng1 = 35.0195184;
                String userName = getSharedPreferences("U", MODE_PRIVATE).
                        getString("username", null);

                Location.distanceBetween(lat, lng, lat1, lng1, distance);
                // distance[0] is now the distance between these lat/lons in meters
                System.out.println("Distanceee");
                System.out.println(distance[0]);

                if ((distance[0] < 3000) && (state == true)) {
                    System.out.println("><><><><><><>< gottt innnnnnnnnnnnnn");
                    startForeground(1, notification);
                    if (state == false) {
                        stopForeground(true);
                    }
                }
            }
        }, 0, 10);

        return super.onStartCommand(intent, flags, startId);
        //return 0;
    }
}

//        return super.onStartCommand(intent, flags, startId);
//    }

                    //ringtone.play();
//                    DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("reminder_list").child(key);
//
//                    HashMap hashmap = new HashMap();
//                    hashmap.put("state", false);
//                    ref.updateChildren(hashmap);

                //}
//                if (Calendar.getInstance().getTime().getHours() == reminderHour &&
//                        Calendar.getInstance().getTime().getMinutes() == reminderMinute){
//                    ringtone.play();
//                    //startForeground(1, notification);
//                }
//                else {
//                    ringtone.stop();
//                }

//            }
//        }, 0, 500);
//
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Override
//    public void onDestroy() {
//        ringtone.stop();
//        t.cancel();
//        super.onDestroy();
//    }
//}
//




//
//package com.example.smartremindersapp2;
//
//        import android.app.Notification;
//        import android.app.NotificationChannel;
//        import android.app.NotificationManager;
//        import android.app.PendingIntent;
//        import android.app.Service;
//        import android.content.Intent;
//        import android.media.Ringtone;
//        import android.media.RingtoneManager;
//        import android.os.Build;
//        import android.os.IBinder;
//        import android.view.View;
//
//        import androidx.annotation.Nullable;
//        import androidx.annotation.RequiresApi;
//        import androidx.core.app.NotificationCompat;
//
//        import com.google.android.material.snackbar.Snackbar;
//
//        import java.util.Calendar;
//        import java.util.Timer;
//        import java.util.TimerTask;
//
//public class NotifierRemind extends Service {
//    private Integer reminderHour;
//    private Integer reminderMinute;
//    private Ringtone ringtone;
//    private Timer t2 = new Timer();
//    private static final String CHANNEL_ID = "MyNotificationChannelID";
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        reminderHour = intent.getIntExtra("hour", 0);
//        reminderMinute = intent.getIntExtra("minutes", 0);
//        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
//        Intent notificationIntent = new Intent(this, HomePage.class);
//        notificationIntent.putExtra("User Name",intent.getStringExtra("User Name"));
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("My Reminder")
//                .setContentText("Reminder time - " + reminderHour.toString() + " : " + reminderMinute.toString())
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentIntent(pendingIntent)
//                .build();
//
//
//
//
//
//
//        try {
//            startForeground(1, notification);
//            //stopForeground(true);
//            NotificationChannel notificationChannel = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                notificationChannel = new NotificationChannel(CHANNEL_ID, "My Reminder Service", NotificationManager.IMPORTANCE_DEFAULT);
//            }
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                notificationManager.createNotificationChannel(notificationChannel);
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//        //stopForeground(true);
//        t2.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                if (Calendar.getInstance().getTime().getHours() == reminderHour &&
//                        Calendar.getInstance().getTime().getMinutes() == reminderMinute &&
//                        (Calendar.getInstance().getTime().getSeconds()==1 || Calendar.getInstance().getTime().getSeconds()==2)){
//                    startForeground(1, notification);
//                }
////                if (Calendar.getInstance().getTime().getHours() == reminderHour &&
////                        Calendar.getInstance().getTime().getMinutes() == reminderMinute){
////                    ringtone.play();
////                    //startForeground(1, notification);
////                }
////                else {
////                    ringtone.stop();
////                }
//
//            }
//        }, 0, 500);
//
//
//
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//
//}
//
//
//
//
//
////package com.example.smartremindersapp2;
////
////import android.app.Notification;
////import android.app.NotificationChannel;
////import android.app.NotificationManager;
////import android.app.PendingIntent;
////import android.app.Service;
////import android.content.Context;
////import android.content.Intent;
////import android.media.Ringtone;
////import android.media.RingtoneManager;
////import android.os.Build;
////import android.os.IBinder;
////import android.view.View;
////
////import androidx.annotation.Nullable;
////import androidx.annotation.RequiresApi;
////import androidx.core.app.NotificationCompat;
////
////import com.google.android.material.snackbar.Snackbar;
////
////import java.util.Calendar;
////import java.util.Timer;
////import java.util.TimerTask;
////
////public class NotifierRemind extends Service {
////    private Integer reminderHour;
////    private Integer reminderMinute;
////    private Ringtone ringtone;
////    private Timer t2 = new Timer();
////    private static final String CHANNEL_ID = "MyNotificationChannelID";
////
////    @Nullable
////    @Override
////    public IBinder onBind(Intent intent) {
////        return null;
////    }
////
////
////
////    @RequiresApi(api = Build.VERSION_CODES.M)
////    @Override
////    public int onStartCommand(Intent intent, int flags, int startId) {
////        reminderHour = intent.getIntExtra("hour", 0);
////        reminderMinute = intent.getIntExtra("minutes", 0);
////        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
//////        Intent notificationIntent = new Intent(this, HomePage.class);
//////        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//////        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//////                .setContentTitle("My Reminder")
//////                .setContentText("Reminder time - " + reminderHour.toString() + " : " + reminderMinute.toString())
//////                .setSmallIcon(R.drawable.ic_launcher_foreground)
//////                .setContentIntent(pendingIntent)
//////                .build();
////        long when = System.currentTimeMillis(); //now
////        Context context = getApplicationContext();
////        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
////        Notification notification = new Notification(R.drawable.ic_baseline_add_alarm_24, "message", when);
////
////        Intent notificationIntent = new Intent(context, HomePage.class);
////
////        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
////                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
////
////        PendingIntent intent2 = PendingIntent.getActivity(context, 0,
////                notificationIntent, 0);
////
////        notification.setLatestEventInfo(context, "title", "message", intent2);
////        notification.flags |= Notification.FLAG_AUTO_CANCEL;
////        notificationManager.notify(0, notification);
////
////
////
//////        try {
//////            startForeground(1, notification);
//////            stopForeground(true);
//////            NotificationChannel notificationChannel = null;
//////            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//////                notificationChannel = new NotificationChannel(CHANNEL_ID, "My Reminder Service", NotificationManager.IMPORTANCE_DEFAULT);
//////            }
//////            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//////                notificationManager.createNotificationChannel(notificationChannel);
//////            }
//////        }
//////        catch (Exception e){
//////            e.printStackTrace();
//////        }
//////
//////        //stopForeground(true);
//////        t2.scheduleAtFixedRate(new TimerTask() {
//////            @Override
//////            public void run() {
//////                if (Calendar.getInstance().getTime().getHours() == reminderHour &&
//////                        Calendar.getInstance().getTime().getMinutes() == reminderMinute &&
//////                        Calendar.getInstance().getTime().getSeconds()==1){
//////                    startForeground(1, notification);
//////
//////                }
////////                if (Calendar.getInstance().getTime().getHours() == reminderHour &&
////////                        Calendar.getInstance().getTime().getMinutes() == reminderMinute){
////////                    ringtone.play();
////////                    //startForeground(1, notification);
////////                }
////////                else {
////////                    ringtone.stop();
////////                }
//////
//////            }
//////        }, 0, 500);
////
////        return super.onStartCommand(intent, flags, startId);
////    }
////
////
////}
