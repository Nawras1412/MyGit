//package com.example.smartremindersapp2;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.media.Ringtone;
//import android.media.RingtoneManager;
////import android.support.v4.app.NotificationCompat;
//
//import androidx.core.app.NotificationCompat;
//
//public class AlertReceiver extends BroadcastReceiver {
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        NotificationHelper notificationHelper = new NotificationHelper(context);
//        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
//        notificationHelper.getManager().notify(1, nb.build());
//
//    }
//}



package com.example.smartremindersapp2;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;

public class AlertReceiver extends BroadcastReceiver {
    private static Ringtone ringtone;
    private static boolean stopring;


    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }

    public static Ringtone getRingtone() {
        return ringtone;
    }


    public static void stopRingtone() {
        stopring=false;
    }
    @Override
    public void onReceive(Context context, Intent intent){
        System.out.println("im in OnReceive");
//        context.startService(new Intent(context, NotifierAlarm.class));
        stopring=true;
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Notification nb = notificationHelper.getChannelNotification(intent.getStringExtra("Key"));
        notificationHelper.getManager().notify(0, nb);
        ringtone = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        new CountDownTimer(30*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished){
                if(stopring==true) {
                    ringtone.play();
                    System.out.println("play ringtone");
                }
                else{
                    ringtone.stop();
//                    System.out.println("stop ringtone");
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                ringtone.stop();
            }
        }.start();
    }


}