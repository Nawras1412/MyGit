package com.example.smartremindersapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class HomePage extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {
    boolean mBound = false;
    MyBackgroundService mService;
    private static String userName;
    DrawerLayout drawerLayout;
    private TextView Date1;
    private static TextView instruction;
    private Place wantedLocation;
    FloatingActionButton add_button, pen_button, locate_button;
    Boolean clicked = false;
    Integer hour,minutes;
    private boolean location_flag = false, notify_flag = false;
    private static DatabaseReference ref;
    private Dialog dialog;
//    TextView location;
    private TextView empty;
    LocationRequest locationRequest;
    int LOCATION_REQUEST_CODE = 1998;
    List<Reminder> reminders = new ArrayList<Reminder>();
    FusedLocationProviderClient fusedLocationProviderClient;
    private Spinner RemindersKindSpinner;
    public ArrayList<reminders_view> reminders_list_firebase = new ArrayList<>();
    private static RecyclerView mRecyclerView;
    private ReminderAdapter mAdapter;
    private static RecyclerView.LayoutManager mLayoutManager;
    private static HomePage instance;
    private Calendar NotificationDate;
    private ConstraintLayout background_layout;
    private ImageView menu_btn,setting_btn;
    public Spinner getRemindersKindSpinner() {
        return RemindersKindSpinner;
    }

    public static RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.home_page);
        drawerLayout = findViewById(R.id.drawer_layout);
        add_button = findViewById(R.id.btn_add);
        pen_button = findViewById(R.id.btn_pen);
        locate_button = findViewById(R.id.btn_locateReminder);
        Spinner spinner = findViewById(R.id.DateSpinner);
        RemindersKindSpinner = findViewById((R.id.KindSpinner));
        instruction = findViewById(R.id.instructions);
        TextView hello_txt = findViewById(R.id.Hello);
        pen_button = findViewById(R.id.btn_pen);
        empty = findViewById(R.id.instructions);
        Date1 = findViewById(R.id.textView);
        background_layout=findViewById(R.id.background_layout);
        menu_btn=findViewById(R.id.MenuButton);
        setting_btn=findViewById(R.id.setting_btn);
        userName = getSharedPreferences("U", MODE_PRIVATE).getString("username", null);


        String time = "Hello ";
        int items_color =R.color.white;
        int backgroungImage=0;
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,23);
        System.out.println("the hour is: "+ calendar.getTime().getHours());
        if(calendar.getTime().getHours()>=6 &&
                calendar.getTime().getHours()<12) {
            backgroungImage= R.drawable.morning_image;
            time = "Good Morning, ";
        }
        if(calendar.getTime().getHours()>=12 &&
                calendar.getTime().getHours()<17) {
            items_color=R.color.black;
            backgroungImage= R.drawable.afternoon_image;
            time = "Good Afternoon, ";
        }
        if(calendar.getTime().getHours()>=17 &&
                calendar.getTime().getHours()<20) {
            backgroungImage= R.drawable.evening_image;
            time = "Good Evening, ";
        }
        if(calendar.getTime().getHours()>=20 ||
                calendar.getTime().getHours()<6) {
            backgroungImage= R.drawable.night_image;
            time = "Good Night, ";
        }
        Date1.setTextColor(ContextCompat.getColor(getInstance(), items_color));
        setting_btn.setColorFilter(ContextCompat.getColor(getInstance(), items_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        menu_btn.setColorFilter(ContextCompat.getColor(getInstance(), items_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        hello_txt.setTextColor(ContextCompat.getColor(getInstance(), items_color));
        background_layout.setBackground(ContextCompat.getDrawable(getInstance(), backgroungImage));
        hello_txt.setText(time+ userName+"!");

        if (location_flag == true) {
            updateLocation();
        }
        Places.initialize(getApplicationContext(), "AIzaSyCfsrOq62GRNdUvZeMBhimX4RFX9cpm4uU");
        get_all_reminders_by_kind("all");
        mRecyclerView = findViewById(R.id.recycleViewR);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("my_channel_01", "hello", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel for reminders");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        add_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setVisibility(clicked);
                if (!clicked) clicked = true;
                else clicked = false;
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> KindsAdapter = ArrayAdapter.createFromResource(this, R.array.kinds, android.R.layout.simple_spinner_item);
        KindsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RemindersKindSpinner.setAdapter(KindsAdapter);
        RemindersKindSpinner.getBackground().setColorFilter(R.color.black, PorterDuff.Mode.DARKEN);


        RemindersKindSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id){
                Object item = adapterView.getItemAtPosition(position);
                String stringItem = item.toString();
                get_all_reminders_by_kind(stringItem);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        Date currentDate = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentDate);
        String[] splitDate = formattedDate.split(",");
        int index = formattedDate.indexOf(",");
        String day = splitDate[0];
        int spinnerPosition = adapter.getPosition(day);
        spinner.setSelection(spinnerPosition);
        spinnerPosition = KindsAdapter.getPosition("all");
        RemindersKindSpinner.setSelection(spinnerPosition);
        Date1.setText(formattedDate.substring(index + 1));


        pen_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                addTodoList a=new addTodoList(userName);
                a.openDialog(false,null,0);
            }
        });
        locate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                addReminder a=new addReminder(userName);
                a.openDialog(false,null,0);
            }
        });
    }


    public void addReminder(String type) {
        NotificationDate=Calendar.getInstance();
        dialog = new Dialog(HomePage.this);
        dialog.setContentView(R.layout.anna_reminder);
        Button add_btn;
        ImageButton cancel_btn, selectDate_btn, addDescription_btn, location_btn;
        TextView  title;
        selectDate_btn = dialog.findViewById(R.id.selectDate);
        cancel_btn = dialog.findViewById(R.id.cancel_Btn);
        add_btn = dialog.findViewById(R.id.addButton);
        addDescription_btn = dialog.findViewById(R.id.addDiscription);
        location_btn = dialog.findViewById(R.id.location);
        title = dialog.findViewById(R.id.Title);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


//        addDescription_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent=new Intent(this,)
//            }
//        });


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("reminder_list");
//                DatabaseReference keyRef =ref.push();
//                Reminder reminder = new Reminder();
//                reminder.setKey(keyRef.getKey());
//                reminder.setMyType(type);
//                reminder.setState(true);
//                reminder.setMessage(title.getText().toString().trim());
//                if (Objects.equals(type,"Date"))
//                {
//                    try{
////                        if(time_date.getText().toString().isEmpty())
////                            reminder.setMyType("Todo List");
////                        System.out.println("the setted date is: "+time_date.getText().toString().charAt(8));
////                        Date remind = new Date(time_date.getText().toString().charAt(8));
//                        Date remind=NotificationDate.getTime();
//                        reminder.setRemindDate(remind);
////                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
//                        sendToAlarmManager(reminder);
////                        calendar.setTime(remind);
////                        calendar.set(Calendar.SECOND, 0);
//                    }
//                    catch (Exception e){
//                        Toast.makeText(HomePage.this, "Missing Location", Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    }
//                }
//                else{
//                    try{
//                        reminder.setLocationAsString(location.getText().toString());
//                        reminder.setLAT(wantedLocation.getLatLng().latitude);
//                        reminder.setLNG(wantedLocation.getLatLng().longitude);}
//                    catch (Exception e) {
//                        Toast.makeText(HomePage.this, "Missing Location", Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    }
//                }
//                reminder.setDescription(discription.getText().toString());
//                reminders.add(reminder);
//
////                DatabaseReference keyRef =ref.push();
////                reminder.setKey(keyRef.getKey());
//                keyRef.setValue(reminder);
//                Log.e("ID chahiye", reminder.getId() + "");
//                Toast.makeText(HomePage.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//                ArrayAdapter<CharSequence> KindsAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.kinds, android.R.layout.simple_spinner_item);
//                KindsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                int spinnerPosition = KindsAdapter.getPosition("all");
//                RemindersKindSpinner.setSelection(spinnerPosition);
//                get_all_reminders_by_kind("all");
//                mRecyclerView.setHasFixedSize(true);
            }
        });


        selectDate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalender = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(HomePage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                        final Calendar newDate = Calendar.getInstance();
                        Calendar newTime = Calendar.getInstance();
                        NotificationDate = Calendar.getInstance();
                        NotificationDate.set(Calendar.MONTH, month);
                        NotificationDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        NotificationDate.set(Calendar.YEAR, year);
//                            date=NotificationDate.getTime();
                        TimePickerDialog time = new TimePickerDialog(HomePage.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                                    hour=hourOfDay;
//                                    minutes=minute;
                                NotificationDate.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                NotificationDate.set(Calendar.MINUTE,minute);
                                NotificationDate.set(Calendar.SECOND,0);
                                newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);
                                Calendar tem = Calendar.getInstance();
                                Log.w("TIME", System.currentTimeMillis() + "");
                                if (newDate.getTimeInMillis() - tem.getTimeInMillis() > 0) {
//                                        time_date.setText(newDate.getTime().toString());
//                                        time_date.setVisibility(View.VISIBLE);
                                } else
                                    Toast.makeText(HomePage.this, "Invalid time", Toast.LENGTH_SHORT).show();

                            }
                        }, newTime.get(Calendar.HOUR_OF_DAY), newTime.get(Calendar.MINUTE), true);
                        time.show();
                    }
                }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();
            }
        });


        Dexter.withActivity(HomePage.this).withPermissions(Arrays.asList(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                location_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mService.requestLocationUpdates();
                    }
                });
                bindService(new Intent(HomePage.this, MyBackgroundService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
            }
            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {}
        }).check();

        if (notify_flag == false) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
            builder.setTitle("Required Permission !");
            builder.setMessage("In order to get a notification pop-up ,go to Setting->App Notification and allow Floating Notifications and Sound for this App ");
            builder.setPositiveButton("OK", null);
            builder.show();
        }
        notify_flag = true;


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }














