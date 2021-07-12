////package com.example.smartremindersapp2;
////
////import android.app.Notification;
////import android.app.NotificationChannel;
////import android.app.NotificationManager;
////import android.app.PendingIntent;
////import android.app.Service;
////import android.content.Intent;
////import android.media.Ringtone;
////import android.media.RingtoneManager;
////import android.os.Build;
////import android.os.IBinder;
////import androidx.annotation.Nullable;
////import androidx.annotation.RequiresApi;
////import androidx.core.app.NotificationCompat;
////import java.util.Calendar;
////import java.util.Timer;
////import java.util.TimerTask;
////
////
////public class NotifierAlarm extends Service {
////    private Integer alarmHour;
////    private Integer alarmMinute;
////    private static Ringtone ringtone;
////    private Timer t = new Timer();
////    private static final String CHANNEL_ID = "MyNotificationChannelID";
////    private static boolean stopring;
////
////    @Nullable
////    @Override
////    public IBinder onBind(Intent intent) {
////        return null;
////    }
////
////
////    public static Ringtone getRingtone() {
////        return ringtone;
////    }
////
////
////    public static void setRingtone() {
////        stopring=false;
////    }
////
////    @RequiresApi(api = Build.VERSION_CODES.M)
////    @Override
////    public int onStartCommand(Intent intent, int flags, int startId) {
////        stopring=true;
////        alarmHour = intent.getIntExtra("hour", 0);
////        alarmMinute = intent.getIntExtra("minutes", 0);
////        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
////        Intent notificationIntent = new Intent(this, all_alarms.class);
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
////        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
////                .addAction(R.drawable.ic_cancel,"remove",pendingIntent)
////                .setContentTitle("My Alarm clock")
////                .setContentText("Alarm time - " + alarmHour.toString() + " : " + alarmMinute.toString())
////                .setSmallIcon(R.drawable.ic_launcher_foreground)
////                //.setPriority(NotificationCompat.PRIORITY_HIGH)
////                .setContentIntent(pendingIntent)
////                .build();
////        notification.flags = Notification.FLAG_AUTO_CANCEL;
////
////
////
////        try {
////            startForeground(1, notification);
//////            stopForeground(true);
////            NotificationChannel notificationChannel = null;
////            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
////                notificationChannel = new NotificationChannel(CHANNEL_ID, "My Alarm clock Service", NotificationManager.IMPORTANCE_DEFAULT);
////            }
////            NotificationManager notificationManager = getSystemService(NotificationManager.class);
////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////                notificationManager.createNotificationChannel(notificationChannel);
////            }
////        }
////        catch (Exception e){
////            e.printStackTrace();
////        }
////
////        //stopForeground(true);
////        t.scheduleAtFixedRate(new TimerTask() {
////            @Override
////            public void run() {
////
////                if (Calendar.getInstance().getTime().getHours() == alarmHour &&
////                        Calendar.getInstance().getTime().getMinutes() == alarmMinute &&
////                        Calendar.getInstance().getTime().getSeconds()==1 ){
////                    startForeground(1, notification);
////                }
////
////                if (Calendar.getInstance().getTime().getHours() == alarmHour &&
////                        Calendar.getInstance().getTime().getMinutes() == alarmMinute && stopring==true){
////                    ringtone.play();
////
////                    //startForeground(1, notification);
////                }
////                else {
////
////                    ringtone.stop();
////                }
////
////
////            }
////        }, 0, 500);
//////        return Service.START_NOT_STICKY;
////        return super.onStartCommand(intent, flags, startId);
////    }
////
////}
//
//
//
//
//package com.example.smartremindersapp2;
//import android.app.AlarmManager;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.media.Ringtone;
//import android.media.RingtoneManager;
//import android.os.Build;
//import android.os.IBinder;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.core.app.NotificationCompat;
//import java.text.DateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Timer;
//import java.util.TimerTask;
//
//
//public class NotifierAlarm extends Service {
//    private Date date;
//    private static Ringtone ringtone;
//    private Timer t = new Timer();
//    private static final String CHANNEL_ID = "MyNotificationChannelID";
//    private String key;
//    private static boolean stopring;
//    private static boolean killTimer;
//    private int k=0;
//
//
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
//
//    public static Ringtone getRingtone() {
//        return ringtone;
//    }
//
//    public static void stopRingtone() {
//        stopring=false;
//    }
//
//    public static void setKillTimer() {
//        killTimer = true;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        stopring=true;
//        killTimer = false;
//        key=intent.getStringExtra("key");
//        date = new Date(intent.getExtras().getLong("date", -1));
//        //now ringtone is null
//        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
//        Intent notificationIntent = new Intent(this, all_alarms.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, key.hashCode(), notificationIntent, 0);
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .addAction(R.drawable.ic_cancel,"remove",pendingIntent)
//                .setContentTitle("My Alarm clock")
//                .setContentText("Alarm time - " + date)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                //.setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setContentIntent(pendingIntent)
//                .build();
//        notification.flags = Notification.FLAG_AUTO_CANCEL;
//
////        Intent intent2 = new Intent(this, AlertReceiver.class);
////        intent2.putExtra("key", key);
////        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this.getApplicationContext()
////                , key.hashCode(), intent2,0);
////        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
////        alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent2);
//
//        startForeground(0,notification);
////        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
////        Notification nb = notificationHelper.getChannelNotification(intent.getStringExtra("key"));
//
//
//
////            notificationHelper.getManager().notify(0, nb);
////            startForeground(1, notification);
////            NotificationChannel notificationChannel = null;
////            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
////                notificationChannel = new NotificationChannel(CHANNEL_ID, "My Alarm clock Service", NotificationManager.IMPORTANCE_DEFAULT);
////            }
////            NotificationManager notificationManager = getSystemService(NotificationManager.class);
////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////                notificationManager.createNotificationChannel(notificationChannel);
//
//
//
//
//        Intent intent2 = new Intent(this, AlertReceiver.class);
//
////        System.out.println("Date now: "+getDateAsString(cal.getTime()));
////        System.out.println("Date of scheduled alarm: "+getDateAsString(date));
//        //stopForeground(true);
//
//        t.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("im run");
//                k++;
//                if(k==2) {
//                    intent2.putExtra("key", key);
//                    PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext()
//                            , key.hashCode(), intent2, 0);
//                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent2);
//                }
//////                System.out.println(key);
////                if(killTimer) cancel();
////                Calendar cal=Calendar.getInstance();
////                Date dateNow=cal.getTime();
////                System.out.println("run");
////                if(intent.getBooleanExtra("Repeating",false)){
////                    if(dateNow.getHours()==date.getHours() && dateNow.getMinutes()==date.getMinutes() &&
////                            dateNow.getDay()==date.getDay() && dateNow.getSeconds()==0) {
////                        notificationHelper.getManager().notify(0, nb);
////                    }
////                    if(dateNow.getHours()==date.getHours() && dateNow.getMinutes()==date.getMinutes() &&
////                            dateNow.getDay()==date.getDay() && stopring==true) {
////                        ringtone.play();
////                    }
////                    else{
////                        ringtone.stop();
////                    }
////                }else{
////                    if(dateNow.getHours()==date.getHours() && dateNow.getMinutes()==date.getMinutes() &&
////                            dateNow.getDay()==date.getDay() && dateNow.getMonth()==date.getMonth() &&
////                            dateNow.getYear()==date.getYear() && dateNow.getDate()==date.getDate()
////                            && dateNow.getSeconds()==0){
////                        notificationHelper.getManager().notify(0, nb);
////                    }
////                    if(dateNow.getHours()==date.getHours() && dateNow.getMinutes()==date.getMinutes() &&
////                            dateNow.getDay()==date.getDay() && dateNow.getMonth()==date.getMonth() &&
////                            dateNow.getYear()==date.getYear() && dateNow.getDate()==date.getDate()
////                            && stopring==true) {
////                        ringtone.play();
////                    }
////                    else{
////                        ringtone.stop();
////                    }
////                }
//
//            }
//        }, 0, 500);
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//
//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//        System.out.println("bbbbbbbbbbbbbbbbbbbb");
//        ringtone.stop();
//        t.cancel();
//        stopring=false;
//    }
//}




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
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class NotifierAlarm extends Service {
    private Date date;
    private static Ringtone ringtone;
    private Timer t = new Timer();
    private static final String CHANNEL_ID = "MyNotificationChannelID";
    private String key;
    private static boolean stopring;
    private static boolean killTimer;
    private int k;


    public static void setKillTimer() {
        killTimer = true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    public static Ringtone getRingtone() {
        return ringtone;
    }

    public static void stopRingtone() {
        stopring=false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        k=0;
        stopring=true;
        killTimer=false;
        Calendar cal=Calendar.getInstance();
        key=intent.getStringExtra("key");
        date = new Date(intent.getExtras().getLong("date", -1));
        Integer pendingKey=intent.getIntExtra("Pending_key",0);
        Intent notificationIntent = new Intent(this, all_alarms.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, pendingKey , notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .addAction(R.drawable.ic_cancel,"remove",pendingIntent)
                .setContentTitle("My Alarm clock")
                .setContentText("Alarm time - " + date)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        startForeground(1,notification);

        SharedPreferences sharedPreferences=getSharedPreferences("U",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("AlarmsNum",sharedPreferences.getInt("AlarmsNum",0)+1);
        editor.putBoolean("ring "+key,true);
        editor.commit();
        Intent intent2 = new Intent(this, AlertReceiver.class);
        t.scheduleAtFixedRate(new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run(){
                if(sharedPreferences.getInt("AlarmsNum",0)==0){
                    cancel();
                }
                System.out.println("im run");
                k++;
                if(k==2) {
                    intent2.putExtra("key", key);
                    intent2.putExtra("userName",intent.getStringExtra("userName"));
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext()
                            , pendingKey, intent2, 0);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent2);

                }
            }
        }, 0, 500);
        return super.onStartCommand(intent, flags, startId);
    }
}