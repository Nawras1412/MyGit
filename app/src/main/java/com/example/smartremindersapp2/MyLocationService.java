package com.example.smartremindersapp2;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationSettingsRequest;

public class MyLocationService extends BroadcastReceiver {


    public static  final String ACTION_PROCESS_UPDATE="com.example.smartremindersapp2.UPDATE_LOCATION";
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.print("ANNNNNNNNNNNNNNNNAA!!!!!!!!3333");
        if (intent != null)
        {

            final String action = intent.getAction();
            if(ACTION_PROCESS_UPDATE.equals(action))
            {
                LocationResult result = LocationResult.extractResult(intent);
                if (result!=null)
                {
                    Location location=result.getLastLocation();

                    String location_string = "" + location.getLatitude() + "/" + location.getLongitude();

//                    try {
//                        HomePage.getInstance().updateTextView(location_string);
//                    }catch (Exception ex)
//                    {
//                        Toast.makeText(context,location_string,Toast.LENGTH_SHORT).show();
//                    }

                }
            }
        }
    }
}








//package com.example.smartremindersapp2;
//
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.location.Location;
//import android.os.IBinder;
//import android.widget.Toast;
//
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationSettingsRequest;
//
//public class MyLocationService extends BroadcastReceiver {
//    public static  final String ACTION_PROCESS_UPDATE="com.example.smartremindersapp2.UPDATE_LOCATION";
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (intent != null)
//        {
//
//            final String action = intent.getAction();
//            if(ACTION_PROCESS_UPDATE.equals(action))
//            {
//                LocationResult result = LocationResult.extractResult(intent);
//                if (result!=null)
//                {
//                    Location location=result.getLastLocation();
//                    String location_string = "" + location.getLatitude() + "/" + location.getLongitude();
//                    try {
//                        HomePage.getInstance().updateTextView(location_string);
//                    }catch (Exception ex)
//                    {
//                        Toast.makeText(context,location_string,Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            }
//        }
//    }
//}