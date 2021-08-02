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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("im in OnReceive");
        if (intent.getStringExtra("ContentTitle").equals("My Date Reminder")) {
            System.out.println("***********************************in dateeee************************************");
            NotificationHelper notificationHelper = new NotificationHelper(context);
            Notification nb = notificationHelper.getChannelNotification(intent.getStringExtra("key")
                    , HomePage.class, intent.getStringExtra("ContentTitle"), intent.getStringExtra("ContentText"));
            notificationHelper.getManager().notify(0, nb);

        } else {
            String url = "";
            ///  location request & notification intent
            String type= intent.getStringExtra("locationType").toLowerCase();
            String radius = "500";
            String lat="32.7614296";
            String Google_api_key="AIzaSyCfsrOq62GRNdUvZeMBhimX4RFX9cpm4uU";
            String lang="35.0195184";
            System.out.println("TYPEEEEEEEEEEEEEEEEE"+type);
            if (type == "Other") {
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + "location=" + lat + "," + lang + "&" +
                        "radius=" + radius + "&" + "key=" + "AIzaSyDMU9eVBmHymFvymjsCO3pUCBwwGMTqV5w"
                ;
            } else {
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + "location=" + lat + "," + lang + "&" +
                        "rankby=distance" + "&" + "type="+type +"&"+ "key=" + "AIzaSyDMU9eVBmHymFvymjsCO3pUCBwwGMTqV5w";
                new GooglePlacesClient().getResponseThread(url,lat,lang,intent.getStringExtra("title"));

            }



        }
    }
}