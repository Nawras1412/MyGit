package com.example.smartremindersapp2;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class addReminder extends AppCompatActivity {
    private Dialog add_reminder_dialog, add_description_dialog,add_location_dialog,category_menu_dialog;
    private Button add_btn;
    private HorizontalScrollView images_scroll;
    private ImageView image_view;
    public TextView LocationTextView;
    private ImageButton cancel_btn, selectDate_btn, addDescription_btn, location_btn;//,searchIcon;
    private ImageButton selectDateImage, addDescriptionImage, locationImage;
    private ImageButton removeDate,removeLocation,removeDescription;
    private FloatingActionButton mRecordBtn;
    private TextView title,DescriptionTextView,TimeTextView;
    //    public TextView searchLocationbar;
    private EditText AddDescriptionEditText;
    private Button save_description,cancel_desc_dialog,searchLocation;
    private Reminder reminder;
    private ConstraintLayout dialog_layout;
    private RelativeLayout background_layout;
    private Calendar NotificationDate;
    private RadioGroup LocationMenu;
    private RadioButton radioButtonOption;
    private String Category="";
    private String UserName;
    private int numberOfAdditions;
    private List<String> Additions;
    private List<ImageButton> Additions_Images;
    private List<TextView> Additions_TextView;
    private List<ImageButton> remove_icons;
    private Date oldDate=new Date();
    private Date date=new Date();
    public static Place wantedLocation;
    private static addReminder instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Uri imageUri;
    private Uri oneClipImage=null;
    private ClipData clipimages=null;
    private static final int IMAGE_REQUEST=2;
    private DatabaseReference keyRef;
    public addReminder(String userName) { UserName=userName; }
    public String address;
    public Double lat=null;
    public Double lang=null;
    public addReminder() {}
    private TextView mRecordLabel;
    private MediaRecorder mRecorder;
    private static final String LOG_TAG="Record_log";
    private StorageReference mStorage=FirebaseStorage.getInstance().getReference();
    public static final int RECORD_AUDIO = 0;
    private ScrollView all_audios;
    private ImageView delete_audio;
    private List<String> all_audios_list;
    //boolean isRotate =false;
    private FloatingActionButton btn_food,btn_other,btn_office,btn_car,btn_shop,btn_book,btn_medical,btn_money;
    private ExtendedFloatingActionButton btn_food_res,btn_food_cafe,btn_food_bakery,btn_money_bank,btn_money_atm,btn_medical_hospital,btn_medical_pharmacy,btn_car_wash,btn_car_repair,btn_car_gas,btn_car_parking,btn_shop_supermarket,btn_shop_mall,btn_office_laywer,btn_office_acoounting,btn_office_police,btn_office_post_office,btn_book_library,btn_book_uni,btn_book_book_store;
    private int currentPosition=0;
    private SeekBar seekBar2;
    private boolean play=false;
    private Handler handler=new Handler();
    private String PathName="";
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer= new MediaPlayer();;
    final int REQUEST_PERMISSION_CODE=1000;
    //    ArrayList<Boolean> isRotate=new ArrayList<Boolean>(Arrays.asList(false,false,false,false,false,false,false));
//    ArrayList<FloatingActionButton> Main_btns=new ArrayList<FloatingActionButton>(Arrays.asList(btn_food,btn_office,btn_car,btn_shop,btn_book,btn_medical,btn_money,btn_other));
    ArrayList<Boolean> isRotate=new ArrayList<Boolean>();
    // ArrayList<FloatingActionButton> Main_btns=new ArrayList<FloatingActionButton>(2);
    FloatingActionButton Main_btns[] =new FloatingActionButton[7];
    ArrayList<ArrayList<ExtendedFloatingActionButton>> subList = new ArrayList<ArrayList<ExtendedFloatingActionButton>>();




    private void ExpandDialogAndSetData2(ImageButton removeIcon, ImageButton image_button, TextView text_view, String string, String type) {
        numberOfAdditions+=1;
        int WindowHeight=440+Additions.size()*110;
        Additions.add(type);
        Additions_Images.add(image_button);
        Additions_TextView.add(text_view);
        remove_icons.add(removeIcon);
        ViewGroup.LayoutParams params = background_layout. getLayoutParams();
        params.height=WindowHeight;
        background_layout.setLayoutParams(params);
        ViewGroup.LayoutParams params2 = dialog_layout. getLayoutParams();
        params2.height=WindowHeight;
        dialog_layout.setLayoutParams(params2);

        removeIcon.setVisibility(View.VISIBLE);
        removeIcon.setEnabled(true);
        image_button.setVisibility(View.VISIBLE);
        image_button.setEnabled(false);
        text_view.setEnabled(false);
        text_view.setVisibility(View.VISIBLE);
        text_view.setText(string);
        if(numberOfAdditions==1){
            System.out.println("im in 1");
            ArrangingTheWindow(50,30,0);//160   140
        }
        if(numberOfAdditions==2){
            System.out.println("im in 2");
            ArrangingTheWindow(-20,-40,0);  // 120  100
            ArrangingTheWindow(80,60,1);  // 220  200
        }
        if(numberOfAdditions==3){
            System.out.println("im in 3");
            ArrangingTheWindow(-80,-100,0);
            ArrangingTheWindow(20,0,1);
            ArrangingTheWindow(120,100,2);
        }
    }

    private void ArrangingTheWindow(int NextTextViewY, int NextImageButtonY,int index){
        System.out.println("the value of index is: "+index);
   //     System.out.println("the size of Additions is: "+Additions.size());
//        if (Additions.get(index).equals("Location")) NextTextViewY+=20;
//        Additions_Images.get(index).setX((float) 20);  //20
        Additions_Images.get(index).setY(NextImageButtonY);
        remove_icons.get(index).setY(NextImageButtonY);
        //        Additions_TextView.get(index).setX((float) 150);  //150
        Additions_TextView.get(index).setY(NextTextViewY);
    }

    private void ExpandDialogAndSetData(ImageButton removeIcon,ImageButton image_button, TextView text_view, String string,String type) {
        int WindowHeight=440+Additions.size()*110;
        System.out.println("im in expand and my height is: "+WindowHeight);
        Additions.add(type);
        Additions_Images.add(image_button);
        Additions_TextView.add(text_view);
        remove_icons.add(removeIcon);
        ViewGroup.LayoutParams params = background_layout. getLayoutParams();
        params.height=WindowHeight;
        background_layout.setLayoutParams(params);
        ViewGroup.LayoutParams params2 = dialog_layout. getLayoutParams();
        params2.height=WindowHeight;
        dialog_layout.setLayoutParams(params2);

        removeIcon.setVisibility(View.VISIBLE);
        removeIcon.setEnabled(true);
        image_button.setVisibility(View.VISIBLE);
        image_button.setEnabled(false);
        text_view.setEnabled(false);
        text_view.setVisibility(View.VISIBLE);
        if(!string.equals("Other"))
            text_view.setText(string);

        if(numberOfAdditions==1){
            System.out.println("im in 1");
//            if(Additions.contains("Description"))
            ArrangingTheWindow(160,140,0);
        }
        if(numberOfAdditions==2){
            System.out.println("im in 2");
            ArrangingTheWindow(120,100,0);
            ArrangingTheWindow(220,200,1);
        }
        if(numberOfAdditions==3){
            System.out.println("im in 3");
            ArrangingTheWindow(140,120,0);
            ArrangingTheWindow(240,220,1);
            ArrangingTheWindow(320,300,2);
        }
    }

    private void ReduceDialogAndReorder(ImageButton removeIcon,ImageButton image_button, TextView text_view,String type) {
        numberOfAdditions-=1;
        int WindowHeight=440+110*(numberOfAdditions-1);
        if(numberOfAdditions==0)
            WindowHeight=440;
        ViewGroup.LayoutParams params = background_layout. getLayoutParams();
        System.out.println("bbbbbbbbbbbbb");
        System.out.println(params.height);
        System.out.println(WindowHeight);
        System.out.println(numberOfAdditions);
//        int WindowHeight=440+Additions.size()*110;
        Additions.remove(type);
        Additions_Images.remove(image_button);
        Additions_TextView.remove(text_view);
        remove_icons.remove(removeIcon);


        removeIcon.setVisibility(View.INVISIBLE);
        removeIcon.setEnabled(false);
        image_button.setVisibility(View.INVISIBLE);
        image_button.setEnabled(false);
        text_view.setVisibility(View.INVISIBLE);
        text_view.setEnabled(false);


        params = background_layout. getLayoutParams();
        params.height=WindowHeight;
        background_layout.setLayoutParams(params);
        ViewGroup.LayoutParams params2 = dialog_layout. getLayoutParams();
        params2.height=WindowHeight;
        dialog_layout.setLayoutParams(params2);



        if(numberOfAdditions==1){
            System.out.println("im in 1");
            ArrangingTheWindow(200,180,0);
        }
        if(numberOfAdditions==2){
            System.out.println("im in 2");
            ArrangingTheWindow(210,190,0);
            ArrangingTheWindow(310,290,1);
        }
//        if(numberOfAdditions==3){
//            System.out.println("im in 3");
//            ArrangingTheWindow(140,120,0);
//            ArrangingTheWindow(240,220,1);
//            ArrangingTheWindow(320,300,2);
//        }
    }


    public void Initialization(boolean edit,reminders_view oldReminder){
        numberOfAdditions=0;
        reminder=new Reminder();
        Additions=new ArrayList<>();
        Additions_Images=new ArrayList<>();
        Additions_TextView=new ArrayList<>();
        remove_icons=new ArrayList<>();
        NotificationDate=Calendar.getInstance();
        add_reminder_dialog = new Dialog(HomePage.getInstance());

        if(edit){
            reminder.setMessage(oldReminder.getTitle());
            reminder.setKey(oldReminder.getKey());
            reminder.setLocationAsString(oldReminder.getLocationAsString());
            reminder.setRemindDate(oldReminder.getDate());
            reminder.setDescription(oldReminder.getDescription());
            reminder.setMyType(oldReminder.getType());
            reminder.setLAT(oldReminder.getLAT());
            reminder.setLNG(oldReminder.getLNG());
            reminder.setAudios(oldReminder.getAudios());
        }
    }


    public static void cancelNotification(String key ,Context context) {
        System.out.println("the pending key is:    "+ key.hashCode());
        AlarmManager alarmManager = (AlarmManager) HomePage.getInstance().getSystemService(HomePage.getInstance().ALARM_SERVICE);
        Intent intent = new Intent(HomePage.getInstance(), NotifierLocationRemind.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(HomePage.getInstance(), key.hashCode(), intent, 0);
        alarmManager.cancel(pendingIntent);
    }


    public void sendToAlarmManager(Reminder reminder,boolean check)  {
        Context context=HomePage.getInstance();
        Intent ServiceIntent=new Intent(context,NotifierLocationRemind.class);
        context.stopService(ServiceIntent);
        ServiceIntent.putExtra("title","Location Reminder");
        ServiceIntent.putExtra("content","I near the desired location");

        if (check==false)
        {
            ServiceIntent.putExtra("locationType","Other");
            ServiceIntent.putExtra("type", "Location");
            ServiceIntent.putExtra("key", reminder.getKey());
            ServiceIntent.putExtra("date",reminder.getRemindDate().getTime());
            ServiceIntent.putExtra("address","");
            ServiceIntent.putExtra("lat",reminder.getLNG());
            ServiceIntent.putExtra("lang",reminder.getLAT());
        }
        else {


            if (Additions.contains("Location") && !Additions.contains("Time")) {
                System.out.println("location and not time");
                ServiceIntent.putExtra("locationType", Category);
                ServiceIntent.putExtra("type", "Location");
                Calendar c = Calendar.getInstance();
//            long currentTime=c.getTimeInMillis();
//            Date d=new Date(currentTime+60*1000);
//            d.setSeconds(0);
                ServiceIntent.putExtra("date", c.getTime().getTime());
            }
            if (Additions.contains("Location") && Additions.contains("Time")) {
                ServiceIntent.putExtra("locationType", Category);
                System.out.println("location and time");
                ServiceIntent.putExtra("type", "Location");
                ServiceIntent.putExtra("date", reminder.getRemindDate().getTime());
            }
            if (!Additions.contains("Location") && Additions.contains("Time")) {
                System.out.println("not location and time");
                ServiceIntent.putExtra("locationType", "");
                ServiceIntent.putExtra("type", "Time");
                ServiceIntent.putExtra("title", "Date Reminder");
                ServiceIntent.putExtra("date", reminder.getRemindDate().getTime());
                ServiceIntent.putExtra("content", "it is the time to notify");
            }
            if (Category != null) {
                if (Category.equals("Other")) {
                    ServiceIntent.putExtra("locationType", "Other");
                    ServiceIntent.putExtra("address", address);
                    ServiceIntent.putExtra("lat", lat);
                    ServiceIntent.putExtra("lang", lang);
                }
            }
        }
        ServiceIntent.putExtra("key",reminder.getKey());
        ServiceIntent.putExtra("Pending_key",reminder.getKey().hashCode());
        ServiceIntent.putExtra("userName",UserName);
        context.startService(ServiceIntent);
    }

    public void SetFindViewById(){
        add_reminder_dialog.setContentView(R.layout.anna_reminder);
        DescriptionTextView=add_reminder_dialog.findViewById(R.id.DescriptionTextView);
//        searchIcon=add_reminder_dialog.findViewById(R.id.searchIcon);
        TimeTextView=add_reminder_dialog.findViewById(R.id.TimeTextView);
        LocationTextView=add_reminder_dialog.findViewById(R.id.LocationTextView);
//        searchLocationbar=add_reminder_dialog.findViewById(R.id.searchLocation);
        background_layout=add_reminder_dialog.findViewById(R.id.background_layout);
        dialog_layout=add_reminder_dialog.findViewById(R.id.dialog_layout);
        selectDate_btn = add_reminder_dialog.findViewById(R.id.selectDate);
        cancel_btn = add_reminder_dialog.findViewById(R.id.cancel_Btn);
        add_btn = add_reminder_dialog.findViewById(R.id.addButton);
        addDescription_btn = add_reminder_dialog.findViewById(R.id.addDescription);
        location_btn = add_reminder_dialog.findViewById(R.id.location);
        title = add_reminder_dialog.findViewById(R.id.Title);
        selectDateImage=add_reminder_dialog.findViewById(R.id.selectDateImage);
        addDescriptionImage=add_reminder_dialog.findViewById(R.id.addDescriptionImage2);
        locationImage=add_reminder_dialog.findViewById(R.id.locationImage);
        removeDate=add_reminder_dialog.findViewById(R.id.RemoveDate);
        removeLocation=add_reminder_dialog.findViewById(R.id.RemoveDescription);
        removeDescription=add_reminder_dialog.findViewById(R.id.RemoveLocation);
    }

    public void InitializeTheDialogIfEdit(reminders_view oldReminder) {
        all_audios_list = oldReminder.getAudios();
        title.setText(oldReminder.getTitle());
        if (oldReminder.getDescription() != null || oldReminder.getAudios() != null) {
            String content = "";
            if (oldReminder.getDescription() != null) content += " +Description";
            if (oldReminder.getAudios() != null) content += " +Audio";

            ExpandDialogAndSetData2(removeDescription, addDescriptionImage, DescriptionTextView, content, "Description");
        }
        if (oldReminder.getType().equals("Date")){
            String hour, minutes;
            Date date = oldReminder.getDate();
            if (Integer.toString(date.getHours()).length() == 1)
                hour = "0" + Integer.toString(date.getHours());
            else
                hour = Integer.toString(date.getHours());

            if (Integer.toString(date.getMinutes()).length() == 1)
                minutes = "0" + Integer.toString(date.getMinutes());
            else
                minutes = Integer.toString(date.getMinutes());
            String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
            formattedDate = formattedDate + " - " + hour + ":" + minutes;
            ExpandDialogAndSetData2(removeDate, selectDateImage, TimeTextView, formattedDate, "Time");
        }

        if (oldReminder.getType().equals("Location")) {
            if (oldReminder.getLocationAsString().equals("Other")) {
                locationImage.setImageDrawable(ContextCompat.getDrawable(HomePage.getInstance(), R.drawable.ic_search));
                System.out.println("oldReminder.getLocation()  " + oldReminder.getLocation());
                System.out.println("oldReminder.get other()  " + oldReminder.getTitle());
                ExpandDialogAndSetData2(removeLocation, locationImage, LocationTextView
                        , oldReminder.getLocation(), "Location");

                LocationTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.TYPES);
                        Intent intentL = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).setCountry("IL").build(HomePage.getInstance());
                        HomePage.getInstance().startActivityForResult(intentL, 100);
                    }
                });
                LocationTextView.setEnabled(true);
                LocationTextView.setVisibility(View.VISIBLE);
                //LocationTextView.setAutofillHints("tab to search");
                //LocationTextView.setText(oldReminder.getlocation());
                //LocationTextView.setAutofillHints("tab to search");
            } else {
                ExpandDialogAndSetData2(removeLocation, locationImage, LocationTextView
                        , oldReminder.getLocationAsString(), "Location");
            }

            //
            //            ExpandDialogAndSetData2(removeLocation,locationImage, LocationTextView
            //                    , oldReminder.getLocationAsString(), "Location");
        }
    }

    public void openDialog(boolean edit,reminders_view oldReminder,int position){
        all_audios_list=new ArrayList<>();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(UserName).child("reminder_list");
        keyRef =ref.push();
        Initialization(edit,oldReminder);
        SetFindViewById();
        if(edit)
            InitializeTheDialogIfEdit(oldReminder);

        if(edit && oldReminder.getDate()!=null)
            oldDate=oldReminder.getDate();

        removeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminder.setRemindDate(null);
                ReduceDialogAndReorder(removeDate,selectDateImage,TimeTextView,"Time");
            }
        });

        removeDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminder.setDescription(null);
                ReduceDialogAndReorder(removeDescription,addDescriptionImage,DescriptionTextView,
                        "Description");
                all_audios_list=new ArrayList<>();
                System.out.println("the size of the list after remove description: "
                        +all_audios_list.size());
            }
        });

        removeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminder.setLocationAsString(null);
                ReduceDialogAndReorder(removeLocation,locationImage,LocationTextView,"Location");
            }
        });


        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_reminder_dialog.cancel();
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean delete=true;
                System.out.println("cattt2222  "+Category +" lattt "+ lat );
                if(Category.equals("Other") && lat==null){
                    Toast.makeText(HomePage.getInstance(),"No Location Specified! try again",Toast.LENGTH_LONG).show();
                    cancel_btn.performClick();
                    // cancelNotification(reminder.getKey(),HomePage.getInstance());
                    delete=false;
                }
                reminder.setAudios(all_audios_list);
                if(all_audios_list!=null)
                    uploadAudios();
                if(title.getText().toString().isEmpty())
                    reminder.setMessage("Reminder");
                else
                    reminder.setMessage(title.getText().toString());
                reminder.setState(true);
                reminder.setLocation(address);
                reminder.setMyType(checkType());
                if(!edit&& delete) {
                    reminder.setKey(keyRef.getKey());
                    keyRef.setValue(reminder);
                }
                else if(delete){
                    cancelNotification(reminder.getKey(),HomePage.getInstance());
                    keyRef=ref.child(reminder.getKey());
                    keyRef.setValue(reminder);
                }
                if(reminder.getRemindDate()==null && delete)
                {
                    date = new Date();
                    reminder.setRemindDate((date));
//                    keyRef=ref.child(reminder.getKey());
//                    keyRef.setValue(reminder);
                }

                if(reminder.getMyType().equals("Location") && reminder.getLocationAsString().equals("Other")&& delete)
                {
                    System.out.println("GOT IN SETTIG LANG AmD LAT");
                    reminder.setLocation(address);
                    reminder.setLAT(lat);
                    reminder.setLNG(lang);
                    keyRef=ref.child(reminder.getKey());
                    keyRef.setValue(reminder);

                }
                System.out.println("the  of the reminder is: "+reminder.getMyType());
                if(!reminder.getMyType().equals("Todo List") && delete) {
                    System.out.println("im in reminder not a todo lost reminder ");
                    sendToAlarmManager(reminder,true);
                }
                add_reminder_dialog.cancel();
                ArrayAdapter<CharSequence> KindsAdapter = ArrayAdapter.createFromResource(HomePage.getInstance(), R.array.kinds, android.R.layout.simple_spinner_item);
                KindsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                int spinnerPosition = KindsAdapter.getPosition("all");
                HomePage.getInstance().getRemindersKindSpinner().setSelection(spinnerPosition);
                HomePage.getInstance().get_all_reminders_by_kind("all");
                HomePage.getInstance().getmRecyclerView().setHasFixedSize(true);
            }
        });

        location_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                category_menu_dialog = new Dialog(add_reminder_dialog.getContext());
                category_menu_dialog.setContentView(R.layout.category_menu);
                category_menu_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                category_menu_dialog.show();
                btn_other = category_menu_dialog.findViewById(R.id.btn_other);

                btn_food = category_menu_dialog.findViewById(R.id.btn_food);
                btn_food_res = category_menu_dialog.findViewById(R.id.btn_food_res);
                btn_food_cafe = category_menu_dialog.findViewById(R.id.btn_food_cafe);
                btn_food_bakery = category_menu_dialog.findViewById(R.id.btn_food_bakery);

                btn_money = category_menu_dialog.findViewById(R.id.btn_money);
                btn_money_bank = category_menu_dialog.findViewById(R.id.btn_money_bank);
                btn_money_atm = category_menu_dialog.findViewById(R.id.btn_money_atm);

                btn_office = category_menu_dialog.findViewById(R.id.btn_office);
                btn_office_laywer = category_menu_dialog.findViewById(R.id.btn_office_lawyer);
                btn_office_acoounting = category_menu_dialog.findViewById(R.id.btn_office_accounting);
                btn_office_post_office = category_menu_dialog.findViewById(R.id.btn_office_postOffice);
                btn_office_police = category_menu_dialog.findViewById(R.id.btn_office_police);

                btn_car = category_menu_dialog.findViewById(R.id.btn_car);
                btn_car_wash = category_menu_dialog.findViewById(R.id.btn_car_wash);
                btn_car_repair = category_menu_dialog.findViewById(R.id.btn_car_repair);
                btn_car_gas = category_menu_dialog.findViewById(R.id.btn_car_gas);
                btn_car_parking = category_menu_dialog.findViewById(R.id.btn_car_parking);

                btn_shop = category_menu_dialog.findViewById(R.id.btn_shop);
                btn_shop_supermarket = category_menu_dialog.findViewById(R.id.btn_shop_supermarket);
                btn_shop_mall = category_menu_dialog.findViewById(R.id.btn_mall);

                btn_book = category_menu_dialog.findViewById(R.id.btn_book);
                btn_book_library = category_menu_dialog.findViewById(R.id.btn_book_library);
                btn_book_uni = category_menu_dialog.findViewById(R.id.btn_book_Uni);
                btn_book_book_store = category_menu_dialog.findViewById(R.id.btn_book_book_Store);

                btn_medical = category_menu_dialog.findViewById(R.id.btn_medical);
                btn_medical_pharmacy = category_menu_dialog.findViewById(R.id.btn_medical_pharmacy);
                btn_medical_hospital = category_menu_dialog.findViewById(R.id.btn_medical_hospital);

                Main_btns[0]=btn_food;
                Main_btns[1]=btn_money;
                Main_btns[2]=btn_office;
                Main_btns[3]=btn_car;
                Main_btns[4]=btn_shop;
                Main_btns[5]=btn_book;
                Main_btns[6]=btn_medical;

                isRotate.add(false);
                isRotate.add(false);
                isRotate.add(false);
                isRotate.add(false);
                isRotate.add(false);
                isRotate.add(false);
                isRotate.add(false);

                ArrayList<ExtendedFloatingActionButton> list0 = new ArrayList<ExtendedFloatingActionButton>(Arrays.asList(btn_food_res,btn_food_cafe,btn_food_bakery));
                ArrayList<ExtendedFloatingActionButton> list1 = new ArrayList<ExtendedFloatingActionButton>(Arrays.asList(btn_money_bank,btn_money_atm));
                ArrayList<ExtendedFloatingActionButton> list2 = new ArrayList<ExtendedFloatingActionButton>(Arrays.asList(btn_office_laywer,btn_office_acoounting,btn_office_police,btn_office_post_office));
                ArrayList<ExtendedFloatingActionButton> list3 = new ArrayList<ExtendedFloatingActionButton>(Arrays.asList(btn_car_wash,btn_car_repair,btn_car_gas,btn_car_parking));
                ArrayList<ExtendedFloatingActionButton> list4 = new ArrayList<ExtendedFloatingActionButton>(Arrays.asList(btn_shop_supermarket,btn_shop_mall));
                ArrayList<ExtendedFloatingActionButton> list5 = new ArrayList<ExtendedFloatingActionButton>(Arrays.asList(btn_book_library,btn_book_uni,btn_book_book_store));
                ArrayList<ExtendedFloatingActionButton> list6 = new ArrayList<ExtendedFloatingActionButton>(Arrays.asList( btn_medical_hospital,btn_medical_pharmacy));
                subList.add(list0);
                subList.add(list1);
                subList.add(list2);
                subList.add(list3);
                subList.add(list4);
                subList.add(list5);
                subList.add(list6);


                btn_other.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("im in btn_other");
                        Category="Other";
                        afterchoosing();
                    }
                });

                btn_food.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        closeAll(0);
                        ArrayList<ExtendedFloatingActionButton> buttons = new ArrayList<>();
                        buttons.add(btn_food_cafe);
                        buttons.add(btn_food_bakery);
                        buttons.add(btn_food_res);
                        animation(v, btn_food, buttons,0);

                        btn_food_res.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Category = "Restaurant";
                                afterchoosing();
                            }
                        });
                        btn_food_bakery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Category = "Bakery";
                                afterchoosing();
                            }
                        });
                        btn_food_cafe.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Category = "Cafe";
                                afterchoosing();
                            }
                        });
                    }
                });

                btn_money.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeAll(1);

                        ArrayList<ExtendedFloatingActionButton> buttons = new ArrayList<>();
                        buttons.add(btn_money_bank);
                        buttons.add(btn_money_atm);
                        animation(v, btn_money, buttons,1);

                        btn_money_bank.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Category = "Bank";
                                afterchoosing();
                            }
                        });
                        btn_money_atm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Category = "ATM";
                                afterchoosing();
                            }
                        });

                    }
                });

                btn_office.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeAll(2);
                        ArrayList<ExtendedFloatingActionButton> buttons = new ArrayList<>();
                        buttons.add(btn_office_acoounting);
                        buttons.add(btn_office_police);
                        buttons.add(btn_office_post_office);
                        buttons.add(btn_office_laywer);
                        animation(v, btn_office, buttons,2);

                        btn_office_acoounting.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Category = "accounting";
                                afterchoosing();
                            }
                        });
                        btn_office_police.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Category = "Police";
                                afterchoosing();
                            }
                        });
                        btn_office_post_office.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Category = "post_office";
                                afterchoosing();
                            }
                        });
                        btn_office_laywer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Category = "lawyer";
                                afterchoosing();
                            }
                        });
                    }
                });

                btn_car.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeAll(3);

                        ArrayList<ExtendedFloatingActionButton> buttons = new ArrayList<>();
                        buttons.add(btn_car_gas);
                        buttons.add(btn_car_parking);
                        buttons.add(btn_car_wash);
                        buttons.add(btn_car_repair);
                        animation(v, btn_car, buttons,3);

                        btn_car_gas.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Category = "gas_station";
                                afterchoosing();
                            }
                        });
                        btn_car_parking.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Category = "parking";
                                afterchoosing();
                            }
                        });
                        btn_car_wash.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Category = "car_wash";
                                afterchoosing();
                            }
                        });
                        btn_car_repair.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Category = "car_repair";
                                afterchoosing();
                            }
                        });

                    }
                });

                btn_shop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeAll(4);
                        ArrayList<ExtendedFloatingActionButton> buttons = new ArrayList<>();
                        buttons.add(btn_shop_supermarket);
                        buttons.add(btn_shop_mall);
                        animation(v, btn_shop, buttons,4);

                        btn_shop_supermarket.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Category = "supermarket";
                                afterchoosing();
                            }
                        });
                        btn_shop_mall.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Category = "shopping_mall";
                                afterchoosing();
                            }
                        });

                    }
                });

                btn_book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeAll(5);

                        ArrayList<ExtendedFloatingActionButton> buttons = new ArrayList<>();
                        buttons.add(btn_book_book_store);
                        buttons.add(btn_book_library);
                        buttons.add(btn_book_uni);
                        animation(v, btn_book, buttons,5);

                        btn_book_book_store.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Category = "book_store";
                                afterchoosing();
                            }
                        });
                        btn_book_library.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Category = "library";
                                afterchoosing();
                            }
                        });
                        btn_book_uni.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Category = "university";
                                afterchoosing();
                            }
                        });

                    }
                });
                btn_medical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeAll(6);

                        ArrayList<ExtendedFloatingActionButton> buttons = new ArrayList<>();
                        buttons.add(btn_medical_pharmacy);
                        buttons.add(btn_medical_hospital);
                        animation(v, btn_medical, buttons,6);

                        btn_medical_pharmacy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Category = "pharmacy";
                                afterchoosing();
                            }
                        });
                        btn_medical_hospital.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Category = "hospital";
                                afterchoosing();
                            }
                        });

                    }
                });

            }
        });

