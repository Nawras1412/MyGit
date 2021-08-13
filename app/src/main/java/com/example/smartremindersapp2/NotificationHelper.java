package com.example.smartremindersapp2;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
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
        else restartService(base);
    }

    private void restartService(Context context)
    {
        Intent restartServiceIntent = new Intent(context, AlertReceiver.class);
        context.startService(restartServiceIntent);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            getManager().createNotificationChannel(channel);
        }
    }
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }


    public Notification getChannelNotification(String key,Class returnedPage
            ,String Title,String Content,String cadHTTP,String name,String address,String category,String lat1,String lang1) {
        setKey(key);
        Intent notificationIntent1 = new Intent(this, returnedPage);
        Intent notificationIntent2 = new Intent(this, returnedPage);
        Intent notificationIntent3 = new Intent(this, returnedPage);
        Intent notificationIntent4 = new Intent(this, returnedPage);

        notificationIntent1.putExtra("type","Dismiss");
        notificationIntent1.putExtra("key",key);


        notificationIntent2.putExtra("type","Another");
        notificationIntent2.putExtra("cadHTTP",cadHTTP);
        notificationIntent2.putExtra("title",Title);
        notificationIntent2.putExtra("content",Content);
        notificationIntent2.putExtra("key",key);
        notificationIntent2.putExtra("category",category);
        notificationIntent2.putExtra("lat1",lat1);
        notificationIntent2.putExtra("lang1",lang1);

        notificationIntent3.putExtra("key",key);
        notificationIntent3.putExtra("type","DELETE");

        notificationIntent4.putExtra("key",key);
        notificationIntent4.putExtra("type","SNOOZE");
        notificationIntent4.putExtra("title",Title);

        //dismiss
        PendingIntent pendingIntent1=PendingIntent.getActivity(this,key.hashCode()
                ,notificationIntent1,PendingIntent.FLAG_UPDATE_CURRENT);
        //another
        PendingIntent pendingIntent2=PendingIntent.getActivity(this,key.hashCode()+1
                ,notificationIntent2,PendingIntent.FLAG_UPDATE_CURRENT);
        //delete
        PendingIntent pendingIntent3=PendingIntent.getActivity(this,key.hashCode()+2
                ,notificationIntent3,PendingIntent.FLAG_UPDATE_CURRENT);
        //snooze
        PendingIntent pendingIntent4=PendingIntent.getActivity(this,key.hashCode()+3
                ,notificationIntent4,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification;

        if(cadHTTP!=""){
            System.out.println("im in if");
            notification = new NotificationCompat.Builder(this, channelID)
                .setContentTitle(Title+": "+ category +" found nearby ")
                .setAutoCancel(true)
                .setContentText(name +" at "+address)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentIntent(pendingIntent1)
                .setOngoing(true).setCategory(Notification.CATEGORY_SERVICE)
                .addAction(R.drawable.ic_cancel, "Dismiss", pendingIntent1)
                .addAction(R.drawable.ic_cancel, "Choose Another", pendingIntent2).build();
        }

        else if (name.equals("alarm")){
            System.out.println("im in else if");
            notification = new NotificationCompat.Builder(this, channelID)
                    .setContentTitle(Title)
                    .setContentText(Content)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH) // addition
                    .setSmallIcon(R.drawable.ic_alarm).setContentIntent(pendingIntent1)
                    .setOngoing(true).setCategory(Notification.CATEGORY_SERVICE)
                    .addAction(R.drawable.ic_cancel, "SNOOZE", pendingIntent4)
                    .addAction(R.drawable.ic_cancel, "Dismiss", pendingIntent1)
                    .build();

        }
        else{
            System.out.println("im in else");
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