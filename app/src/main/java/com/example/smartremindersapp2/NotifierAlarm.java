package com.example.smartremindersapp2;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class NotifierAlarm extends Service {
    private Date date;
    private static Ringtone ringtone;
    private Timer t = new Timer();
    private static final String CHANNEL_ID = "MyNotificationChannelID";
    private String key,title;
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
        key=intent.getStringExtra("key");
        title=intent.getStringExtra("title");
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
        startForeground(0,notification);

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
                k++;
                if(k==2) {
                    intent2.putExtra("key", key);
                    intent2.putExtra("title", title);
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