//                searchLocation.setOnClickListener(new View.OnClickListener() {
//                    @RequiresApi(api = Build.VERSION_CODES.O)
//                    @Override
//                    public void onClick(View v) {
//                        //save the chosen category in the reminder and start search the location
//                        add_location_dialog.cancel();
//                        reminder.setLocationAsString(Category);
//                        if(Category.equals("Other")){
//                            System.out.println("its other");
//                            locationImage.setImageDrawable(ContextCompat.getDrawable(HomePage.getInstance(), R.drawable.ic_search));
//                            if(!Additions.contains("Location")) {
//                                numberOfAdditions+=1;
//                                ExpandDialogAndSetData(removeLocation,locationImage,LocationTextView,Category,"Location");
//                            }
//                            LocationTextView.setText("tab to search");
//                            LocationTextView.setEnabled(true);
//                            LocationTextView.setVisibility(View.VISIBLE);
//                            LocationTextView.setAutofillHints("tab to search");
//                            LocationTextView.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME,Place.Field.TYPES);
//                                    Intent intentL= new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).setCountry("IL").build(HomePage.getInstance());
//                                    sharedPreferences=HomePage.getInstance().getSharedPreferences("U",MODE_PRIVATE);
//                                    editor=sharedPreferences.edit();
//                                    editor.putString("key of other notification",keyRef.getKey());
//                                    editor.commit();
//                                    HomePage.getInstance().startActivityForResult(intentL,100);
//                                }
//                            });
//                        }
//                        else{
//                            System.out.println("its not other");
//                            locationImage.setImageDrawable(ContextCompat.getDrawable(HomePage.getInstance(), R.drawable.ic_location));
//                            if(Additions.contains("Location")){
//                                LocationTextView.setText(Category);
//                            }
//                            else{
//                                numberOfAdditions+=1;
//                                ExpandDialogAndSetData(removeLocation,locationImage,LocationTextView,Category,"Location");
//                            }
//                        }
//                    }
//                });


        selectDate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalender = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(add_reminder_dialog.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("WrongConstant")
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                        System.out.println("im in onDateSet");
                        final Calendar newDate = Calendar.getInstance();
                        Calendar newTime = Calendar.getInstance();
                        NotificationDate = Calendar.getInstance();
                        NotificationDate.set(Calendar.MONTH, month);
                        NotificationDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        NotificationDate.set(Calendar.YEAR, year);
                        TimePickerDialog time = new TimePickerDialog(add_reminder_dialog.getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                NotificationDate.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                NotificationDate.set(Calendar.MINUTE,minute);
                                NotificationDate.set(Calendar.SECOND,0);
                                newDate.set(year, month, dayOfMonth, hourOfDay, minute, 0);
                                Calendar tem = Calendar.getInstance();
                                Log.w("TIME", System.currentTimeMillis() + "");
                                if (newDate.getTimeInMillis() - tem.getTimeInMillis() > 0) {
                                    String hour,minutes;
                                    if(Integer.toString(NotificationDate.getTime().getHours()).length()==1)
                                        hour="0"+Integer.toString(NotificationDate.getTime().getHours());
                                    else
                                        hour=Integer.toString(NotificationDate.getTime().getHours());

                                    if(Integer.toString(NotificationDate.getTime().getMinutes()).length()==1)
                                        minutes="0"+Integer.toString(NotificationDate.getTime().getMinutes());
                                    else
                                        minutes=Integer.toString(NotificationDate.getTime().getMinutes());

                                    String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(NotificationDate.getTime());
                                    formattedDate=formattedDate+" - "+hour+":"+minutes;
                                    reminder.setRemindDate(NotificationDate.getTime());
                                    if(Additions.contains("Time")){
                                        TimeTextView.setText(formattedDate);
                                    }
                                    else{
                                        numberOfAdditions+=1;
                                        ExpandDialogAndSetData(removeDate,selectDateImage,TimeTextView,formattedDate,"Time");
                                    }

                                } else
                                    Toast.makeText(add_reminder_dialog.getContext(), "Invalid time", Toast.LENGTH_SHORT).show();
                            }
                        }, newTime.get(Calendar.HOUR_OF_DAY), newTime.get(Calendar.MINUTE), true);
                        time.getWindow().setColorMode(Window.DECOR_CAPTION_SHADE_DARK);
                        time.show();
                    }
                }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();
            }
        });

        addDescription_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //Initialization of add description dialog

                if(!checkPermissionFromDevice())
                    requestPermission();

                add_description_dialog = new Dialog(add_reminder_dialog.getContext());
                add_description_dialog.setContentView(R.layout.add_description);
                Chronometer simpleChronometer = add_description_dialog.findViewById(R.id.simpleChronometer2); // initiate a chronometer

                all_audios=add_description_dialog.findViewById(R.id.all_audios2);
                delete_audio=add_description_dialog.findViewById(R.id.delete_audio);
                save_description = add_description_dialog.findViewById(R.id.save_description);
                cancel_desc_dialog = add_description_dialog.findViewById(R.id.cancel_desc_dialog);
                mRecordBtn = add_description_dialog.findViewById(R.id.recordBtn2);
                AddDescriptionEditText = add_description_dialog.findViewById(R.id.AddDescriptionEditText);
                AddDescriptionEditText.setFocusedByDefault(true);
                if (reminder.getDescription() != null) {
                    AddDescriptionEditText.setText(reminder.getDescription());
                    save_description.setEnabled(true);
                }

                if(reminder.getAudios()!=null){
                    save_description.setEnabled(true);
                    for(int i=0;i<all_audios_list.size();i++){
                        LinearLayout audios=add_description_dialog.findViewById(R.id.kinear_all_audios);
                        LayoutInflater inflater=LayoutInflater.from(add_description_dialog.getContext());
                        View view= inflater.inflate(R.layout.one_audio,audios,false);
                        ImageView playAudio=view.findViewById(R.id.playAudio);
                        ImageView deleteAudio=view.findViewById(R.id.delete_audio);
                        SeekBar seekBar=view.findViewById(R.id.seekBar);
                        view.setTag(all_audios_list.get(i));
                        audios.addView(view);
                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                if(fromUser) mediaPlayer.seekTo(progress);
                            }
                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {}
                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {/*mediaPlayer.seekTo(seekBar.getProgress());*/}
                        });
                        deleteAudio.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v){
                                LinearLayout audios=add_description_dialog.findViewById(R.id.kinear_all_audios);
                                audios.removeView((LinearLayout)v.getParent());
                                all_audios_list.remove((String)((View)v.getParent()).getTag());
                            }
                        });
                        playAudio.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v){
                                seekBar2=seekBar;
                                if (play)
                                    play = false;
                                else
                                    play = true;
                                if (play) {
                                    mediaPlayer = new MediaPlayer();
                                    try {
                                        mediaPlayer.setDataSource((String)((View)v.getParent()).getTag());
//                        mediaPlayer.prepare();
                                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer mp) {
                                                mp.start();
                                            }
                                        });
                                        mediaPlayer.prepare();

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    seekBar.setMax(mediaPlayer.getDuration());
//                    mediaPlayer.start();
                                    UpdateSeekBar updateSeekBar = new UpdateSeekBar();
                                    handler.post(updateSeekBar);
                                } else {
                                    System.out.println("play is false");
                                    if (mediaPlayer != null) {
//                        mediaPlayer.stop();
//                        mediaPlayer.release();
                                        mediaPlayer.pause();
                                        currentPosition = mediaPlayer.getCurrentPosition();
//                        setupMediaRecorder();
                                    }
                                }
                                mediaPlayer.seekTo(seekBar.getProgress());
                            }
                        });
                    }
                }



                AddDescriptionEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(!AddDescriptionEditText.getText().toString().isEmpty()
                        || all_audios_list.size()!=0)
                            save_description.setEnabled(true);
                        else
                            save_description.setEnabled(false);
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void afterTextChanged(Editable s) {}
                });

                cancel_desc_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_description_dialog.cancel();
                    }
                });

                save_description.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.Q)
                    @Override
                    public void onClick(View v) {
                        String content="";
                        if(all_audios_list.size()!=0)
                            content="Audio ";
                        if(!AddDescriptionEditText.getText().toString().equals(""))
                            content+="Description";

                        if(Additions.contains("Description")){
                            DescriptionTextView.setText(content);
                        }
                        else{
                            numberOfAdditions+=1;
                            ExpandDialogAndSetData(removeDescription,addDescriptionImage,DescriptionTextView,content,"Description");
                        }
                        reminder.setDescription(AddDescriptionEditText.getText().toString());
                        reminder.setAudios(all_audios_list);
                        add_description_dialog.cancel();
                    }
                });

                mRecordBtn.setOnTouchListener(new View.OnTouchListener() {
                    @RequiresApi(api = Build.VERSION_CODES.P)
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                            if (checkPermissionFromDevice()) {
                                PathName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                        UUID.randomUUID().toString() + "audio_record.3gp";
                                setupMediaRecorder();
                                try {
                                    mediaRecorder.prepare();
                                    mediaRecorder.start();
                                    simpleChronometer.start();
                                }catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(HomePage.getInstance(), "Recording ...", Toast.LENGTH_SHORT).show();
                            } else
                                requestPermission();
                        }else if(event.getAction() == MotionEvent.ACTION_UP){
                            System.out.println("the max amplitude "+mediaRecorder.getMaxAmplitude());
                            simpleChronometer.stop();
                            simpleChronometer.setBase(SystemClock.elapsedRealtime());
                            mediaRecorder.stop();
                            mediaRecorder.release();
                            mediaRecorder=null;
                            addToScrollView();
                        }
                        return false;
                    }
                });
                add_description_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                add_description_dialog.show();
            }
        });

        add_reminder_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        add_reminder_dialog.show();
    }



    private void setupMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        System.out.println("the path name of the audio is: "+PathName);
        mediaRecorder.setOutputFile(PathName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

    }



    private void uploadAudios(){
        for(int i=0;i<all_audios_list.size();i++) {
            final ProgressDialog pd = new ProgressDialog(HomePage.getInstance());
            pd.setMessage("Uploading Audio ...");
            pd.show();
            StorageReference filePath = mStorage.child("Users").child(UserName).child("Audio")
                    .child(keyRef.getKey())
                    .child(System.currentTimeMillis() + "_" + "new_audio.3gp");
            Uri uri = Uri.fromFile(new File(all_audios_list.get(i)));
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                }
            });
        }
    }

    private void addToScrollView(){
        all_audios_list.add(PathName);
        if(all_audios_list.size()==1)
            save_description.setEnabled(true);
        LinearLayout audios=add_description_dialog.findViewById(R.id.kinear_all_audios);
        LayoutInflater inflater=LayoutInflater.from(add_description_dialog.getContext());
        View view= inflater.inflate(R.layout.one_audio,audios,false);
        ImageView playAudio=view.findViewById(R.id.playAudio);
        ImageView deleteAudio=view.findViewById(R.id.delete_audio);
        SeekBar seekBar=view.findViewById(R.id.seekBar);
        view.setTag(PathName);
        audios.addView(view);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar2, int progress, boolean fromUser) {

                if(fromUser){
                    mediaPlayer.seekTo(progress);
                    System.out.println("im in seekbar...");
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {/*mediaPlayer.seekTo(seekBar.getProgress());*/}
        });
        deleteAudio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                LinearLayout audios=add_description_dialog.findViewById(R.id.kinear_all_audios);
                audios.removeView((LinearLayout)v.getParent());
                all_audios_list.remove((String)((View)v.getParent()).getTag());
            }
        });
        playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                seekBar2=seekBar;
                if (play)
                    play = false;
                else
                    play = true;
                if (play) {
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource((String)((View)v.getParent()).getTag());
//                        mediaPlayer.prepare();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                            }
                        });
                        mediaPlayer.prepare();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    seekBar.setMax(mediaPlayer.getDuration());
