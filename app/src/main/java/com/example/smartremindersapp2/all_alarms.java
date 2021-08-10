package com.example.smartremindersapp2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.number.Scale;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
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
    private SharedPreferences msharedPreferences;
    private SharedPreferences.Editor editor;
    private int hour;
    private int minutes;
    private boolean snooze;
    private static TextView instruction;




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //snooze=false;
        userName=getSharedPreferences("U",MODE_PRIVATE).
                getString("username",null);
        try{
            if(this.getIntent().getStringExtra("type").equals("Dismiss")) {
                String key=this.getIntent().getStringExtra("key");
//                addReminder add_remind =new addReminder();
                NotificationManager manager=(NotificationManager) getApplicationContext()
                        .getSystemService(NOTIFICATION_SERVICE);
                manager.cancel(key.hashCode());
//                add_remind.cancelNotification(key,HomePage.getInstance());
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("reminder_list").child(key);
                ref.removeValue();
            }else if(this.getIntent().getStringExtra("type").equals("SNOOZE")) {
                System.out.println("its snooze 11111");

                String key = this.getIntent().getStringExtra("key");
                System.out.println("key "+key);
                NotificationManager manager = (NotificationManager) getApplicationContext()
                        .getSystemService(NOTIFICATION_SERVICE);
                manager.cancel(key.hashCode());
                System.out.println("2222");
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("Alarms");
                System.out.println("2.5");
                Calendar date = Calendar.getInstance();
                long timeInSecs = date.getTimeInMillis();
                Date afterAdding10Mins = new Date(timeInSecs + (10 * 60 * 100));
                System.out.println("33333");
                HashMap map2 = new HashMap();
                map2.put("date", afterAdding10Mins);
                map2.put("checked", true);
                map2.put("minutes", afterAdding10Mins.getMinutes());
                map2.put("hour", afterAdding10Mins.getHours());
                System.out.println("3.5");
                ref.child(key).updateChildren(map2);
                System.out.println("4444");
                alarm_clock AC=new alarm_clock();
                snooze=true;
                AC.startAlarm(key,true,this.getIntent().getStringExtra("title"));
//                NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
//                Notification nb = notificationHelper.getChannelNotification(key
//                        ,all_alarms.class,"Alarm!","Your AlarmManager is working.","","alarm","","","","");
//                notificationHelper.getManager().notify(key.hashCode(), nb);
            }
        }
        catch(Exception ex){
            System.out.println("in catch");
        }
        System.out.println("im in all_alarms onCreate");
        msharedPreferences=getSharedPreferences("U",MODE_PRIVATE);
        editor=msharedPreferences.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_alarms);
        drawerLayout=findViewById(R.id.drawer_layout);
        instruction = findViewById(R.id.instructions_A);

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
            System.out.println("AlertReceiver.getRingtone()!=null");
            if (AlertReceiver.getRingtone().isPlaying()){
                System.out.println("AlertReceiver.getRingtone().isPlaying()");
                DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("Alarms").child(msharedPreferences.getString("Current Ring Key",null));
                NotifierAlarm.setKillTimer();
                System.out.println("the current is:"+msharedPreferences.getString("Current Ring Key",null));
                editor.putBoolean("ring "+msharedPreferences.getString("Current Ring Key",null),false);
                editor.putInt("AlarmsNum",msharedPreferences.getInt("AlarmsNum",0)-1);
                editor.commit();
                ref.child("days_date").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.getValue()==null) {
                            System.out.println("im in snapshot.getValue()==null");
                            HashMap map = new HashMap();
                           map.put("checked", false); // must be checked
                            ref.updateChildren(map);
                        }
                        else{
                            DatabaseReference ref3=FirebaseDatabase.getInstance().getReference().child("Users")
                                    .child(userName).child("Alarms").
                                            child(msharedPreferences.getString("Current Ring Key",null));
                            ref3.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    alarm_view alarm=snapshot.getValue(alarm_view.class);
                                    hour=alarm.getHour();
                                    minutes=alarm.getMinutes();
                                    setRepeatedAlarm(alarm.getDays_date().size()+1,msharedPreferences.getString("Current Ring Key",null),true);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}
                            });
                        }
                        alarms_list = get_all_the_alarms_from_firebase(userName);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        }
        else {
            System.out.println("im in get_all_the_alarms_from_firebase");
            alarms_list = get_all_the_alarms_from_firebase(userName);
        }

        // we must update the switch in the fire database and in the application
        //            String key=NotificationHelper.getKey();
        // we can get the ket of the alarm by the line above
    }



    public static void setInstruction(int v) {
        if(v==0)
            instruction.setVisibility(View.VISIBLE);
        else
            instruction.setVisibility(View.INVISIBLE);
    }


    public void setRepeatedAlarm(int i,String key,boolean repeated) {
        System.out.println(" the i is : "+i);
        System.out.println(" the key is : "+key);
        Intent intent=new Intent(this,NotifierAlarm.class);
        stopService(intent);
        Calendar c = Calendar.getInstance();
        int today=c.get(Calendar.DAY_OF_WEEK);
        System.out.println("the current day is: "+today);
        Date NextDate;
        System.out.println("im in else");
        int offset = Calendar.SATURDAY;
        c.add(Calendar.DATE, offset);
//        NextDate = c.getTime();


        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,minutes);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        System.out.println("the date is: " + c.getTime());
        System.out.println("the hour is:"+hour);
        System.out.println("the minutes is:"+minutes);
        Date date=c.getTime();
        intent.putExtra("Pending_key",key.hashCode() + i);
        intent.putExtra("date",date.getTime());
        intent.putExtra("key",key);
        intent.putExtra("userName",userName);
        startService(intent);
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
                    System.out.println(alarm.getHour());
                    System.out.println(alarm.getMinutes());
                    System.out.println(alarm.getKey());
