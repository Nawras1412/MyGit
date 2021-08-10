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

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

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


    @Override
    public void onReceive(Context context, Intent intent){
        System.out.println("alarm journy 33333");
        SharedPreferences sharedPreferences=context
                .getSharedPreferences("U",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String key=intent.getStringExtra("key");
        String title=intent.getStringExtra("title");

        System.out.println("the key in the AlertReceiver is: "+key);
        editor.putString("Current Ring Key",key);
        editor.commit();
//        System.out.println("im in OnReceive");
        stopring=true;
//        SetNewAlarmsIfRepeating(key,usr_name);
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Notification nb = notificationHelper.getChannelNotification(intent.getStringExtra("key")
                ,all_alarms.class,"Alarm!",title,"","alarm","","","","");
        notificationHelper.getManager().notify(key.hashCode(), nb);
        ringtone = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        new CountDownTimer(30*1000, 1000){
            @Override
            public void onTick(long millisUntilFinished){
                System.out.println("the current key is: "+intent.getStringExtra("key"));
                System.out.println("the ring key is in AlertReciever:");
                System.out.println(sharedPreferences.getBoolean("ring "+intent.getStringExtra("key"),false));
                if(sharedPreferences.getBoolean("ring "+key,false))
                    ringtone.play();
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