//                    mediaPlayer.start();
                    UpdateSeekBar updateSeekBar = new UpdateSeekBar();
                    handler.post(updateSeekBar);
                } else {
                    System.out.println("play is false");
                    if(mediaPlayer != null) {
//                        mediaPlayer.stop();
//                        mediaPlayer.release();
                        mediaPlayer.pause();
                        currentPosition = mediaPlayer.getCurrentPosition();
//                        setupMediaRecorder();
                    }
                }
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    public class UpdateSeekBar implements Runnable{
        @Override
        public void run() {
            seekBar2.setProgress(mediaPlayer.getCurrentPosition());
            handler.postDelayed(this,100);
            if(mediaPlayer.getCurrentPosition()==mediaPlayer.getDuration()) {
                System.out.println("im in run...");
                seekBar2.setProgress(0);
                play=true;

            }
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(HomePage.getInstance(),new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO

        },REQUEST_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }
    private boolean checkPermissionFromDevice() {
        int write_external_storage_result= ContextCompat.checkSelfPermission
                (HomePage.getInstance(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result=ContextCompat.checkSelfPermission(HomePage.getInstance(),
                Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result== PackageManager.PERMISSION_GRANTED
                && record_audio_result==PackageManager.PERMISSION_GRANTED;
    }
    private void afterchoosing(){
        //save the chosen category in the reminder and start search the location
        category_menu_dialog.cancel();
        reminder.setLocationAsString(Category);
        if(Category.equals("Other")){
            System.out.println("its other");
            locationImage.setImageDrawable(ContextCompat.getDrawable(HomePage.getInstance(), R.drawable.ic_search));
            if(!Additions.contains("Location")) {
                numberOfAdditions+=1;
                ExpandDialogAndSetData(removeLocation,locationImage,LocationTextView,Category,"Location");
            }
            LocationTextView.setText("tab to search");
            LocationTextView.setEnabled(true);
            LocationTextView.setVisibility(View.VISIBLE);
            //LocationTextView.setAutofillHints("tab to search");
            LocationTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME,Place.Field.TYPES);
                    Intent intentL= new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).setCountry("IL").build(HomePage.getInstance());
                    HomePage.getInstance().startActivityForResult(intentL,100);
                }
            });
        }
        else{
            System.out.println("its not other");
            locationImage.setImageDrawable(ContextCompat.getDrawable(HomePage.getInstance(), R.drawable.ic_location));
            if(Additions.contains("Location")){
                LocationTextView.setText(Category);
            }
            else{
                numberOfAdditions+=1;
                ExpandDialogAndSetData(removeLocation,locationImage,LocationTextView,Category,"Location");
            }
        }

        closeAll(8);
    }
    private void closeAll (int indx) {
        boolean rotate;
        for (int i=0 ;i<7;i++ ){
            rotate=isRotate.get(i);
            System.out.println("ROTATE in closeall "+isRotate.get(i)+" for "+i);
            if (rotate & i!=indx) {
                FloatingActionButton main_btn = Main_btns[i];
                viewAnimation.rotateFab(main_btn,! isRotate.get(i));
                DrawableCompat.setTintList(DrawableCompat.wrap(main_btn.getDrawable()), ColorStateList.valueOf(Color.BLACK)); // <- icon
                DrawableCompat.setTintList(DrawableCompat.wrap(main_btn.getBackground()), ColorStateList.valueOf(Color.parseColor("#FDC53A"))); // <- background

                List<ExtendedFloatingActionButton> buttons = subList.get(i);
                for (ExtendedFloatingActionButton btn : buttons) {viewAnimation.showOut(btn);
                }
                isRotate.set(i,false);

            }}

    }
    private void animation(View v,FloatingActionButton main_btn, List<ExtendedFloatingActionButton> buttons,int i ){
        isRotate.set(i,viewAnimation.rotateFab(main_btn,! isRotate.get(i)));
        System.out.println("ROTATE+"+isRotate.get(i));
        if(isRotate.get(i)){
            for (ExtendedFloatingActionButton btn : buttons){viewAnimation.showIn(btn);}

            DrawableCompat.setTintList(DrawableCompat.wrap(main_btn.getDrawable()), ColorStateList.valueOf(Color.parseColor("#FDC53A"))); // <- icon
            DrawableCompat.setTintList(DrawableCompat.wrap(main_btn.getBackground()), ColorStateList.valueOf(Color.BLACK)); // <- background

        }else{
            for (ExtendedFloatingActionButton btn : buttons){viewAnimation.showOut(btn);}

            DrawableCompat.setTintList(DrawableCompat.wrap(main_btn.getDrawable()), ColorStateList.valueOf(Color.BLACK)); // <- icon
            DrawableCompat.setTintList(DrawableCompat.wrap(main_btn.getBackground()), ColorStateList.valueOf(Color.parseColor("#FDC53A"))); // <- background

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("INNNNNNNNNNNNNn started 000");
        if ((requestCode==100)&&(resultCode==RESULT_OK))
        {

//            AutocompleteFilter filter =
//                    new AutocompleteFilter.Builder().setCountry("IL").build();

            System.out.println("INNNNNNNNNNNNNn started");
            Place place = Autocomplete.getPlaceFromIntent(data);

            searchLocation.setVisibility(View.INVISIBLE);
            LocationTextView.setVisibility(View.VISIBLE);
            LocationTextView.setText(place.getAddress());
            wantedLocation=place;
            System.out.println("PLACE !!!!1"+place.getName());
        }
        else if (resultCode== AutocompleteActivity.RESULT_ERROR) {
            Status status =Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();;
        }
    }
    private String checkType() {
        boolean Location=false,Date=false;
        if (Additions.contains("Location")) Location=true;
        else if(Additions.contains("Time")) Date=true;
        //if(Location && Date) return "Location_Date";
        if(Location) return "Location";
        if(Date) return "Date";
        return "Todo List";
    }


}