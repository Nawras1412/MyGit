package com.example.smartremindersapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class HomePage extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private static String userName;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    DrawerLayout drawerLayout;
    private TextView Date1;
    private static TextView instruction;
    private Place wantedLocation;
    FloatingActionButton add_button, pen_button, locate_button;
    Boolean clicked = false;
    Integer hour,minutes;
    boolean mBound = false;
    private boolean location_flag = false, notify_flag = false;
    static HomePage instance;
    private static DatabaseReference ref;
    private Dialog dialog;
    TextView location;
    private TextView empty;

    public static void setInstruction(int v) {
        if(v==0) {
            instruction.setVisibility(View.VISIBLE);
        }
        else{
            instruction.setVisibility(View.INVISIBLE);
        }
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

    MyBackgroundService mService;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    int LOCATION_REQUEST_CODE = 1998;
    //TextView txt_location;
    List<Reminder> reminders = new ArrayList<Reminder>();

//    private static SharedPreferences.Editor s;

    public static HomePage getInstance() {
        return instance;
    }

    public ArrayList<remindres_view> reminders_list_firebase = new ArrayList<>();
    private static RecyclerView mRecyclerView;
    private ReminderAdapter mAdapter;
    private static RecyclerView.LayoutManager mLayoutManager;

    public static void setmRecyclerView(RecyclerView mRecyclerView) {
        mRecyclerView = mRecyclerView;
    }

    public String getSessionId() {
        return userName;
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener) HomePage.this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        // EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {

//        if (mBound) {
//            unbindService(mServiceConnection);
//            mBound = false;
//
//        }
//        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener) this);
//        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        instruction=findViewById(R.id.instructions);
        sharedPreferences=getSharedPreferences("U",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        userName=sharedPreferences.getString("username",null);

//        s=(SharedPreferences.Editor) getIntent().getParcelableExtra("pref");
        TextView hello_txt = findViewById(R.id.Hello);
        hello_txt.setText("Hello " + userName);

        //Toast.makeText(HomePage.this,sessionId,Toast.LENGTH_LONG).show();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //.findFragmentById(R.id.mapAPI);
        //mapFragment.getMapAsync(this);
        instance = this;
        if (location_flag == true){
            updateLocation();
        }
//
        // Initialize the SDK
        Places.initialize(getApplicationContext(), "AIzaSyCfsrOq62GRNdUvZeMBhimX4RFX9cpm4uU");

        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);


        reminders_list_firebase = get_all_the_reminders_from_firebase(userName);
        if ((reminders_list_firebase.isEmpty())) {
            instruction.setVisibility(View.INVISIBLE);
            System.out.println("ddddddddddddddd");
        }


        else{
            System.out.println("wrytjujuuuuuuuuuuuuu");

            instruction.setVisibility(View.VISIBLE);
        }
        if (!(reminders_list_firebase.isEmpty())) {
            System.out.println("cccccccccccccccc");
            instruction.setVisibility(View.INVISIBLE);
        }
        mRecyclerView = findViewById(R.id.recycleViewR);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("my_channel_01", "hello", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel for reminders");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
//        sessionId = getIntent().getStringExtra("User Name");
        drawerLayout = findViewById(R.id.drawer_layout);
        add_button = findViewById(R.id.btn_add);
        pen_button = findViewById(R.id.btn_pen);
        locate_button = findViewById(R.id.btn_locateReminder);
        //txt_location = findViewById(R.id.txt_location);
        Spinner spinner = findViewById(R.id.spinner1);
        add_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setVisibility(clicked);
                //setAnimation(clicked);
                if (!clicked) clicked = true;
                else clicked = false;
                // Code here executes on main thread after user presses button
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Date currentDate = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentDate);
        String[] splitDate = formattedDate.split(",");
        int index = formattedDate.indexOf(",");
        String day = splitDate[0];
        int spinnerPosition = adapter.getPosition(day);
        spinner.setSelection(spinnerPosition);
        Date1 = findViewById(R.id.textView);
        Date1.setText(formattedDate.substring(index + 1));
        Log.d("MyLOG", currentDate.toString());

        pen_button = findViewById(R.id.btn_pen);
         empty = findViewById(R.id.instructions);

        pen_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                addReminder("Date");

            }
        });

        locate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReminder("Location");
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void askPermissionL() {
        shouldShowRequestPermissionRationale("Location");

    }


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



                //              Intent intent=new Intent(this, login.class);
