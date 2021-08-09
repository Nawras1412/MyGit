package com.example.smartremindersapp2;

import android.Manifest;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import android.os.IBinder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import android.content.ServiceConnection;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class ReminderReceiver extends BroadcastReceiver  {


    String url = "";
    String type,radius="500",Google_api_key="AIzaSyCfsrOq62GRNdUvZeMBhimX4RFX9cpm4uU";
    String key,lat,lang;
    Intent recievedIntent;
    Context recievedContext;

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }

    public  String getResponse(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet del = new HttpGet(url);
        del.setHeader("content-type", "application/json");

        String respStr;
        try {
            HttpResponse resp = httpClient.execute(del);
            respStr = EntityUtils.toString(resp.getEntity());
        } catch(Exception ex) {
            Log.e("RestService","Error!", ex);
            respStr = "";
        }

        Log.e("getResponse",respStr);
        return respStr;
    }






    @Override
    public void onReceive(Context context, Intent intent){
        recievedIntent=intent;
        recievedContext=context;
        url = "";
        type= intent.getStringExtra("locationType").toLowerCase();
        radius = "500";
        Google_api_key="AIzaSyCfsrOq62GRNdUvZeMBhimX4RFX9cpm4uU";
        key=intent.getStringExtra("key");
        System.out.println("im in OnReceive");
        if (intent.getStringExtra("title").equals("Date Reminder")){
            System.out.println("***********************************in dateeee************************************");
            NotificationHelper notificationHelper = new NotificationHelper(context);
            Notification nb = notificationHelper.getChannelNotification(intent.getStringExtra("key")
                    , HomePage.class, intent.getStringExtra("title"), intent.getStringExtra("content"),"","","","","","");
            notificationHelper.getManager().notify(0, nb);
        }else{
            //change date status to true
            HashMap map = new HashMap();
            map.put("dateState",true);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(intent.getStringExtra("userName")).child("reminder_list").child(intent.getStringExtra("key"));

            ref.updateChildren(map);
            // HomePage.getInstance().get_all_reminders_by_kind("all");
//            ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot snapshot) {
//                    Reminder reminder=snapshot.getValue(Reminder.class);
//                    System.out.println("reminder into "+reminder.getMyType());
//
//                }
//                @Override
//                public void onCancelled(DatabaseError error) {}
//            });

            //creating background location Service


        }
    }


}