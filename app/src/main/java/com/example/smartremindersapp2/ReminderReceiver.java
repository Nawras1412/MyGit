package com.example.smartremindersapp2;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.CountDownTimer;
import android.os.IBinder;

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }


    @Override
    public void onReceive(Context context, Intent intent){
        System.out.println("im in OnReceive");
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Notification nb = notificationHelper.getChannelNotification(intent.getStringExtra("key")
                ,HomePage.class,"Reminder","You are a distance of less than 3000 from the destination");
        notificationHelper.getManager().notify(0, nb);
    }
}
