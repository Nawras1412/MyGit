package com.example.smartremindersapp2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.List;
import java.util.Objects;

public class addReminder extends AppCompatActivity {
    private Dialog add_reminder_dialog, add_description_dialog,add_location_dialog;
    private Button add_btn;
    public TextView LocationTextView;
    private ImageButton cancel_btn, selectDate_btn, addDescription_btn, location_btn;
    private ImageButton selectDateImage, addDescriptionImage, locationImage;
    private ImageButton removeDate,removeLocation,removeDescription;
    private TextView title,DescriptionTextView,TimeTextView,searchLocationbar;
    private EditText AddDescriptionEditText;
    private Button save_description,cancel_desc_dialog,searchLocation;
    private Reminder reminder;
    private ConstraintLayout dialog_layout;
    private RelativeLayout background_layout;
    private Calendar NotificationDate;
    private RadioGroup LocationMenu;
    private RadioButton radioButtonOption;
    private String Category;
    private String UserName;
    private int numberOfAdditions;
    private List<String> Additions;
    private List<ImageButton> Additions_Images;
    private List<TextView> Additions_TextView;
    private List<ImageButton> remove_icons;
    private Date oldDate=new Date();
    private Date date=new Date();
    private Place wantedLocation;
    private static addReminder instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public addReminder(String userName) { UserName=userName; }

    public addReminder() {}


    private void ExpandDialogAndSetData2(ImageButton removeIcon,ImageButton image_button, TextView text_view, String string,String type) {
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
        System.out.println("the size of Additions is: "+Additions.size());
//        if (Additions.get(index).equals("Location")) NextTextViewY+=20;
//        Additions_Images.get(index).setX((float) 20);  //20
        Additions_Images.get(index).setY(NextImageButtonY);
        remove_icons.get(index).setY(NextImageButtonY);
        //        Additions_TextView.get(index).setX((float) 150);  //150
        Additions_TextView.get(index).setY(NextTextViewY);
    }


    private void ExpandDialogAndSetData(ImageButton removeIcon,ImageButton image_button, TextView text_view, String string,String type) {
//        int linesNum=string.length()/30;
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


//    private void ReduceDialogAndReorder(ImageButton removeIcon,ImageButton image_button, TextView text_view, String string,String type) {
    private void ReduceDialogAndReorder(ImageButton removeIcon,ImageButton image_button, TextView text_view,String type) {
        numberOfAdditions-=1;
        ViewGroup.LayoutParams params = background_layout. getLayoutParams();
        int WindowHeight=440+110*(numberOfAdditions-1);
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
        }
    }


