//package com.example.smartremindersapp2;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Intent;
//import android.media.Ringtone;
//import android.media.RingtoneManager;
//import android.os.Build;
//import android.os.CountDownTimer;
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
//    private static Ringtone ringtone;
//    private Timer t = new Timer();
//    private static final String CHANNEL_ID = "MyNotificationChannelID";
//    private static boolean stopring;
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    public static Ringtone getRingtone() {
//        return ringtone;
//    }
//
//
//    public static void stopRingtone() {
//        stopring=false;
//    }
//
//
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        System.out.println("Im in onStartCommand");
//        stopring=true;
//        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
//        Notification nb = notificationHelper.getChannelNotification(intent.getStringExtra("Key"));
//        notificationHelper.getManager().notify(0, nb);
////        Intent intent1 = new Intent(context, NotifierAlarm.class);
////        intent1.putExtra("notification",nb);
////        context.startService(intent1);
//
//        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
//        new CountDownTimer(30*1000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished){
//                if(stopring==true) {
//                    ringtone.play();
//                    System.out.println("play ringtone");
//                }
//                else{
//                    ringtone.stop();
////                    System.out.println("stop ringtone");
//                    cancel();
//                }
//            }
//
//            @Override
//            public void onFinish() {
//                ringtone.stop();
//            }
//        }.start();
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
////                .setContentIntent(pendingIntent)
////                .build();
////        notification.flags = Notification.FLAG_AUTO_CANCEL;
////
////        try {
////            startForeground(1, notification);
//////            stopForeground(true);
////            NotificationChannel notificationChannel = null;
////            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
////                System.out.println("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
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
//
//        //stopForeground(true);
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
//////                if(all_alarms.isStop()==true){
//////                    ringtone.stop();
//////                }
////
////            }
////        }, 0, 500);
//
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Override
//    public void onDestroy() {
////        ringtone.stop();
////        t.cancel();
//        System.out.println("im in destroy");
//        super.onDestroy();
//    }
//}

////the problem that i cant save two alarms, i think that after all alarm we initial the channel and i will be an empty
//
//package com.example.smartremindersapp2;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android..content.BroadcastReceiver;
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
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
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

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class NotifierAlarm extends Service {
    private Integer alarmHour;
    private Integer alarmMinute;
    private static Ringtone ringtone;
    private Timer t = new Timer();
    private static final String CHANNEL_ID = "MyNotificationChannelID";
    private static boolean stopring;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public static Ringtone getRingtone() {
        return ringtone;
    }


    public static void setRingtone() {
        stopring=false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        stopring=true;
        alarmHour = intent.getIntExtra("hour", 0);
        alarmMinute = intent.getIntExtra("minutes", 0);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        Intent notificationIntent = new Intent(this, all_alarms.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .addAction(R.drawable.ic_cancel,"remove",pendingIntent)
                .setContentTitle("My Alarm clock")
                .setContentText("Alarm time - " + alarmHour.toString() + " : " + alarmMinute.toString())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                //.setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;



        try {
            startForeground(1, notification);
//            stopForeground(true);
            NotificationChannel notificationChannel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                System.out.println("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
                notificationChannel = new NotificationChannel(CHANNEL_ID, "My Alarm clock Service", NotificationManager.IMPORTANCE_DEFAULT);
            }
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //stopForeground(true);
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (Calendar.getInstance().getTime().getHours() == alarmHour &&
                        Calendar.getInstance().getTime().getMinutes() == alarmMinute &&
                        Calendar.getInstance().getTime().getSeconds()==1 ){
                    startForeground(1, notification);
                }

                if (Calendar.getInstance().getTime().getHours() == alarmHour &&
                        Calendar.getInstance().getTime().getMinutes() == alarmMinute && stopring==true){
                    ringtone.play();

                    //startForeground(1, notification);
                }
                else {

                    ringtone.stop();
                }


            }
        }, 0, 500);

        return super.onStartCommand(intent, flags, startId);
    }

}