//                startActivity(intent);


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
    public void onNothingSelected(AdapterView<?> parent) {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode==100)&&(resultCode==RESULT_OK))
        {
            Place place = Autocomplete.getPlaceFromIntent(data);

            location.setText(place.getAddress());
            wantedLocation=place;

            //  location.setText(String.format("Locality Name : %s",place.getName()));
        }
        else if (resultCode== AutocompleteActivity.RESULT_ERROR)
        {
            Status status =Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();;
        }
    }
    private void ServiceCaller(Intent intent,int H,int M) {
        stopService(intent);
        intent.putExtra("hour", H);
        intent.putExtra("minutes", M);
        startService(intent);
    }


    public void addReminder(String type) {

        dialog = new Dialog(HomePage.this);
        dialog.setContentView(R.layout.add_reminder);

        // final TextView textView = dialog.findViewById(R.id.date_Time);
        Button add;

        ImageButton cancel, select, add_text, location_btn;
        TextView time_date, title, discription;
        select = dialog.findViewById(R.id.select);
        cancel = dialog.findViewById(R.id.cancel_Btn);
        add = dialog.findViewById(R.id.addButton);
        discription = dialog.findViewById(R.id.discription);
        time_date = dialog.findViewById(R.id.searchlocation);
        add_text = dialog.findViewById(R.id.addDiscription);
        location_btn = dialog.findViewById(R.id.location);
        title = dialog.findViewById(R.id.Title);
        location = dialog.findViewById(R.id.location_txt2);
        // final EditText message = dialog.findViewById(R.id.message);
        if(Objects.equals(type,"Date"))
        {
            select.setVisibility(View.VISIBLE);
            location_btn.setVisibility(View.INVISIBLE);
        }
        else
        {
            location_btn.setVisibility(View.VISIBLE);
            select.setVisibility(View.INVISIBLE);

        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });
        add_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discription.setVisibility(View.VISIBLE);
                discription.setEnabled(true);
            }
        });
        if (Objects.equals(type,"Location"))
        {
            Dexter.withActivity(HomePage.this).withPermissions(Arrays.asList(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)).withListener(new MultiplePermissionsListener() {


                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    location_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            location.setVisibility(View.VISIBLE);
                            mService.requestLocationUpdates();

                        }

                    });
                    location.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            List<Place .Field> fieldList=Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME,Place.Field.TYPES);
                            Intent intentL= new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(HomePage.this);

                            startActivityForResult(intentL,100);
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

        }


        final Calendar newCalender = Calendar.getInstance();
        if (Objects.equals(type,"Date")) {
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog dialog = new DatePickerDialog(HomePage.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                            final Calendar newDate = Calendar.getInstance();
                            Calendar newTime = Calendar.getInstance();
                            TimePickerDialog time = new TimePickerDialog(HomePage.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    hour=hourOfDay;

                                    minutes=minute;
                                    newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);
                                    Calendar tem = Calendar.getInstance();
                                    Log.w("TIME", System.currentTimeMillis() + "");
                                    if (newDate.getTimeInMillis() - tem.getTimeInMillis() > 0) {
                                        time_date.setText(newDate.getTime().toString());
                                        time_date.setVisibility(View.VISIBLE);
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

        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                instruction.setVisibility(View.INVISIBLE);
                Reminder reminder = new Reminder();
                reminder.setMyType(type);
                reminder.setState(true);
                reminder.setMessage(title.getText().toString().trim());
                if (Objects.equals(type,"Date"))
                {
                    try{
                        Date remind = new Date(time_date.getText().toString().charAt(8));
                        reminder.setRemindDate(remind);
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                        calendar.setTime(remind);
                        calendar.set(Calendar.SECOND, 0);}
                    catch (Exception e) {
                        Toast.makeText(HomePage.this, "Missing Location", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else{
                    try{
                        reminder.setLAT(wantedLocation.getLatLng().latitude);
                        reminder.setLNG(wantedLocation.getLatLng().longitude);}
                    catch (Exception e) {
                        Toast.makeText(HomePage.this, "Missing Location", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
                reminder.setDescription(discription.getText().toString());
                reminders.add(reminder);
                ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("reminder_list");

                DatabaseReference keyRef =ref.push();



                System.out.println("KEY::");
                System.out.println(ref.getKey());
                reminder.setKey(keyRef.getKey());
                keyRef.setValue(reminder);
                System.out.println(ref.child(reminder.getMessage().toString()).getKey());


//                ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        temp=snapshot.getValue(Reminder.class);
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                })
//                ref.child("Reminders_list").setValue(reminders);
//                roomDAO.Insert(reminders);
//                List<Reminders> l = roomDAO.getAll();
//                reminders = l.get(l.size()-1);
                Log.e("ID chahiye", reminder.getId() + "");
                if(Objects.equals(type,"Date"))
                {
                    Intent intent=new Intent(HomePage.this,NotifierRemind.class);
                    stopService(intent);
                    System.out.println("TIMEEEEEE");

                    System.out.println(hour);
                    System.out.println(minutes);

                    intent.putExtra("hour",hour);
                    intent.putExtra("minutes",minutes);
                    //intent.putExtra("date")
                    //intent.putExtra("date",time_date.getText().toString().trim());
                    startService(intent);
                }


                // Intent intent = new Intent(HomePage.this, NotifierAlarm.class);
                /*intent.putExtra("Message", reminder.getMessage());
                intent.putExtra("RemindDate", reminder.getRemindDate().toString());
                PendingIntent intent1 = PendingIntent.getBroadcast(HomePage.this, reminder.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                //AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
*/

                //     System.out.println(time_date.getText().toString().trim());
                //       System.out.println(reminder.getRemindDate().getTime());
                //alarmManager.set(AlarmManager.RTC_WAKEUP, reminder.getRemindDate().getTime(), intent1);

                Toast.makeText(HomePage.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
//                ref = FirebaseDatabase.getInstance().getReference().child("Reminders").child(reminders.getMessage().toString());
//                ref.setValue(reminders);
//                setItemsInRecyclerView();
                dialog.dismiss();
                reminders_list_firebase = get_all_the_reminders_from_firebase(userName);
                mRecyclerView.setHasFixedSize(true);
                // mLayoutManager = new LinearLayoutManager(this);

            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onListenLocation(SendLocationToActivity event) {
        if (event != null) {
            String data = new StringBuilder()
                    .append(event.getLocation().getLatitude())
                    .append("/")
                    .append(event.getLocation().getLongitude())
                    .toString();
//            Toast.makeText(mService, data, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLocation() {
        buildLocationRequest();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(HomePage.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);//change to 5 min 300000
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);
    }

//    public void updateTextView(String value) {
//        HomePage.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                txt_location.setText(value);
//            }
//        });
//
//    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, MyLocationService.class);
        intent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    private void askLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {

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

//    public void setItemsInRecyclerView() {
//        // RoomDAO dao = appDatabase.getRoomDAO();
//        //temp = dao.orderThetable();
//        //if(temp.size()>0) {
//        empty.setVisibility(View.INVISIBLE);
//        recyclerView.setVisibility(View.VISIBLE);
//        //}
//        adapter = new AdapterReminders(temp);
//        recyclerView.setAdapter(adapter);
//    }


    public ArrayList<remindres_view> get_all_the_reminders_from_firebase(String userName) {
        ArrayList<remindres_view> remindres_views_list = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("reminder_list");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Double LAT  = null;
                    Double LNG =null;

                    Date date = null;

                    Reminder reminder = ds.getValue(Reminder.class);
                    String massege = reminder.getMessage();
                    String type = reminder.getMyType();
                    if (Objects.equals(type,"Location")) {
                        //mylocation =reminder.getMyLocation();
                        LAT  = reminder.getLAT();
                        LNG =reminder.getLNG();
                    }
                    else{

                        date = reminder.getRemindDate();
                    }
                    //Date date = reminder.getRemindDate();
                    String discription = reminder.getDescription();
                    String key = reminder.getKey();
                    remindres_view remindView = new remindres_view(key,massege, discription,type);
                    if (Objects.equals(type,"Location")) {
                        remindView.setLAT(LAT);
                        remindView.setLNG(LNG);

                    }
                    else{
                        remindView.setDate(date);
                    }
                    remindres_views_list.add(remindView);
                }



                c(remindres_views_list, userName);
            }


            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        return remindres_views_list;
    }


    public void get_all_the_reminders_from_firebase2(String userName,Location mLocation ) {
        //String userName=sessionId;
        final boolean[] c = {false};
        ArrayList<Reminder> reminders_locationList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("reminder_list");
        String b ;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("IN 2");
                for (DataSnapshot ds : snapshot.getChildren()) {
                    System.out.println("nnnnnnnnn");
                    Reminder reminder = ds.getValue(Reminder.class);
                    System.out.println(reminder.getMessage());
//                    System.out.println(reminder.getMyType());
//                    System.out.println(Objects.equals(reminder.getMyType(),"Location"));
                    if (Objects.equals(reminder.getMyType(),"Location"))
                    {
                        System.out.println("IN reminder!");
//                        Reminder remind1 = new Reminder();
//                        remind1.setMessage(reminder.getMessage());
//                        remind1.setDescription(reminder.getDescription());
//                        remind1.setKey(reminder.getKey());
//                        remind1.setLNG(reminder.getLNG());
//                        remind1.setLAT(reminder.getLAT());
                        reminders_locationList.add(reminder);

                    }
                }

                for (Reminder remind : reminders_locationList) {
                    System.out.println("njxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                    double lat= remind.getLAT();
                    double lng= remind.getLNG();
                    float[] distance = new float[1];
                    double lat1 = 32.7614296;
                    double lng1 = 35.0195184;

                    Location.distanceBetween(lat, lng, lat1, lng1, distance);
                    // distance[0] is now the distance between these lat/lons in meters
                    System.out.println("Distanceee");
                    System.out.println(distance[0]);

                    if ((distance[0] < 100.1881594)&& (remind.isState() == true)) {
                        // your code...

                        Toast.makeText(HomePage.getInstance(), "You ARRIVED", Toast.LENGTH_SHORT).show();
                        System.out.println("You ARRIVED OMGGGGGGGGGGGGGGGGGGGG");
                        Intent intent = new Intent(HomePage.getInstance(), NotifierRemindLocation.class);
                        intent.putExtra("title",remind.getMessage());
                        stopService(intent);

                        startService(intent);
                        remind.setState(false);

                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("reminder_list").child(remind.getKey());
                        HashMap hashmap = new HashMap();
                        hashmap.put("state", false);
                        ref.updateChildren(hashmap);
                    }
                }


                System.out.println("nnnnnqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqnnnnnnn");
                c[0] =true;
                System.out.println("lasttttttttttttttttttttttt");
                System.out.println(reminders_locationList.isEmpty());
                System.out.println(reminders_locationList.size());
                System.out.println("lasttttttttttttttttttttttt");
            }

            @Override
            public void onCancelled(DatabaseError error) {}

        });
//        while(c[0]==false){}




//        DatabaseReference ref2=FirebaseDatabase.getInstance().getReference().child("Users").child(userName);
//        HashMap hashmap = new HashMap();
//        hashmap.put("status", "1");
//        ref2.updateChildren(hashmap);
//        System.out.println("lasttttttttttttttttttttttt");
//        System.out.println(reminders_locationList.isEmpty());
//        System.out.println(reminders_locationList.size());
//        System.out.println("lasttttttttttttttttttttttt");
//
//        return reminders_locationList;

    }


    public void removeItem(int position) {
        reminders_list_firebase.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
//    public void addItem(int position,Reminder reminder) {
//        reminders_list_firebase.a;
//        mAdapter.notifyItemRemoved(position);
//    }


    public void changeItem(int position) {

        mAdapter.notifyItemRemoved(position);
    }

    public void c(ArrayList<remindres_view> reminderss, String userName) {
        mAdapter = new ReminderAdapter(this, reminderss, userName);
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


                remindres_view currentReminder=reminderss.get(position);
                DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(userName).child("reminder_list").child(currentReminder.getKey());
                ref.removeValue();
                //openNewPage(HomePage.class);
                addReminder(currentReminder.getType());
                //changeItem(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    public void openNewPage(Class classX) {
        Intent intent = new Intent(this, classX);
        //String sessionId = getIntent().getStringExtra("User Name");
        //intent.putExtra("User Name", sessionId);
        startActivity(intent);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}