    void cancelNotification(String key ,Context context) {
        System.out.println("the pending key is:    "+ key.hashCode());
        AlarmManager alarmManager = (AlarmManager) HomePage.getInstance().getSystemService(HomePage.getInstance().ALARM_SERVICE);
        Intent intent = new Intent(HomePage.getInstance(), NotifierLocationRemind.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(HomePage.getInstance(), key.hashCode(), intent, 0);
        alarmManager.cancel(pendingIntent);
    }


    private void sendToAlarmManager(Reminder reminder) {
        Context context=HomePage.getInstance();
        Intent ServiceIntent=new Intent(context,NotifierLocationRemind.class);
        context.stopService(ServiceIntent);
        ServiceIntent.putExtra("date",reminder.getRemindDate().getTime());
        if(Additions.contains("Location"))
        if(Additions.contains("Location"))
        {
            ServiceIntent.putExtra("type","LocationRemind");
            ServiceIntent.putExtra("locationType",reminder.getLocationAsString());
        }
        else {
            ServiceIntent.putExtra("type","DateRemind");
        }
        ServiceIntent.putExtra("key",reminder.getKey());
        ServiceIntent.putExtra("title",reminder.getMessage());
        ServiceIntent.putExtra("title",reminder.getMessage());
        ServiceIntent.putExtra("Pending_key",reminder.getKey().hashCode());
        ServiceIntent.putExtra("userName",UserName);
        context.startService(ServiceIntent);
    }



    public void SetFindViewById(){
        add_reminder_dialog.setContentView(R.layout.anna_reminder);
        DescriptionTextView=add_reminder_dialog.findViewById(R.id.DescriptionTextView);
        TimeTextView=add_reminder_dialog.findViewById(R.id.TimeTextView);
        LocationTextView=add_reminder_dialog.findViewById(R.id.LocationTextView);
        searchLocationbar=add_reminder_dialog.findViewById(R.id.searchLocation);
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

    public void InitializeTheDialogIfEdit(reminders_view oldReminder){
        title.setText(oldReminder.getTitle());
        if (oldReminder.getDescription() != null)
            ExpandDialogAndSetData2(removeDescription,addDescriptionImage,DescriptionTextView, oldReminder.getDescription().trim(), "Description");

        if (oldReminder.getType().equals("Date")) {
            String hour,minutes;
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
            ExpandDialogAndSetData2(removeDate,selectDateImage,TimeTextView, formattedDate, "Time");
        }

        if (oldReminder.getType().equals("Location") )
            ExpandDialogAndSetData2(removeLocation,locationImage, LocationTextView
                    , oldReminder.getLocationAsString(), "Location");
    }



    public void openDialog(boolean edit,reminders_view oldReminder,int position){
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





        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(UserName).child("reminder_list");
                DatabaseReference keyRef =ref.push();
                if(title.getText().toString().isEmpty())
                    reminder.setMessage("Reminder");
                else
                    reminder.setMessage(title.getText().toString());
                reminder.setState(true);
                reminder.setMyType(checkType());
                if(!edit) {
                    reminder.setKey(keyRef.getKey());
                    keyRef.setValue(reminder);
                }
                else{
                    cancelNotification(reminder.getKey(),HomePage.getInstance());
                    keyRef=ref.child(reminder.getKey());
                    keyRef.setValue(reminder);
                }
                if(reminder.getRemindDate()==null)
                {
                    date = new Date();
                    reminder.setRemindDate((date));


                }
                sendToAlarmManager(reminder);



//                if(edit) {
//
//                    reminder.setRemindDate(oldReminder.getDate());
//                    reminder.setLocationAsString(oldReminder.getLocationAsString());
//                    reminder.setDescription(oldReminder.getDescription());
//                    reminder.setKey(oldReminder.getKey());
//
//                }

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
                add_location_dialog = new Dialog(add_reminder_dialog.getContext());
                add_location_dialog.setContentView(R.layout.location_menu);
                LocationMenu = add_location_dialog.findViewById(R.id.LocationMenu);
                searchLocation=add_location_dialog.findViewById(R.id.searchButton);
                List<String> Menu=new ArrayList<>();

                Menu.add("Other");
                Menu.add("Supermarket");
                Menu.add("Library");
                Menu.add("book_store");
                Menu.add("Atm");
                Menu.add("Bank");
                Menu.add("Restaurant");
                Menu.add("Cafe");
                Menu.add("Bakery");
                Menu.add("gas_station");
                Menu.add("car_wash");
                Menu.add("car_repair");
                Menu.add("post_office");

                Menu.add("laundry");
                Menu.add("pharmacy");
                RadioButton optionButton;
                for(String option:Menu){
                    optionButton = new RadioButton(add_location_dialog.getContext());
                    optionButton.setText(option);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.height=150;
                    optionButton.setLayoutParams(params);
                    optionButton.setTextSize(24);
                    optionButton.setTextColor(Color.GRAY);
                    optionButton.setButtonTintList(ColorStateList.valueOf(Color.GRAY));
                    LocationMenu.addView(optionButton);
                }

                if(reminder.getLocationAsString()!=null){
                    int count = LocationMenu.getChildCount();
                    ArrayList<RadioButton> listOfRadioButtons = new ArrayList<RadioButton>();
                    for (int i=0;i<count;i++) {
                        View o = LocationMenu.getChildAt(i);
                        if (o instanceof RadioButton) {
                            listOfRadioButtons.add((RadioButton)o);
                        }
                    }
                    for(RadioButton button:listOfRadioButtons){
                        if (button.getText().toString().equals(reminder.getLocationAsString())) {
                            button.setChecked(true);
                            searchLocation.setEnabled(true);
                        }
                    }
                }

                LocationMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        searchLocation.setEnabled(true);
                        RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                        Category=checkedRadioButton.getText().toString();
                    }
                });

                searchLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //save the chosen category in the reminder and start search the location
                        add_location_dialog.cancel();



                        if (Category.equals("Other")){
                            System.out.println("CATEGORYYYY "+Category);
                            searchLocationbar.setVisibility(View.VISIBLE);
                            LocationTextView.setVisibility(View.INVISIBLE);
                            locationImage.setVisibility(View.INVISIBLE);
                            removeLocation.setVisibility(View.INVISIBLE);


                            searchLocationbar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    //Places.initialize(HomePage.getInstance(), "AIzaSyDMU9eVBmHymFvymjsCO3pUCBwwGMTqV5w");
                                    List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME,Place.Field.TYPES);
                                    Intent intentL= new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).setCountry("IL").build(HomePage.addRdialog);
                                    //Intent intentL= new AutocompletePrediction().IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).setCountry("IL").build(HomePage.this).addCategory("university");
//setInitialQuery().
                                    HomePage.addRdialog.startActivityForResult(intentL,100);
                                   // wantedLocation = getSharedPreferences("U", MODE_PRIVATE)
//                                    sharedPreferences=getSharedPreferences("U",MODE_PRIVATE);
//
//                                    System.out.println(sharedPreferences.getString("location",null));


                                }

                            });

                        }
                        reminder.setLocationAsString(Category);

                        if(Category=="Other"){
                            searchLocation.setVisibility(View.INVISIBLE);
                            wantedLocation=HomePage.getInstance().wantedLocation;
                            System.out.println("PLACE****************8 "+wantedLocation);
                        }
                        if(Additions.contains("Location") && Category!="Other"){

                            LocationTextView.setText(Category);
                        }
                        else{
                            numberOfAdditions+=1;
                            ExpandDialogAndSetData(removeLocation,locationImage,LocationTextView,Category,"Location");
                            if (Category.equals("Other")){
                            LocationTextView.setVisibility(View.INVISIBLE);
                            locationImage.setVisibility(View.INVISIBLE);
                            removeLocation.setVisibility(View.INVISIBLE);}
                        }
                    }
                });
                add_location_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                add_location_dialog.show();
            }
        });








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
                add_description_dialog = new Dialog(add_reminder_dialog.getContext());
                add_description_dialog.setContentView(R.layout.add_description);
                save_description= add_description_dialog.findViewById(R.id.save_description);
                cancel_desc_dialog=add_description_dialog.findViewById(R.id.cancel_desc_dialog);
                AddDescriptionEditText=add_description_dialog.findViewById(R.id.AddDescriptionEditText);
                AddDescriptionEditText.setFocusedByDefault(true);

                if(reminder.getDescription()!=null) {
                    AddDescriptionEditText.setText(reminder.getDescription());
                    save_description.setEnabled(true);
                }

                AddDescriptionEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(!AddDescriptionEditText.getText().toString().isEmpty())
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
                        if(Additions.contains("Description")){
                            DescriptionTextView.setText(AddDescriptionEditText.getText().toString());
                        }
                        else{
                            numberOfAdditions+=1;
                            ExpandDialogAndSetData(removeDescription,addDescriptionImage,DescriptionTextView,AddDescriptionEditText.getText().toString(),"Description");
                        }
                        reminder.setDescription(AddDescriptionEditText.getText().toString());
                        add_description_dialog.cancel();
                    }
                });
                add_description_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                add_description_dialog.show();
            }
        });
        add_reminder_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        add_reminder_dialog.show();
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

            //  location.setText(String.format("Locality Name : %s",place.getName()));
        }
        else if (resultCode== AutocompleteActivity.RESULT_ERROR)
        {
            Status status =Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();;
        }
    }



    private String checkType() {
        boolean Location=false,Date=false;
        if (Additions.contains("Location")) Location=true;
        else{ Date=true;}
        //if(Location && Date) return "Location_Date";
        if(Location) return "Location";
        if(Date) return "Date";
        return "Todo List";
    }


}
