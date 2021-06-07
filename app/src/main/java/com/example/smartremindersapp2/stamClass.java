//
//package com.example.smartremindersapp2;
//
//import androidx.annotation.RequiresApi;
//import  androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import android.Manifest;
//import android.app.TimePickerDialog;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.AlarmClock;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.TimePicker;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//public class stamClass extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
//    private CheckBox SundayBox;
//    private CheckBox MondayBox;
//    private CheckBox TuesdayBox;
//    private CheckBox WednesdayBox;
//    private CheckBox ThursdayBox;
//    private CheckBox FridayBox;
//    private CheckBox SaturdayBox;
//    private int hour;
//    private int minutes;
//    private DatabaseReference ref;
//
//    private void ServiceCaller(Intent intent,int H,int M,List<String> checkedBoxes,String userName){
//        stopService(intent);
//        intent.putExtra("hour",H);
//        intent.putExtra("minutes",M);
//        intent.putExtra("User Name",userName);
//        startService(intent);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);
//        Intent intent=new Intent(stamClass.this,NotifierAlarm.class);
//
//        setContentView(R.layout.activity_alarm_clock);
//        SundayBox=findViewById(R.id.Sunday_checkBox);
//        MondayBox=findViewById(R.id.Monday_CheckBox);
//        TuesdayBox=findViewById(R.id.Tuesday_Check_Box);
//        WednesdayBox=findViewById(R.id.Wednesday_Check_box);
//        ThursdayBox=findViewById(R.id.Thursday_Check_box);
//        FridayBox=findViewById(R.id.Friday_Check_Box);
//        SaturdayBox=findViewById(R.id.Saturday_Check_Box);
//        TimePicker alarmTimePicker=findViewById(R.id.alarmTimePicker);
//        Button scheduleButton = (Button) findViewById(R.id.schedule_button);
//        scheduleButton.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                List<String> checkedBoxes=alarm_clock_control.check_boxes(SundayBox,MondayBox,TuesdayBox,WednesdayBox,ThursdayBox,FridayBox,SaturdayBox);
//                hour=alarmTimePicker.getHour();
//                minutes=alarmTimePicker.getMinute();
//                String userName=getIntent().getStringExtra("User Name");
//
//                ref= FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("Alarms");
//                ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot){
//                        DatabaseReference keyRef =ref.push();
//                        List<Integer> daysOfWeekToRepeat=new ArrayList<>();
//                        clock_alarm_entity alarm=new clock_alarm_entity(keyRef.getKey(),Integer.toString(hour),Integer.toString(minutes),checkedBoxes,true);
//                        keyRef.setValue(alarm);
//                        Intent intent=new Intent(AlarmClock.ACTION_SET_ALARM);
//                        intent.putExtra(AlarmClock.EXTRA_HOUR,alarm.getHour());
//                        intent.putExtra(AlarmClock.EXTRA_MINUTES,alarm.getMinuter());
////                        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
////                        intent.putExtra(AlarmClock.EXTRA_VIBRATE, true);
////                        if(checkedBoxes.isEmpty())
////                            daysOfWeekToRepeat.add(Calendar.DAY_OF_WEEK);
////                        for (String day:checkedBoxes){
////                            if (day=="sunday")
////                                daysOfWeekToRepeat.add(java.util.Calendar..SUNDAY);
////                            if (day=="monday")
////                                daysOfWeekToRepeat.add(java.util.Calendar..MONDAY);
////                            if (day=="tuesday")
////                                daysOfWeekToRepeat.add(java.util.Calendar..THURSDAY);
////                            if (day=="wednesday")
////                                daysOfWeekToRepeat.add(java.util.Calendar.WEDNESDAY);
////                            if (day=="thursday")
////                                daysOfWeekToRepeat.add(java.util.Calendar.THURSDAY);
////                            if (day=="friday")
////                                daysOfWeekToRepeat.add(java.util.Calendar.FRIDAY);
////                            if (day=="saturday")
////                                daysOfWeekToRepeat.add(java.util.Calendar.SATURDAY);
////
////
////                        }
////                        intent.putExtra(AlarmClock.EXTRA_DAYS, String.valueOf(daysOfWeekToRepeat));
////                        intent.putExtra(AlarmClock.EXTRA_LENGTH, 1000);
////                        intent.putExtra(AlarmClock.ACTION_SNOOZE_ALARM, true);
////                        intent..putExtra(AlarmClock.ACTION_DISMISS_TIMER,true);
//
////                        for (String day:alarm.getDays()){
////                            intent.putExtra(AlarmClock.EXTRA_MINUTES,day);
////                        }
//                        startActivity(intent);
//                        openNewPage(all_alarms.class);
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError){}
//                });
//                ServiceCaller(intent,hour,minutes,checkedBoxes,userName);
//            }
//        });
//    }
//
//
//
//
//    public void openNewPage(Class classX){
//        Intent intent=new Intent(this,classX);
//        String user=getIntent().getStringExtra("User Name");
//        intent.putExtra("User Name", user);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        hour=hourOfDay;
//        minutes=minute;
//    }
//}
//