//        if (Objects.equals(type,"Location")){
//            Dexter.withActivity(HomePage.this).withPermissions(Arrays.asList(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)).withListener(new MultiplePermissionsListener() {
//                @Override
//                public void onPermissionsChecked(MultiplePermissionsReport report) {
//                    location_btn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
////                            location.setVisibility(View.VISIBLE);
//                            mService.requestLocationUpdates();
//                        }
//                    });
////                    location.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            List<Place .Field> fieldList=Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME,Place.Field.TYPES);
////                            Intent intentL= new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(HomePage.this);
////                            startActivityForResult(intentL,100);
////                        }
////
////                    });
//                    bindService(new Intent(HomePage.this, MyBackgroundService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
//                }
//                @Override
//                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {}
//            }).check();
//
//            if (notify_flag == false) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
//                builder.setTitle("Required Permission !");
//                builder.setMessage("In order to get a notification pop-up ,go to Setting->App Notification and allow Floating Notifications and Sound for this App ");
//                builder.setPositiveButton("OK", null);
//                builder.show();
//
//            }
//            notify_flag = true;
//        }
//
//








////        final Calendar newCalender = Calendar.getInstance();
//        if (Objects.equals(type,"Date")) {
//            selectDate_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Calendar newCalender = Calendar.getInstance();
//                    DatePickerDialog dialog = new DatePickerDialog(HomePage.this, new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
//                            final Calendar newDate = Calendar.getInstance();
//                            Calendar newTime = Calendar.getInstance();
//                            NotificationDate = Calendar.getInstance();
//                            NotificationDate.set(Calendar.MONTH, month);
//                            NotificationDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                            NotificationDate.set(Calendar.YEAR, year);
////                            date=NotificationDate.getTime();
//                            TimePickerDialog time = new TimePickerDialog(HomePage.this, new TimePickerDialog.OnTimeSetListener() {
//                                @Override
//                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
////                                    hour=hourOfDay;
////                                    minutes=minute;
//                                    NotificationDate.set(Calendar.HOUR_OF_DAY,hourOfDay);
//                                    NotificationDate.set(Calendar.MINUTE,minute);
//                                    NotificationDate.set(Calendar.SECOND,0);
//                                    newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);
//                                    Calendar tem = Calendar.getInstance();
//                                    Log.w("TIME", System.currentTimeMillis() + "");
//                                    if (newDate.getTimeInMillis() - tem.getTimeInMillis() > 0) {
////                                        time_date.setText(newDate.getTime().toString());
////                                        time_date.setVisibility(View.VISIBLE);
//                                    } else
//                                        Toast.makeText(HomePage.this, "Invalid time", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }, newTime.get(Calendar.HOUR_OF_DAY), newTime.get(Calendar.MINUTE), true);
//                            time.show();
//                        }
//                    }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
//
//                    dialog.getDatePicker().setMinDate(System.currentTimeMillis());
//                    dialog.show();
//                }
//            });
//        }
//







    //        addDescription_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                discription.setVisibility(View.VISIBLE);