//                    System.out.println(snapshot.child(alarm.getKey()).child("checked").getValue());
                    alarm.setSwitch((Boolean) ds.child("checked").getValue());
                    alarms.add(alarm);
                }
                c(alarms, userName);
                setInstruction(alarms.size());
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
        return alarms;
    }




    public void c(ArrayList<alarm_view> alarms, String userName) {
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


    void cancelAlarm(alarm_view alarm,Context context) {
        //subtract one from the number of running processes
        SharedPreferences sharedPreferences=context.getSharedPreferences("U",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        //one alarm to delete from AlarmManager
        if(alarm.getDate()!=null){
            AlarmManager alarmManager
                    = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getKey().hashCode(), intent, 0);
            alarmManager.cancel(pendingIntent);
            editor.putInt("AlarmsNum",sharedPreferences.getInt("AlarmsNum",0)-1);
            editor.commit();
        }
        else{
            List<String> days=alarm.getDays_date();
            int i=0;
            String key=alarm.getKey();
            for (String day:days){
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                Intent intent = new Intent(context, AlertReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getKey().hashCode()+i, intent, 0);
                alarmManager.cancel(pendingIntent);
                editor.putInt("AlarmsNum",sharedPreferences.getInt("AlarmsNum",0)-1);
                editor.commit();
                i++;
            }

        }

//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
//        Intent intent = new Intent(context, AlertReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, key.hashCode(), intent, 0);
//        alarmManager.cancel(pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
     void startAlarm(alarm_view alarm,Context context){
        System.out.println("im in start alarm ");
        //add one for the number of running processes
        SharedPreferences sharedPreferences=context.getSharedPreferences("U",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        //add one alarm to the AlarmManager
        if(alarm.getDate()!=null) {
            System.out.println("im in alarm.getDate()!=null ");
//            editor.putInt("AlarmsNum",sharedPreferences.getInt("AlarmsNum",0)+1);
//            editor.commit();
            Intent ServiceIntent=new Intent(context,NotifierAlarm.class);
            ServiceIntent.putExtra("date",alarm.getDate().getTime());
            ServiceIntent.putExtra("Pending_key",alarm.getKey().hashCode());
            ServiceIntent.putExtra("key",alarm.getKey());
            ServiceIntent.putExtra("Repeating",false);
            context.startService(ServiceIntent);
        }
        else{
            int i=0;
            String key=alarm.getKey();
            List<String> daysList=alarm.getDays_date();
            if (daysList.contains("0"))  setRepeatedAlarm(i++,Calendar.SUNDAY,key,alarm,context);
            if (daysList.contains("1"))  setRepeatedAlarm(i++,Calendar.MONDAY,key,alarm,context);
            if (daysList.contains("2"))  setRepeatedAlarm(i++,Calendar.TUESDAY,key,alarm,context);
            if (daysList.contains("3"))  setRepeatedAlarm(i++,Calendar.WEDNESDAY,key,alarm,context);
            if (daysList.contains("4")) setRepeatedAlarm(i++,Calendar.THURSDAY,key,alarm,context);
            if (daysList.contains("5"))  setRepeatedAlarm(i++,Calendar.FRIDAY,key,alarm,context);
            if (daysList.contains("6"))  setRepeatedAlarm(i++,Calendar.SATURDAY,key,alarm,context);
        }
    }

    public void setRepeatedAlarm(int i,int day,String key,alarm_view alarm,Context context){
//        SharedPreferences sharedPreferences=getSharedPreferences("U",MODE_PRIVATE);
//        SharedPreferences.Editor editor=sharedPreferences.edit();
//        editor.putInt("AlarmsNum",sharedPreferences.getInt("AlarmsNum",0)+1);
//        editor.commit();
        Intent intent=new Intent(context,NotifierAlarm.class);
        context.stopService(intent);
        Calendar NotificationDate=Calendar.getInstance();
        NotificationDate.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        NotificationDate.set(Calendar.MINUTE, alarm.getMinutes());
        NotificationDate.set(Calendar.SECOND, 0);
        NotificationDate.set(Calendar.MILLISECOND, 0);
        NotificationDate.set(Calendar.DAY_OF_WEEK, day);
        Date date=NotificationDate.getTime();
        intent.putExtra("Pending_key",key.hashCode() + i);
        intent.putExtra("date",date.getTime());
        intent.putExtra("key",key);
        intent.putExtra("Repeating",true);
        context.startService(intent);
    }
}




        // add one to the number of running processes per day
        // add to the alarm manager
        // if the alarms with nultiple days so must add alarm per day
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
//        Intent intent = new Intent(context, AlertReceiver.class);
//        intent.putExtra("Key",key);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, key.hashCode(), intent,0);
//        if (c.before(Calendar.getInstance())){
//            c.add(Calendar.DATE, 1);
//        }
//        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//        startService(intent);







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





