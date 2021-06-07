package com.example.smartremindersapp2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class all_alarms extends AppCompatActivity {
    private ImageView addAlarmImage;
    private ArrayList<alarm_view> alarms_list = new ArrayList<>();
    private static RecyclerView mRecyclerView;
    private static ExampleAdapter mAdapter;
    private static RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout drawerLayout;
    private String userName;
    private AuxiliaryFunctions myAuxiliaryFunctions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_alarms);
        drawerLayout=findViewById(R.id.drawer_layout);
        userName=getSharedPreferences("U",MODE_PRIVATE).
                getString("username",null);
        addAlarmImage = findViewById(R.id.imageView);
        addAlarmImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                myAuxiliaryFunctions.openNewPage(getApplicationContext(),alarm_clock.class);
            }
        });
        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        myAuxiliaryFunctions=AuxiliaryFunctions.getInstance();
        if(AlertReceiver.getRingtone()!=null) {
            DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("Alarms").child(NotificationHelper.getKey());
            HashMap map=new HashMap();
            map.put("checked",false); // must be checked
            ref.updateChildren(map);
            if (AlertReceiver.getRingtone().isPlaying()) {
                AlertReceiver.stopRingtone();
            }
        }
        alarms_list = get_all_the_alarms_from_firebase(userName);

        // we must update the switch in the fire database and in the application
        //            String key=NotificationHelper.getKey();
        // we can get the ket of the alarm by the line above
    }


    public void ClickMenu(View view){ myAuxiliaryFunctions.openDrawer(drawerLayout);}
    public void ClickLogo(View view){ myAuxiliaryFunctions.closeDrawer(drawerLayout);}
    public void ClickHome(View view){ myAuxiliaryFunctions.openNewPage(getApplicationContext(),HomePage.class);}
    public void ClickDashboard(View view){ myAuxiliaryFunctions.closeDrawer(drawerLayout);}
    public void ClickAboutUs(View view){ myAuxiliaryFunctions.openNewPage(getApplicationContext(),AboutUs.class);}
    public void ClickLogout(View view){ myAuxiliaryFunctions.logout(this,userName);}





    public ArrayList<alarm_view> get_all_the_alarms_from_firebase(String userName){
        ArrayList<alarm_view> alarms = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("Alarms");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    alarm_view alarm = ds.getValue(alarm_view.class);
                    alarm.setSwitch((Boolean) snapshot.child(alarm.getKey()).child("checked").getValue());
                    alarms.add(alarm);
                }
                c(alarms, userName);
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
        return alarms;
    }




    public  void c(ArrayList<alarm_view> alarms, String userName) {
        mAdapter = new ExampleAdapter(this, alarms, userName);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                alarm_view currentAlarm=alarms_list.get(position);
//                Intent intent=new Intent(getApplicationContext(),alarm_clock.class);
//                intent.putExtra("Key",currentAlarm.getKey());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                getApplicationContext().startActivity(intent);
//                DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("Alarms").child(currentAlarm.getKey());
//                ref.removeValue();
//                myAuxiliaryFunctions.openNewPage(getApplicationContext(),alarm_clock.class);
            }
            @Override
            public void onDeleteClick(int position) {}
        });
    }


    void cancelAlarm(String key,Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, key.hashCode(), intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
     void startAlarm(Calendar c, String key,Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("Key",key);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, key.hashCode(), intent,0);
        if (c.before(Calendar.getInstance())){
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//        startService(intent);
    }
}




//    mLayoutManager=new LinearLayoutManager(this) -->
//    Adapters are only responsible for creating and managing views for items (called ViewHolder)
//    , these classes do not decide how these views are arranged when displaying them. Instead,
//    they rely on a separate class called LayoutManager.
//
//    LayoutManager is a class that tells Adapters how to arrange those items. For example, you might
//    want those items in a single row top to bottom or you may want them arranged in Grids like Gallery.
//    Instead of writing this logic in your adapter, you write it in LayoutManager and pass that LayoutManager to View (RecyclerView).
//


//    mRecyclerView.setHasFixedSize(true) -->
//    changing the contents of the adapter does not change it's height or the width.





