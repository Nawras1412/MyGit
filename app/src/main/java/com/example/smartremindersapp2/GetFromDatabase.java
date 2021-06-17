////package com.example.smartremindersapp2;
////
////public class GetFromDatabase {
////
////    public static boolean IfUserExist(String UserName){
////        return false;
////    }
////}
//
//
//package com.example.smartremindersapp2;
//
//import androidx.annotation.RequiresApi;
//import  androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import android.Manifest;
//import android.app.Activity;
//import android.app.AlarmManager;
//import android.app.DatePickerDialog;
//import android.app.PendingIntent;
//import android.app.TimePickerDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TimePicker;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import java.text.DateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//public class alarm_clock extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
//    private CheckBox SundayBox;
//    private CheckBox MondayBox;
//    private CheckBox TuesdayBox;
//    private CheckBox WednesdayBox;
//    private CheckBox ThursdayBox;
//    private CheckBox FridayBox;
//    private CheckBox SaturdayBox;
//    private EditText AlarmName;
//    private EditText AlarmDate;
//    private ImageView CalendarButton;
//    private Button scheduleButton;
//    private Button CancelButton;
//    private int hour;
//    private int minutes;
//    private String userName;
//    private DatabaseReference ref;
//    private AuxiliaryFunctions mAuxiliaryFunctions;
//    private TimePicker alarmTimePicker;
//    private String daysList="Every ";
//    private Calendar NotificationDate;
//    private Date date;
//
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);
//        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, PackageManager.PERMISSION_GRANTED);
//        setContentView(R.layout.activity_alarm_clock);
//
//        // findViewById
//        SundayBox=findViewById(R.id.Sunday_checkBox);
//        MondayBox=findViewById(R.id.Monday_CheckBox);
//        TuesdayBox=findViewById(R.id.Tuesday_Check_Box);
//        WednesdayBox=findViewById(R.id.Wednesday_Check_box);
//        ThursdayBox=findViewById(R.id.Thursday_Check_box);
//        FridayBox=findViewById(R.id.Friday_Check_Box);
//        SaturdayBox=findViewById(R.id.Saturday_Check_Box);
//        AlarmName=findViewById(R.id.alarm_name);
//        AlarmDate=findViewById(R.id.editTextDate);
//        CalendarButton=findViewById(R.id.calendar_button);
//        alarmTimePicker=findViewById(R.id.alarmTimePicker);
//        CancelButton = findViewById(R.id.cancel_alarm);
//        scheduleButton = findViewById(R.id.schedule_button);
//
//        //initial a few variables
//        userName=getSharedPreferences("U",MODE_PRIVATE).getString("username",null);
//        mAuxiliaryFunctions=AuxiliaryFunctions.getInstance();
//        NotificationDate=Calendar.getInstance();
//        hour=alarmTimePicker.getHour();
//        minutes=alarmTimePicker.getMinute();
//        updateHourAndMinutesInCalendar();
//        date=NotificationDate.getTime();
//
//        //check if this is an alarm to edit, if yes must initial the alarm_clock page
//        if(getIntent().getStringExtra("Key")!=null){
//            StartAlarmClockActivity(getIntent().getStringExtra("Key"));
//        }
//
//        //back to all_alarms page
//        CancelButton.setOnClickListener(new View.OnClickListener(){
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        // the update of the alarm picker
//        alarmTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                String text;
//                //update the new chooses hour and minutes
//                hour=alarmTimePicker.getHour();
//                minutes=alarmTimePicker.getMinute();
//                updateHourAndMinutesInCalendar();
//
//                //get the date of today and save in text
//                text="Today-"+getDateAsString(NotificationDate.getTime());
//
//
//                //if the chooses time is before the current time then get the date of tomorrow
//                Calendar cal=Calendar.getInstance();
//                cal.set(Calendar.HOUR_OF_DAY, hour);
//                cal.set(Calendar.MINUTE, minutes);
//                cal.set(Calendar.SECOND, 0);
//                cal.set(Calendar.MILLISECOND, 0);
//                if (cal.before(Calendar.getInstance())){
//                    cal.add(Calendar.DATE,1);
//                    text="Tomorrow-"+getDateAsString(cal.getTime());
//                }
//
//                //set text if relevant
//                if ((AlarmDate.getText().toString().isEmpty()) || (AlarmDate.getText().toString()
//                        .split("-")[0].equals("Tomorrow")) || (AlarmDate.getText().toString()
//                        .split("-")[0].equals("Today"))){
//                    AlarmDate.setText(text);
//                    date= NotificationDate.getTime();
//                }
//            }
//        });
//
//        SundayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){UpdateTextViewDays();}
//        });
//        MondayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){UpdateTextViewDays();}
//        });
//        TuesdayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){UpdateTextViewDays();}
//        });
//        WednesdayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){UpdateTextViewDays();}
//        });
//        ThursdayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){UpdateTextViewDays();}
//        });
//        FridayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){UpdateTextViewDays();}
//        });
//        SaturdayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){UpdateTextViewDays();}
//        });
//
//
//        CalendarButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar newCalender = Calendar.getInstance();
//                DatePickerDialog dialog = new DatePickerDialog(alarm_clock.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
//                        //save the date in the calendar
//                        NotificationDate = Calendar.getInstance();
//                        updateHourAndMinutesInCalendar();
//                        NotificationDate.set(Calendar.MONTH, month);
//                        NotificationDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                        date=NotificationDate.getTime();
//                        UnCheckTheCheckBoxes();
//                        //get the date and set in the alarm date text view
//                        AlarmDate.setText(getDateAsString(NotificationDate.getTime()));
//
//                    }
//                }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
//                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
//                dialog.show();
//            }
//        });
//
//        scheduleButton.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v){
//                //if true thats mean that we edit an alarm and save the new version
//                if(getIntent().getStringExtra("Key")!=null){
//                    String key=getIntent().getStringExtra("Key");
//                    ref= FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("Alarms").child(key);
//                    ref.removeValue();
//                    all_alarms AC=new all_alarms();
//                    AC.cancelAlarm(key,getApplicationContext());
//                }
//                List<String> checkedBoxes=alarm_clock_control.check_boxes(SundayBox,MondayBox,TuesdayBox,WednesdayBox,ThursdayBox,FridayBox,SaturdayBox);
//                ref= FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("Alarms");
//                DatabaseReference keyRef =ref.push();
//                String key=keyRef.getKey();
//                alarm_view alarm=new alarm_view(key,AlarmName.getText().toString(),hour
//                        ,minutes,checkedBoxes,true,date);
//                keyRef.setValue(alarm);
//                mAuxiliaryFunctions.openNewPage(getApplicationContext(),all_alarms.class);
//                boolean status;
//                if(AlarmDate.getText().toString().split(" ")[0].equals("Every"))status=false;
//                else status=true;
////                ServiceCaller(intent,hour,minutes,checkedBoxes,userName);
//                startAlarm(key,status);
//            }
//        });
//    }
//
//    private void ServiceCaller(Intent intent,int H,int M,List<String> checkedBoxes,String userName){
//        stopService(intent);
//        intent.putExtra("hour",H);
//        intent.putExtra("minutes",M);
//        intent.putExtra("User Name",userName);
//        startService(intent);
//    }
//
//    private void UnCheckTheCheckBoxes() {
//        SundayBox.setChecked(false);
//        MondayBox.setChecked(false);
//        TuesdayBox.setChecked(false);
//        WednesdayBox.setChecked(false);
//        ThursdayBox.setChecked(false);
//        FridayBox.setChecked(false);
//        SaturdayBox.setChecked(false);
//    }
//
//    private void updateHourAndMinutesInCalendar() {
//        NotificationDate=Calendar.getInstance();
//        NotificationDate.set(Calendar.HOUR_OF_DAY, hour);
//        NotificationDate.set(Calendar.MINUTE, minutes);
//        NotificationDate.set(Calendar.SECOND, 0);
//        NotificationDate.set(Calendar.MILLISECOND, 0);
//    }
//
//    public void StartAlarmClockActivity(String key){
//        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("Alarms").child(key);
//        ref.addListenerForSingleValueEvent(new ValueEventListener(){
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                alarm_view currentAlarm = snapshot.getValue(alarm_view.class);
//                AlarmName.setText(currentAlarm.getTitle());
//                alarmTimePicker.setHour(currentAlarm.getHour());
//                alarmTimePicker.setMinute(currentAlarm.getMinutes());
//                if (currentAlarm.getDays_date()==null)
//                    AlarmDate.setText(getDateAsString(currentAlarm.getDate()));
//                else{
//                    String DaysList="Every ";
//                    for(String day:currentAlarm.getDays_date()) {
//                        if (day.equals("0")){
//                            DaysList=DaysList+"Sun, ";
//                            SundayBox.setChecked(true);
//                        }
//                        if (day.equals("1")){
//                            DaysList=DaysList+"Mon, ";
//                            MondayBox.setChecked(true);
//                        }
//                        if (day.equals("2")){
//                            DaysList=DaysList+"Tue, ";
//                            TuesdayBox.setChecked(true);
//                        }
//                        if (day.equals("3")){
//                            DaysList=DaysList+"Wed, ";
//                            WednesdayBox.setChecked(true);
//                        }
//                        if (day.equals("4")){
//                            DaysList=DaysList+"Thur, ";
//                            ThursdayBox.setChecked(true);
//                        }
//                        if (day.equals("5")){
//                            DaysList=DaysList+"Fri, ";
//                            FridayBox.setChecked(true);
//                        }
//                        if (day.equals("6")){
//                            DaysList=DaysList+"Sat, ";
//                            SaturdayBox.setChecked(true);
//                        }
//                    }
//                    AlarmDate.setText(DaysList);
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError error) {}
//        });
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void UpdateTextViewDays() {
//        date=null;
//        daysList="Every ";
//        if(SundayBox.isChecked()) daysList=daysList+"Sun, ";
//        if(MondayBox.isChecked()) daysList=daysList+"Mon, ";
//        if(TuesdayBox.isChecked()) daysList=daysList+"Tue, ";
//        if(WednesdayBox.isChecked()) daysList=daysList+"Wed, ";
//        if(ThursdayBox.isChecked()) daysList=daysList+"Thur, ";
//        if(FridayBox.isChecked()) daysList=daysList+"Fri, ";
//        if(SaturdayBox.isChecked()) daysList=daysList+"Sat, ";
//
//        if(daysList.equals("Every ")){
//            String text;
//            //update the new chooses hour and minutes
//            hour=alarmTimePicker.getHour();
//            minutes=alarmTimePicker.getMinute();
//            updateHourAndMinutesInCalendar();
//
//            //get the date of today and save in text
//            text="Today-"+getDateAsString(NotificationDate.getTime());
//
//            //if the chooses time is before the current time then get the date of tomorrow
//            Calendar cal=Calendar.getInstance();
//            cal.set(Calendar.HOUR_OF_DAY, hour);
//            cal.set(Calendar.MINUTE, minutes);
//            cal.set(Calendar.SECOND, 0);
//            cal.set(Calendar.MILLISECOND, 0);
//            if (cal.before(Calendar.getInstance())){
//                cal.add(Calendar.DATE,1); //or add
//                text="Tomorrow-"+getDateAsString(cal.getTime());
//            }
//            date= NotificationDate.getTime();
//            AlarmDate.setText(text);
//        }
//        else AlarmDate.setText(daysList.substring(0,daysList.length()-2));
//    }
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void startAlarm(String key,boolean status) {
//        int i = 0;
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        Intent intent = new Intent(this, AlertReceiver.class);
////        Intent intent=new Intent(this,NotifierAlarm.class);
//        intent.putExtra("Key", key);
////        intent.putExtra("hour", hour);
////        intent.putExtra("minutes", minutes);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext()
//                , key.hashCode(), intent,0);
//        if (status){
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, NotificationDate.getTimeInMillis(), pendingIntent);
//        } else {
//            if (daysList.contains("Sun"))  setRepeatedAlarm(i++,Calendar.SUNDAY,key,alarmManager,intent);
//            if (daysList.contains("Mon"))  setRepeatedAlarm(i++,Calendar.MONDAY,key,alarmManager,intent);
//            if (daysList.contains("Tue"))  setRepeatedAlarm(i++,Calendar.TUESDAY,key,alarmManager,intent);
//            if (daysList.contains("Wed"))  setRepeatedAlarm(i++,Calendar.WEDNESDAY,key,alarmManager,intent);
//            if (daysList.contains("Thur"))  setRepeatedAlarm(i++,Calendar.THURSDAY,key,alarmManager,intent);
//            if (daysList.contains("Fri"))  setRepeatedAlarm(i++,Calendar.FRIDAY,key,alarmManager,intent);
//            if (daysList.contains("Sat"))  setRepeatedAlarm(i++,Calendar.SATURDAY,key,alarmManager,intent);
//        }
//
////        startService(intent);
//    }
//
//
//    public void setRepeatedAlarm(int i,int day,String key,AlarmManager alarmManager,Intent intent){
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), key.hashCode() + i, intent, 0);
//        updateHourAndMinutesInCalendar();
//        NotificationDate.set(Calendar.DAY_OF_WEEK, day);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, NotificationDate.getTimeInMillis(), pendingIntent);
////        startService(intent);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {}
//
//
//
//    public String getDateAsString(Date date){
//        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
//        String[] splitDate = formattedDate.split(",");
//        String date2=splitDate[0]+","+splitDate[1];
//        return date2;
//    }
//
//
//}
//
//
////package com.example.smartremindersapp2;
////
////import androidx.annotation.RequiresApi;
////import  androidx.appcompat.app.AppCompatActivity;
////import androidx.core.app.ActivityCompat;
////import android.Manifest;
////import android.app.Activity;
////import android.app.AlarmManager;
////import android.app.DatePickerDialog;
////import android.app.PendingIntent;
////import android.app.TimePickerDialog;
////import android.content.Context;
////import android.content.Intent;
////import android.content.pm.PackageManager;
////import android.os.Build;
////import android.os.Bundle;
////import android.view.View;
////import android.widget.Button;
////import android.widget.CheckBox;
////import android.widget.CompoundButton;
////import android.widget.DatePicker;
////import android.widget.EditText;
////import android.widget.ImageView;
////import android.widget.TimePicker;
////import com.google.firebase.database.DataSnapshot;
////import com.google.firebase.database.DatabaseError;
////import com.google.firebase.database.DatabaseReference;
////import com.google.firebase.database.FirebaseDatabase;
////import com.google.firebase.database.ValueEventListener;
////import java.text.DateFormat;
////import java.util.Calendar;
////import java.util.Date;
////import java.util.List;
////import java.util.concurrent.TimeUnit;
////
////public class alarm_clock extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
////    private CheckBox SundayBox;
////    private CheckBox MondayBox;
////    private CheckBox TuesdayBox;
////    private CheckBox WednesdayBox;
////    private CheckBox ThursdayBox;
////    private CheckBox FridayBox;
////    private CheckBox SaturdayBox;
////    private EditText AlarmName;
////    private EditText AlarmDate;
////    private ImageView CalendarButton;
////    private Button scheduleButton;
////    private Button CancelButton;
////    private int hour;
////    private int minutes;
////    private String userName;
////    private DatabaseReference ref;
////    private AuxiliaryFunctions mAuxiliaryFunctions;
////    private TimePicker alarmTimePicker;
////    private String daysList="Every ";
////    private Calendar NotificationDate;
////    private Date date;
////
////
////    @RequiresApi(api = Build.VERSION_CODES.M)
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);
////        setContentView(R.layout.activity_alarm_clock);
////
////        // findViewById
////        SundayBox=findViewById(R.id.Sunday_checkBox);
////        MondayBox=findViewById(R.id.Monday_CheckBox);
////        TuesdayBox=findViewById(R.id.Tuesday_Check_Box);
////        WednesdayBox=findViewById(R.id.Wednesday_Check_box);
////        ThursdayBox=findViewById(R.id.Thursday_Check_box);
////        FridayBox=findViewById(R.id.Friday_Check_Box);
////        SaturdayBox=findViewById(R.id.Saturday_Check_Box);
////        AlarmName=findViewById(R.id.alarm_name);
////        AlarmDate=findViewById(R.id.editTextDate);
////        CalendarButton=findViewById(R.id.calendar_button);
////        alarmTimePicker=findViewById(R.id.alarmTimePicker);
////        CancelButton = findViewById(R.id.cancel_alarm);
////        scheduleButton = findViewById(R.id.schedule_button);
////
////        //initial a few variables
////        userName=getSharedPreferences("U",MODE_PRIVATE).getString("username",null);
////        mAuxiliaryFunctions=AuxiliaryFunctions.getInstance();
////        NotificationDate=Calendar.getInstance();
////        hour=alarmTimePicker.getHour();
////        minutes=alarmTimePicker.getMinute();
////
////        //check if this is an alarm to edit, if yes must initial the alarm_clock page
////        if(getIntent().getStringExtra("Key")!=null){
////            StartAlarmClockActivity(getIntent().getStringExtra("Key"));
////        }
////
////        //back to all_alarms page
////        CancelButton.setOnClickListener(new View.OnClickListener(){
////            @RequiresApi(api = Build.VERSION_CODES.M)
////            @Override
////            public void onClick(View v) {
////                finish();
////            }
////        });
////
////        // the update of the alarm picker
////        alarmTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
////            @RequiresApi(api = Build.VERSION_CODES.M)
////            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
////                String text;
////                System.out.println("Im in alarm time picker");
////                //update the new chooses hour and minutes
////                hour=alarmTimePicker.getHour();
////                minutes=alarmTimePicker.getMinute();
////                updateHourAndMinutesInCalendar();
////
////                //get the date of today and save in text
////                text="Today-"+getDateAsString(NotificationDate.getTime());
////
////
////                //if the chooses time is before the current time then get the date of tomorrow
////                Calendar cal=Calendar.getInstance();
////                cal.set(Calendar.HOUR_OF_DAY, hour);
////                cal.set(Calendar.MINUTE, minutes);
////                cal.set(Calendar.SECOND, 0);
////                cal.set(Calendar.MILLISECOND, 0);
////                if (cal.before(Calendar.getInstance())){
////                    cal.add(Calendar.DATE,1);
////                    text="Tomorrow-"+getDateAsString(cal.getTime());
////                }
////
////                //set text if relevant
////                if ((AlarmDate.getText().toString().isEmpty()) || (AlarmDate.getText().toString()
////                        .split("-")[0].equals("Tomorrow")) || (AlarmDate.getText().toString()
////                        .split("-")[0].equals("Today"))){
////                    AlarmDate.setText(text);
////                    date= NotificationDate.getTime();
////                }
////            }
////        });
////
////        SundayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
////            @Override
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){UpdateTextViewDays();}
////        });
////        MondayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
////            @Override
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){UpdateTextViewDays();}
////        });
////        TuesdayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
////            @Override
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){UpdateTextViewDays();}
////        });
////        WednesdayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
////            @Override
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){UpdateTextViewDays();}
////        });
////        ThursdayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
////            @Override
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){UpdateTextViewDays();}
////        });
////        FridayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
////            @Override
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){UpdateTextViewDays();}
////        });
////        SaturdayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
////            @Override
////            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){UpdateTextViewDays();}
////        });
////
////
////        CalendarButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Calendar newCalender = Calendar.getInstance();
////                DatePickerDialog dialog = new DatePickerDialog(alarm_clock.this, new DatePickerDialog.OnDateSetListener() {
////                    @Override
////                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
////                        //save the date in the calendar
////                        System.out.println("Im in Calendar Button");
////                        NotificationDate = Calendar.getInstance();
////                        updateHourAndMinutesInCalendar();
////                        NotificationDate.set(Calendar.MONTH, month);
////                        NotificationDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
////                        date=NotificationDate.getTime();
////                        UnCheckTheCheckBoxes();
////                        //get the date and set in the alarm date text view
////                        AlarmDate.setText(getDateAsString(NotificationDate.getTime()));
////
////                    }
////                }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
////                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
////                dialog.show();
////            }
////        });
////
////        scheduleButton.setOnClickListener(new View.OnClickListener() {
////            @RequiresApi(api = Build.VERSION_CODES.M)
////            @Override
////            public void onClick(View v){
////                //if true thats mean that we edit an alarm and save the new version
////                if(getIntent().getStringExtra("Key")!=null){
////                    String key=getIntent().getStringExtra("Key");
////                    ref= FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("Alarms").child(key);
////                    ref.removeValue();
////                    all_alarms AC=new all_alarms();
////                    AC.cancelAlarm(key,getApplicationContext());
////                }
////                List<String> checkedBoxes=alarm_clock_control.check_boxes(SundayBox,MondayBox,TuesdayBox,WednesdayBox,ThursdayBox,FridayBox,SaturdayBox);
////                ref= FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("Alarms");
////                DatabaseReference keyRef =ref.push();
////                String key=keyRef.getKey();
////
////                clock_alarm_entity alarm=new clock_alarm_entity(key,AlarmName.getText().toString(),Integer.toString(hour)
////                        ,Integer.toString(minutes),checkedBoxes,true,date);
////                keyRef.setValue(alarm);
////                mAuxiliaryFunctions.openNewPage(getApplicationContext(),all_alarms.class);
////                boolean status;
////                if(AlarmDate.getText().toString().split(" ")[0].equals("Every"))status=false;
////                else status=true;
////                startAlarm(key,status);
////            }
////        });
////    }
////
////    private void UnCheckTheCheckBoxes() {
////        SundayBox.setChecked(false);
////        MondayBox.setChecked(false);
////        TuesdayBox.setChecked(false);
////        WednesdayBox.setChecked(false);
////        ThursdayBox.setChecked(false);
////        FridayBox.setChecked(false);
////        SaturdayBox.setChecked(false);
////    }
////
////    private void updateHourAndMinutesInCalendar() {
////        NotificationDate.set(Calendar.HOUR_OF_DAY, hour);
////        NotificationDate.set(Calendar.MINUTE, minutes);
////        NotificationDate.set(Calendar.SECOND, 0);
////        NotificationDate.set(Calendar.MILLISECOND, 0);
////    }
////
////    public void StartAlarmClockActivity(String key){
////        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("Alarms").child(key);
////        ref.addListenerForSingleValueEvent(new ValueEventListener(){
////            @RequiresApi(api = Build.VERSION_CODES.M)
////            @Override
////            public void onDataChange(DataSnapshot snapshot) {
////                clock_alarm_entity currentAlarm = snapshot.getValue(clock_alarm_entity.class);
////                AlarmName.setText(currentAlarm.getTitle());
////                alarmTimePicker.setHour(Integer.parseInt(currentAlarm.getHour()));
////                alarmTimePicker.setMinute(Integer.parseInt(currentAlarm.getMinuter()));
////                if (currentAlarm.getDays()==null)
////                    AlarmDate.setText(getDateAsString(currentAlarm.getDate()));
////                else{
////                    String DaysList="Every ";
////                    for(String day:currentAlarm.getDays()) {
////                        if (day.equals("0")){
////                            DaysList=DaysList+"Sun, ";
////                            SundayBox.setChecked(true);
////                        }
////                        if (day.equals("1")){
////                            DaysList=DaysList+"Mon, ";
////                            MondayBox.setChecked(true);
////                        }
////                        if (day.equals("2")){
////                            DaysList=DaysList+"Tue, ";
////                            TuesdayBox.setChecked(true);
////                        }
////                        if (day.equals("3")){
////                            DaysList=DaysList+"Wed, ";
////                            WednesdayBox.setChecked(true);
////                        }
////                        if (day.equals("4")){
////                            DaysList=DaysList+"Thur, ";
////                            ThursdayBox.setChecked(true);
////                        }
////                        if (day.equals("5")){
////                            DaysList=DaysList+"Fri, ";
////                            FridayBox.setChecked(true);
////                        }
////                        if (day.equals("6")){
////                            DaysList=DaysList+"Sat, ";
////                            SaturdayBox.setChecked(true);
////                        }
////                    }
////                    AlarmDate.setText(DaysList);
////                }
////            }
////            @Override
////            public void onCancelled(DatabaseError error) {}
////        });
////
////    }
////
////    @RequiresApi(api = Build.VERSION_CODES.M)
////    private void UpdateTextViewDays() {
////        date=null;
////        daysList="Every ";
////        if(SundayBox.isChecked()) daysList=daysList+"Sun, ";
////        if(MondayBox.isChecked()) daysList=daysList+"Mon, ";
////        if(TuesdayBox.isChecked()) daysList=daysList+"Tue, ";
////        if(WednesdayBox.isChecked()) daysList=daysList+"Wed, ";
////        if(ThursdayBox.isChecked()) daysList=daysList+"Thur, ";
////        if(FridayBox.isChecked()) daysList=daysList+"Fri, ";
////        if(SaturdayBox.isChecked()) daysList=daysList+"Sat, ";
////
////        if(daysList.equals("Every ")){
////            String text;
////            //update the new chooses hour and minutes
////            hour=alarmTimePicker.getHour();
////            minutes=alarmTimePicker.getMinute();
////            updateHourAndMinutesInCalendar();
////
////            //get the date of today and save in text
////            text="Today-"+getDateAsString(NotificationDate.getTime());
////
////            //if the chooses time is before the current time then get the date of tomorrow
////            Calendar cal=Calendar.getInstance();
////            cal.set(Calendar.HOUR_OF_DAY, hour);
////            cal.set(Calendar.MINUTE, minutes);
////            cal.set(Calendar.SECOND, 0);
////            cal.set(Calendar.MILLISECOND, 0);
////            if (cal.before(Calendar.getInstance())){
////                cal.add(Calendar.DATE,1); //or add
////                text="Tomorrow-"+getDateAsString(cal.getTime());
////            }
////            date= NotificationDate.getTime();
////            AlarmDate.setText(text);
////        }
////        else AlarmDate.setText(daysList.substring(0,daysList.length()-2));
////    }
////    @RequiresApi(api = Build.VERSION_CODES.M)
////    private void startAlarm(String key,boolean status) {
////        int i = 0;
////        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
////        Intent intent = new Intent(this, AlertReceiver.class);
////        intent.putExtra("Key", key);
////        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), key.hashCode(), intent, 0);
////        if (status) {
////            alarmManager.setExact(AlarmManager.RTC_WAKEUP, NotificationDate.getTimeInMillis(), pendingIntent);
////        } else {
////            if (daysList.contains("Sun"))  setRepeatedAlarm(i++,Calendar.SUNDAY,key,alarmManager,intent);
////            if (daysList.contains("Mon"))  setRepeatedAlarm(i++,Calendar.MONDAY,key,alarmManager,intent);
////            if (daysList.contains("Tue"))  setRepeatedAlarm(i++,Calendar.TUESDAY,key,alarmManager,intent);
////            if (daysList.contains("Wed"))  setRepeatedAlarm(i++,Calendar.WEDNESDAY,key,alarmManager,intent);
////            if (daysList.contains("Thur"))  setRepeatedAlarm(i++,Calendar.THURSDAY,key,alarmManager,intent);
////            if (daysList.contains("Fri"))  setRepeatedAlarm(i++,Calendar.FRIDAY,key,alarmManager,intent);
////            if (daysList.contains("Sat"))  setRepeatedAlarm(i++,Calendar.SATURDAY,key,alarmManager,intent);
////        }
////    }
////
////
////    public void setRepeatedAlarm(int i,int day,String key,AlarmManager alarmManager,Intent intent){
////        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), key.hashCode() + i, intent, 0);
////        NotificationDate.set(Calendar.DAY_OF_WEEK, day);
////        alarmManager.setExact(AlarmManager.RTC_WAKEUP, NotificationDate.getTimeInMillis(), pendingIntent);
////
////    }
////
////    @RequiresApi(api = Build.VERSION_CODES.M)
////    @Override
////    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {}
////
////
////
////    public String getDateAsString(Date date){
////        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
////        String[] splitDate = formattedDate.split(",");
////        String date2=splitDate[0]+","+splitDate[1];
////        return date2;
////    }
////}
