package com.example.smartremindersapp2;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
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
        if (intent.getStringExtra("title").equals("Date Reminder")){
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
        }
    }
}