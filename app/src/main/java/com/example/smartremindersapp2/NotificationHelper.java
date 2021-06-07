//package com.example.smartremindersapp2;
//
//import android.annotation.TargetApi;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.content.Context;
//import android.content.ContextWrapper;
//import android.os.Build;
////import android.support.v4.app.NotificationCompat;
//
//import androidx.core.app.NotificationCompat;
////import android.support.v4.app.RemoteActionCompatParcelizer;
//
//public class NotificationHelper extends ContextWrapper {
//    public static final String channelID = "channelID";
//    public static final String channelName = "Channel Name";
//    private NotificationManager mManager;
//    public NotificationHelper(Context base) {
//        super(base);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            createChannel();
//        }
//    }
//    @TargetApi(Build.VERSION_CODES.O)
//    private void createChannel() {
//        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
//        getManager().createNotificationChannel(channel);
//    }
//    public NotificationManager getManager() {
//        if (mManager == null) {
//            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        }
//        return mManager;
//    }
//
//
//    public NotificationCompat.Builder getChannelNotification() {
//        return new NotificationCompat.Builder(getApplicationContext(), channelID)
//                .setContentTitle("Alarm!")
//                .setContentText("Your AlarmManager is working.")
//                .setSmallIcon(R.drawable.ic_alarm);
//
//    }
//}



package com.example.smartremindersapp2;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private NotificationManager mManager;
    private static String key;


    public static String getKey(){
        return key;
    }


    public  void setKey(String key) {
        this.key = key;
    }


    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
//        else restartService(base);
    }

//    private void restartService(Context context)
//    {
//        Intent restartServiceIntent = new Intent(context, AlertReceiver.class);
//        context.startService(restartServiceIntent);
//
//    }


    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }




    public Notification getChannelNotification(String key) {
        Intent notificationIntent = new Intent(this, all_alarms.class);
        setKey(key);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, channelID)
                .setContentTitle("Alarm!")
                .setContentText("Your AlarmManager is working.")
                .setPriority(Notification.PRIORITY_HIGH) // addition
                .setSmallIcon(R.drawable.ic_alarm).setContentIntent(pendingIntent)
                .setOngoing(true).setCategory(Notification.CATEGORY_SERVICE).build();

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        return notification;
    }
}