//                discription.setEnabled(true);
//            }
//        });



//        add_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("reminder_list");
//                DatabaseReference keyRef =ref.push();
//                Reminder reminder = new Reminder();
//                reminder.setKey(keyRef.getKey());
//                reminder.setMyType(type);
//                reminder.setState(true);
//                reminder.setMessage(title.getText().toString().trim());
//                if (Objects.equals(type,"Date"))
//                {
//                    try{
////                        if(time_date.getText().toString().isEmpty())
////                            reminder.setMyType("Todo List");
////                        System.out.println("the setted date is: "+time_date.getText().toString().charAt(8));
////                        Date remind = new Date(time_date.getText().toString().charAt(8));
//                        Date remind=NotificationDate.getTime();
//                        reminder.setRemindDate(remind);
////                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
//                        sendToAlarmManager(reminder);
////                        calendar.setTime(remind);
////                        calendar.set(Calendar.SECOND, 0);
//                    }
//                    catch (Exception e){
//                        Toast.makeText(HomePage.this, "Missing Location", Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    }
//                }
//                else{
//                    try{
//                        reminder.setLocationAsString(location.getText().toString());
//                        reminder.setLAT(wantedLocation.getLatLng().latitude);
//                        reminder.setLNG(wantedLocation.getLatLng().longitude);}
//                    catch (Exception e) {
//                        Toast.makeText(HomePage.this, "Missing Location", Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    }
//                }
//                reminder.setDescription(discription.getText().toString());
//                reminders.add(reminder);
//
////                DatabaseReference keyRef =ref.push();
////                reminder.setKey(keyRef.getKey());
//                keyRef.setValue(reminder);
//                Log.e("ID chahiye", reminder.getId() + "");
//                Toast.makeText(HomePage.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//                ArrayAdapter<CharSequence> KindsAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.kinds, android.R.layout.simple_spinner_item);
//                KindsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                int spinnerPosition = KindsAdapter.getPosition("all");
//                RemindersKindSpinner.setSelection(spinnerPosition);
//                get_all_reminders_by_kind("all");
//                mRecyclerView.setHasFixedSize(true);
//            }
//        });
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();
//    }







    public void get_all_reminders_by_kind(String ReminderType) {
        ArrayList<Reminder> reminders_locationList = new ArrayList<>();
        ArrayList<reminders_view> reminders_views_list = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(userName).child("reminder_list");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Reminder reminder = ds.getValue(Reminder.class);
                    if(ReminderType.equals("all")){
                        reminders_locationList.add(reminder);
                    }
                    else {
                        if (reminder.getMyType().contains(ReminderType))
                            reminders_locationList.add(reminder);

                    }
                }
                for (Reminder reminder : reminders_locationList) {
                    String message = reminder.getMessage();
                    String type = reminder.getMyType();
                    String description = reminder.getDescription();
                    String key = reminder.getKey();
                    Date date=reminder.getRemindDate();
                    String location=reminder.getLocationAsString();
                    reminders_view remindView = new reminders_view(key,message, description,type,date,location);
                    if (type.contains("Location")) {
                        remindView.setLAT(reminder.getLAT());
                        remindView.setLNG(reminder.getLNG());
                        remindView.setLocationAsString(reminder.getLocationAsString());
                    }
                    else{
                        remindView.setDate(reminder.getRemindDate());
                    }
                    reminders_views_list.add(remindView);
                }
                c(reminders_views_list);
                setInstruction(reminders_views_list.size());
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }



    public void c(ArrayList<reminders_view> reminderss) {
        mAdapter = new ReminderAdapter(getApplicationContext(), reminderss, userName);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        if (mRecyclerView.getAdapter().getItemCount()==0)
        {
            if (mBound) {
                unbindService(mServiceConnection);
                mBound = false;
            }
            PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener) this);
            EventBus.getDefault().unregister(this);
        }
        mAdapter.setOnItemClickListener(new ReminderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                reminders_view currentReminder=reminderss.get(position);
//                DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("reminder_list").child(currentReminder.getKey());
//                ref.removeValue();
//                addReminder(currentReminder.getType());
            }
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }


    public void removeItem(int position) {
        reminders_list_firebase.remove(position);
        mAdapter.notifyItemRemoved(position);
    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {}



    public static void setInstruction(int v) {
        if(v==0)
            instruction.setVisibility(View.VISIBLE);
        else
            instruction.setVisibility(View.INVISIBLE);
    }


    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            MyBackgroundService.LocalBinder binder = (MyBackgroundService.LocalBinder) iBinder;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };




    private void setVisibility(Boolean clicked) {
        if (!clicked) {
            pen_button.setVisibility(View.VISIBLE);
            locate_button.setVisibility(View.VISIBLE);
        } else {
            pen_button.setVisibility(View.INVISIBLE);
            locate_button.setVisibility(View.INVISIBLE);
        }
    }


    public void ClickMenu(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view) {
        // recreate();
        //redirectActivity(this, HomePage.class, sessionId);
    }

    public void ClickDashboard(View view) {
        redirectActivity(this, all_alarms.class, userName);
    }

    public void ClickAboutUs(View view) {
        redirectActivity(this, AboutUs.class, userName);
    }

    public void ClickLogout(View view) {
        logout(this);
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("are you sure you want to log out?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ref=FirebaseDatabase.getInstance().getReference().child("Users").child(userName);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap hashmap = new HashMap();
                        hashmap.put("status", "0");
                        ref.updateChildren(hashmap);
                        activity.finishAffinity();
                        Intent intent = new Intent(activity, login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("logout",true);
                        activity.startActivity(intent);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    public static void redirectActivity(Activity activity, Class aClass, String sessionId) {
        Intent intent = new Intent(activity, aClass);
        intent.putExtra("User Name", sessionId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode==100)&&(resultCode==RESULT_OK))
        {
            Place place = Autocomplete.getPlaceFromIntent(data);
//            location.setText(place.getAddress());
            wantedLocation=place;
        }
        else if (resultCode== AutocompleteActivity.RESULT_ERROR)
        {
            Status status =Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();;
        }
    }





    private void sendToAlarmManager(Reminder reminder) {
//        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
//        calendar.setTime(remind);
//        calendar.set(Calendar.SECOND, 0);
        Intent ServiceIntent=new Intent(this,NotifierLocationRemind.class);
        stopService(ServiceIntent);
        ServiceIntent.putExtra("date",reminder.getRemindDate().getTime());
        ServiceIntent.putExtra("DateRemind",true);
        ServiceIntent.putExtra("key",reminder.getKey());
        ServiceIntent.putExtra("name",reminder.getMessage());
        ServiceIntent.putExtra("Pending_key",reminder.getKey().hashCode());
        ServiceIntent.putExtra("userName",userName);
        startService(ServiceIntent);
    }

    public static HomePage getInstance() {
        return instance;
    }

    private void updateLocation() {
        buildLocationRequest();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(HomePage.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);//change to 5 min 300000
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.d("onSuccess", location.toString());
                }
            }
        });
    }


    public void get_all_the_reminders_from_firebase2(String userName,Location mLocation ) {
        final boolean[] c = {false};
        ArrayList<Reminder> reminders_locationList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("reminder_list");
        String b ;
        Intent intent=new Intent(this, NotifierLocationRemind.class);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("IN 2");
                for (DataSnapshot ds : snapshot.getChildren()) {
                    System.out.println("nnnnnnnnn");
                    Reminder reminder = ds.getValue(Reminder.class);
                    System.out.println(reminder.getMessage());
                    if (Objects.equals(reminder.getMyType(),"Location"))
                        reminders_locationList.add(reminder);
                }
                for (Reminder remind : reminders_locationList) {
                    double lat= remind.getLAT();
                    double lng= remind.getLNG();

                    float[] distance = new float[1];
                    double lat1 = 32.7614296;
                    double lng1 = 35.0195184;
                    Location.distanceBetween(lat, lng, lat1, lng1, distance);
                    if ((distance[0] < 3000)&& (remind.isState() == true)) {
                        stopService(intent);
                        intent.putExtra("lat",remind.getLAT());
                        intent.putExtra("lng",remind.getLNG());
                        intent.putExtra("state",remind.isState());
                        intent.putExtra("name",remind.getMessage());
                        intent.putExtra("key",remind.getKey());
                        intent.putExtra("Pending_key",remind.getKey().hashCode());
                        intent.putExtra("userName",userName);
                        startService(intent);
                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("reminder_list").child(remind.getKey());
                        HashMap hashmap = new HashMap();
                        hashmap.put("state", false);
                        ref.updateChildren(hashmap);
                    }
                }
                c[0] =true;
            }

            @Override
            public void onCancelled(DatabaseError error) {}

        });
    }






}