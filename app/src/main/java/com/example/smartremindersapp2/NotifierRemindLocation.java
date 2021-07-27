//
//
////
//////the problem that i cant save two alarms, i think that after all alarm we initial the channel and i will be an empty
////
////package com.example.smartremindersapp2;
////
////import android.app.Notification;
////import android.app.NotificationChannel;
////import android.app.NotificationManager;
////import android.app.PendingIntent;
////import android.app.Service;
////import android.content.BroadcastReceiver;
////import android.content.Context;
////import android.content.Intent;
////import android.content.IntentFilter;
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
////public class NotifierAlarm extends Service {
////    private Integer alarmHour;
////    private Integer alarmMinute;
////    private Ringtone ringtone;
////    private Timer t = new Timer();
////    private static final String CHANNEL_ID = "MyNotificationChannelID";
////
////
////    @Override
////    public IBinder onBind(Intent intent) {
////        System.out.println("vvvvvvvvvvvvvvvvvvvvvvv");
////        return null;
////    }
////
////
////    @RequiresApi(api = Build.VERSION_CODES.M)
////    @Override
////    public int onStartCommand(Intent intent, int flags, int startId) {
////        alarmHour = intent.getIntExtra("hour", 0);
////        alarmMinute = intent.getIntExtra("minutes", 0);
////        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
////        Intent notificationIntent = new Intent(this, all_alarms.class);
//////        notificationIntent.putExtra("User Name",intent.getStringExtra("User Name"));
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
////        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
////                .setContentTitle("My Alarm clock")
////                .setContentText("Alarm time - " + alarmHour.toString() + " : " + alarmMinute.toString())
////                .setSmallIcon(R.drawable.ic_launcher_foreground)
////                .setContentIntent(pendingIntent)
////                .build();
////
////
////
////        t.scheduleAtFixedRate(new TimerTask() {
////            @Override
////            public void run() {
////                if (Calendar.getInstance().getTime().getHours() == alarmHour &&
////                        Calendar.getInstance().getTime().getMinutes() == alarmMinute){
////                    ringtone.play();
////                    try {
////                        startForeground(1, notification);
////                        NotificationChannel notificationChannel = null;
////                        if (android.os.Build..VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
////                            System.out.println("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
////                            notificationChannel = new NotificationChannel(CHANNEL_ID, "My Alarm clock Service", NotificationManager.IMPORTANCE_DEFAULT);
////                        }
////                        NotificationManager notificationManager = getSystemService(NotificationManager.class);
////                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////                            notificationManager.createNotificationChannel(notificationChannel);
////                        }
////
////                    }
////                    catch (Exception e){
////                        e.printStackTrace();
////                    }
////
////                }
////                else {
////                    ringtone.stop();
////                }
////
////            }
////        }, 0, 1000);
////
////        return super.onStartCommand(intent, flags, startId);
////    }
////
//////    @Override
//////    public void onDestroy() {
//////        ringtone.stop();
//////        t.cancel();
//////        super.onDestroy();
//////    }
////}
////
////
////
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
//
//        import androidx.annotation.Nullable;
//        import androidx.annotation.RequiresApi;
//        import androidx.core.app.NotificationCompat;
//
//        import java.util.Calendar;
//        import java.util.Timer;
//        import java.util.TimerTask;
//
//public class NotifierRemindLocation extends Service {
//    private String title;
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
//        title = intent.getStringExtra("title");
//        //reminderMinute = intent.getIntExtra("minutes", 0);
//        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//        Intent notificationIntent = new Intent(this, HomePage.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("My Reminder")
//                .setContentText("Reminder For - " + title)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentIntent(pendingIntent)
//                .build();
//        try {
//            startForeground(1, notification);
//            //stopForeground(true);
//            NotificationChannel notificationChannel = null;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
////                if (Calendar.getInstance().getTime().getHours() == reminderHour &&
////                        Calendar.getInstance().getTime().getMinutes() == reminderMinute &&
////                        Calendar.getInstance().getTime().getSeconds()==1){
//                startForeground(1, notification);
//
//                //  }
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
