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
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.media.Ringtone;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        System.out.println("im in NotificationHelper");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
        else restartService(base);
    }

    private void restartService(Context context)
    {
        System.out.println("im in restartService");
        Intent restartServiceIntent = new Intent(context, AlertReceiver.class);
        context.startService(restartServiceIntent);

    }

    //  @RequiresApi(Build.VERSION_CODES.O)
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        System.out.println("im in createChannel");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            getManager().createNotificationChannel(channel);
        }
    }
    public NotificationManager getManager() {
        System.out.println("im in getManager");
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }




    public Notification getChannelNotification(String key,Class returnedPage
            ,String Title,String Content,String cadHTTP,String name,String address,String category,String lat1,String lang1) {
        setKey(key);
        System.out.println("key: "+key);
        System.out.println("111111");
        Intent notificationIntent1 = new Intent(this, returnedPage);
        System.out.println("22222");
        notificationIntent1.putExtra("type","Dismiss");
        notificationIntent1.putExtra("key",key);
        System.out.println("33333");
        PendingIntent pendingIntent1=PendingIntent.getActivity(this,key.hashCode()
                ,notificationIntent1,PendingIntent.FLAG_UPDATE_CURRENT);


        System.out.println("---------------INNN helper");
        System.out.println("category :"+category);
        System.out.println("content :"+Content);
        System.out.println("title :"+Title);
        System.out.println("name :"+name);
        System.out.println("address :"+address);
        System.out.println("lat1 :"+lat1);
        System.out.println("lang1 :"+lang1);
        //System.out.println("cadHTTP :"+cadHTTP);
        Intent notificationIntent2 = new Intent(this, returnedPage);
        notificationIntent2.putExtra("type","Another");
        notificationIntent2.putExtra("cadHTTP",cadHTTP);
        notificationIntent2.putExtra("title",Title);
        notificationIntent2.putExtra("content",Content);
        notificationIntent2.putExtra("key",key);
        notificationIntent2.putExtra("category",category);
        notificationIntent2.putExtra("lat1",lat1);
        notificationIntent2.putExtra("lang1",lang1);
        PendingIntent pendingIntent2=PendingIntent.getActivity(this,key.hashCode()+1
                ,notificationIntent2,PendingIntent.FLAG_UPDATE_CURRENT);


        Intent notificationIntent3 = new Intent(this, returnedPage);
        notificationIntent3.putExtra("key",key);
        notificationIntent3.putExtra("type","DELETE");
        PendingIntent pendingIntent3=PendingIntent.getActivity(this,key.hashCode()+2
                ,notificationIntent3,PendingIntent.FLAG_UPDATE_CURRENT);


        Intent notificationIntent4 = new Intent(this, returnedPage);
        notificationIntent4.putExtra("key",key);
        notificationIntent4.putExtra("type","SNOOZE");
        PendingIntent pendingIntent4=PendingIntent.getActivity(this,key.hashCode()+3
                ,notificationIntent4,PendingIntent.FLAG_UPDATE_CURRENT);
//        Bundle extras = new Bundle();
//        extras.putString("button clicked", "Dismiss");
//        notificationIntent.putExtras(extras);
//        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getApplicationContext());
//        taskStackBuilder.addNextIntentWithParentStack(notificationIntent);
//        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(key.hashCode(), PendingIntent.FLAG_UPDATE_CURRENT);


//        System.out.println("the title issss: "+Title);

//        PendingIntent pendingIntent =
//                PendingIntent.getBroadcast(getApplicationContext(), key.hashCode()
//                        , notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT) ;
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, key.hashCode(), notificationIntent,0);
//        NotificationCompat.Builder builder;
        Notification notification;

        if(cadHTTP!=""){



            //String category= String.valueOf((ref.child("locationAsString").get()));
            //System.out.println("categpry "+  category);
            //NotificationCompat.InboxStyle(NotificationCompat.Builder builder)
            notification = new NotificationCompat.Builder(this, channelID)
                    .setContentTitle(Title+": "+ category +" found nearby ")
                    .setAutoCancel(true)
                    //.setContentInfo(name +" at "+address)
                    .setContentText(name +" at "+address)
                    //.setContentText(Content)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.ic_alarm)
                    .setContentIntent(pendingIntent1)
                    .setOngoing(true).setCategory(Notification.CATEGORY_SERVICE)
                    .addAction(R.drawable.ic_cancel, "Dismiss", pendingIntent1)
                    .addAction(R.drawable.ic_cancel, "Choose Another", pendingIntent2).build();
//            Intent notificationIntent = new Intent(this, returnedPage);
//            Bundle extras2 = new Bundle();
//            extras.putString("button clicked", "Another");
//            extras.putString("cadHTTP", cadHTTP);
//            extras.putString("title", Title);
//            extras.putString("content", Content);
//            extras.putString("key", key);
//            notificationIntent.putExtras(extras);
//            taskStackBuilder = TaskStackBuilder.create(getApplicationContext());
//            taskStackBuilder.addNextIntentWithParentStack(notificationIntent);
//            pendingIntent = taskStackBuilder.getPendingIntent(key.hashCode(), PendingIntent.FLAG_UPDATE_CURRENT);
//
//            builder.addAction(R.drawable.ic_cancel, "Choose Another", pendingIntent);
//            notification=builder.build();
        }
        if (name.equals("alarm")){
            notification = new NotificationCompat.Builder(this, channelID)
                    .setContentTitle(Title)
                    .setContentText(Content)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH) // addition
                    .setSmallIcon(R.drawable.ic_alarm).setContentIntent(pendingIntent1)
                    .setOngoing(true).setCategory(Notification.CATEGORY_SERVICE)
                    .addAction(R.drawable.ic_cancel, "Dismiss", pendingIntent1)
                    .build();

        }
        else{
            notification = new NotificationCompat.Builder(this, channelID)
                    .setContentTitle(Title)
                    .setContentText(Content)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH) // addition
                    .setSmallIcon(R.drawable.ic_alarm).setContentIntent(pendingIntent1)
                    .setOngoing(true).setCategory(Notification.CATEGORY_SERVICE)
                    .addAction(R.drawable.ic_cancel, "SNOOZE", pendingIntent4)
                    .addAction(R.drawable.ic_cancel, "DELETE", pendingIntent3).build();
        }


        notification.flags = Notification.FLAG_AUTO_CANCEL;
        return notification;
    }
}