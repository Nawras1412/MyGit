package com.example.smartremindersapp2;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class ReminderReceiver extends BroadcastReceiver {
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
        String key=intent.getStringExtra("key");
        System.out.println("im in OnReceive");
        if (intent.getStringExtra("title").equals("Date Reminder")){
            System.out.println("***********************************in dateeee************************************");
            NotificationHelper notificationHelper = new NotificationHelper(context);
            Notification nb = notificationHelper.getChannelNotification(intent.getStringExtra("key")
                    , HomePage.class, intent.getStringExtra("title"), intent.getStringExtra("content"),"");
            notificationHelper.getManager().notify(0, nb);
        }else{
            String url = "";
            ///  location request & notification intent
            String type= intent.getStringExtra("locationType").toLowerCase();
            String radius = "500";
            String lat="32.7614296";
            String Google_api_key="AIzaSyCfsrOq62GRNdUvZeMBhimX4RFX9cpm4uU";
            String lang="35.0195184";
            System.out.println("TYPEEEEEEEEEEEEEEEEE"+type);
            if (type.equals("other")){
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + "location=" + lat + "," + lang + "&" +
                        "radius=" + radius + "&" + "key=" + "AIzaSyDMU9eVBmHymFvymjsCO3pUCBwwGMTqV5w";
//                new GooglePlacesClient().getResponseThread(url,
//                        lat,lang,intent.getStringExtra("title"),
//                        intent.getStringExtra("content"),intent.getStringExtra("key"),context);
                //                Double lang2=intent.getDoubleExtra("lang",0.0);
//                Double lat2=intent.getDoubleExtra("lat",0.0);
//                String address=intent.getStringExtra("address");
////                lang=place.getLatLng().longitude;
////                lat=place.getLatLng().latitude;
////                address=place.getAddress();
//                float[] distance = new float[1];
//                double lat1 = 32.7614296;
//                double lng1 = 35.0195184;
//                Location.distanceBetween(lat2, lang2, lat1, lng1, distance);
//                if ((distance[0] < 300000)) {
//                    System.out.println("im in distance[0] < 3000 ");
//                    NotificationHelper notificationHelper = new NotificationHelper(HomePage.getInstance());
//                    Notification nb = notificationHelper.getChannelNotification(key
//                            , HomePage.class, "Congratulation, you have arrive", "You are near " + address, "");
//                    notificationHelper.getManager().notify(0, nb);
//                }
            } else {
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + "location=" + lat + "," + lang + "&" +
                        "rankby=distance" + "&" + "type="+type +"&"+ "key=" + "AIzaSyDMU9eVBmHymFvymjsCO3pUCBwwGMTqV5w";

                new GooglePlacesClient().getResponseThread(url,
                        lat,lang,intent.getStringExtra("title"),
                        intent.getStringExtra("content"),intent.getStringExtra("key"),context);
//                new GooglePlacesClient().getResponseThread(url,lat,lang,
//                        intent.getStringExtra("title"),intent.getStringExtra("content")
//                        ,intent.getStringExtra("key"),context);
            }
        }
    }
}