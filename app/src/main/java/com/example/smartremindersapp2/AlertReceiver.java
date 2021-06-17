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
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

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
        SharedPreferences sharedPreferences=context
                .getSharedPreferences("U",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String key=intent.getStringExtra("key");
        System.out.println("the key in the AlertReceiver is: "+key);
        editor.putString("Current Ring Key",key);
        editor.commit();
        System.out.println("im in OnReceive");
        stopring=true;

        NotificationHelper notificationHelper = new NotificationHelper(context);
        Notification nb = notificationHelper.getChannelNotification(intent.getStringExtra("key"));
        notificationHelper.getManager().notify(0, nb);
        ringtone = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        new CountDownTimer(30*1000, 1000){
            @Override
            public void onTick(long millisUntilFinished){
                System.out.println("the current key is: "+intent.getStringExtra("key"));
                System.out.println("the ring key is in AlertReciever:");
                System.out.println(sharedPreferences.getBoolean("ring "+intent.getStringExtra("key"),false));
                if(sharedPreferences.getBoolean("ring "+key,true))
                    ringtone.play();
//                if(stopring==true) {
//
//                    System.out.println("play ringtone");
//                }
                else{
                    ringtone.stop();
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