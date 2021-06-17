package com.example.smartremindersapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.alarmViewHolder> {
    private static ArrayList<alarm_view> mAlram_view_list;
    private String username;
    private Context mcontext;
    private OnItemClickListener mlistener;


    public static void setmAlram_view_list(ArrayList<alarm_view> mAlram_view_list) {
        mAlram_view_list = mAlram_view_list;
    }

    public static ArrayList<alarm_view> getmAlram_view_list() {
        return mAlram_view_list;
    }

    public static void updateAlarmsList(int position,alarm_view alarm){
        mAlram_view_list.set(position,alarm);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }



    public class alarmViewHolder extends RecyclerView.ViewHolder{
        public TextView mhour;
        public TextView mminutes;
        public Switch mSwitch;
        public TextView mdays_date;
        public TextView alarmName;
        public ImageView DeleteB;
        public alarmViewHolder( View itemView,final OnItemClickListener listener) {
            super(itemView);
            mhour=itemView.findViewById(R.id.hours);
            mminutes=itemView.findViewById(R.id.minutes);
            mdays_date=itemView.findViewById(R.id.date_days);
            mSwitch=itemView.findViewById(R.id.Switch);
            DeleteB=itemView.findViewById(R.id.DeleteButton);
            alarmName=itemView.findViewById(R.id.alarm_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);

                            //get the position of the edited alarm
                            alarm_view currentAlarm=mAlram_view_list.get(position);
                           //must update the key of the alarm in the list
                            Intent intent=new Intent(mcontext,alarm_clock.class);
                            intent.putExtra("Key",currentAlarm.getKey());
                            intent.putExtra("position",position);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mcontext.startActivity(intent);
//                            mAlram_view_list.remove(position);
//                            notifyDataSetChanged();

//                            DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("Alarms").child(currentAlarm.getKey());
//                            ref.removeValue();
//                            mAlram_view_list.remove(position);
//                            notifyDataSetChanged();
//                            all_alarms AC = new all_alarms();
//                            AC.cancelAlarm(currentAlarm.getKey(),mcontext);
//                            Intent intent=new Intent(mcontext,alarm_clock.class);
//                            intent.setClass("alarm_view",currentAlarm);

//                            ref = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("Alarms");
//                            if (mSwitch.isChecked()) {
//                                ref.addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot snapshot) {
//                                        for (DataSnapshot ds : snapshot.getChildren()) {
//                                            clock_alarm_entity alarm = ds.getValue(clock_alarm_entity.class);
//                                            // get the key pf the alarm
////                                            if ((alarm.getHour() == mhour.getText().toString()) &&
////                                                    (alarm.getMinuter() == mminutes.getText().toString())) {
////                                                if((mdays_date.getText().toString().isEmpty()) &&
////                                                        (alarm.getDays().isEmpty())){
////                                                    AC.cancelAlarm(alarm.getKey(), mcontext);
////                                                }
////                                                else{
////                                                    String[] days= mdays_date.getText().toString().split(",");
////                                                    for(String day:alarm.getDays()){
////
////                                                    }
////                                                    for(String day:days){
////                                                        if (day.equals("Sun") )
////                                                    }
////                                                }
////                                            }
//                                        }
//                                    }
//                                    @Override
//                                    public void onCancelled(DatabaseError error) {}
//                                });
//                                //                            setOnItemClickListener();
////                                AC.cancelAlarm(, mcontext);
//                                //**********************
//                            }
                        }
                    }
                }


            });
            DeleteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }


    public ExampleAdapter(Context context,ArrayList<alarm_view> alarm_view_list, String username){
        mAlram_view_list=alarm_view_list;
        this.username=username;
        mcontext = context;
    }
    @Override
    public alarmViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View vi= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_alarm_view,parent,false);
        alarmViewHolder evh=new alarmViewHolder(vi,mlistener);
        return evh;
    }

    public String getDateAsString(alarm_view alarm){
        List<String> days=alarm.getDays_date();
        if (days == null) {
            days = new ArrayList<>();
        }
        Date date;
        String text = "";
        if(days.isEmpty()){
            Calendar cal=Calendar.getInstance();
            cal.setTime(alarm.getDate());
            if (cal.before(Calendar.getInstance())){
                cal.add(Calendar.DATE,1);
                date=cal.getTime();
                String key= alarm.getKey();
                DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(username)
                        .child("Alarms").child(key);
                HashMap map=new HashMap();
                map.put("date",date);
                ref.updateChildren(map);
            }
            else
                date=alarm.getDate();
            String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
            String[] splitDate = formattedDate.split(",");
            String NextDate=splitDate[0]+","+splitDate[1];
            text=NextDate;
        } else {
            if (days.contains("0")) text =  text +   "<u>"+"<b> " + "S" + "</b>"+"</u>"+"  ";
            else text = text + " S  ";
            if (days.contains("1")) text = text + "<u>"+"<b>" + "M" + "</b>"+"</u>"+"  ";
            else text = text + " M  ";
            if (days.contains("2")) text = text +"<u>"+"<b>" + "T" + "</b>"+"</u>"+"  ";
            else text = text + " T  ";
            if (days.contains("3")) text =  text + "<u>"+"<b>" + "W" + "</b>"+"</u>"+"  ";
            else text = text + " W  ";
            if (days.contains("4")) text =  text + "<u>"+"<b>" + "T" + "</b>"+"</u>"+"  ";
            else text = text + " T  ";
            if (days.contains("5")) text =  text + "<u>"+"<b>" + "F" + "</b>"+"</u>"+"  ";
            else text = text + " F  ";
            if (days.contains("6")) text = text + "<u>"+"<b>" + "S" + "</b>"+"</u>"+"  ";
            else text = text + " S  ";
        }
        return text;
    }


    @Override
    public void onBindViewHolder( alarmViewHolder holder, int position) {
        alarm_view currentAlarm=mAlram_view_list.get(position);
        holder.mdays_date.setText(Html.fromHtml(getDateAsString(currentAlarm)));
        holder.alarmName.setText(currentAlarm.getTitle());
        if(Integer.toString(currentAlarm.getHour()).length()==1)
            holder.mhour.setText("0"+Integer.toString(currentAlarm.getHour()));
        else
            holder.mhour.setText(Integer.toString(currentAlarm.getHour()));

        if(Integer.toString(currentAlarm.getMinutes()).length()==1)
            holder.mminutes.setText("0"+Integer.toString(currentAlarm.getMinutes()));
        else
            holder.mminutes.setText(Integer.toString(currentAlarm.getMinutes()));

        if (currentAlarm.isChecked()==true) {
            holder.mSwitch.setChecked(true);
        }
        else{
            holder.mSwitch.setChecked(false);
        }
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("Alarms").child(currentAlarm.getKey());
        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("Alarms").child(currentAlarm.getKey()).child("checked");
        holder.DeleteB.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v){
               ref1.addListenerForSingleValueEvent(new ValueEventListener(){
                   @RequiresApi(api = Build.VERSION_CODES.M)
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot){
                       if ((boolean)(dataSnapshot.getValue())==true){
                           all_alarms AC = new all_alarms();
                           AC.cancelAlarm(currentAlarm,mcontext);
                       }
                       ref.removeValue();
                       mAlram_view_list.remove(position);
                       notifyDataSetChanged();
                   }
                   @Override
                   public void onCancelled(DatabaseError databaseError) {}
               });
//               alarm_clock.cancelAlarm(currentAlarm.getKey());
           }});
        holder.mSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences sharedPreferences=mcontext.getSharedPreferences("U",mcontext.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();

                ref1.addListenerForSingleValueEvent(new ValueEventListener(){
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot){
                        if ((boolean)(dataSnapshot.getValue())==true){
                            currentAlarm.setSwitch(false);
                            ref1.setValue(false);
                            all_alarms AC = new all_alarms();
                            AC.cancelAlarm(currentAlarm,mcontext);
                        }
                        else{
                            System.out.println("im in holder.mSwitch with false value ");
                            all_alarms AC=new all_alarms();
                            AC.startAlarm(currentAlarm,mcontext);
                            currentAlarm.setSwitch(true);
                            ref1.setValue(true);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return mAlram_view_list.size();
    }



}

//package com.example.smartremindersapp2;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Parcelable;
//import android.provider.AlarmClock;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Switch;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.List;
//
//
//public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.alarmViewHolder> {
//    private ArrayList<alarm_view> mAlram_view_list;
//    private String username;
//    private Context mcontext;
//    private OnItemClickListener mlistener;
//
//    public interface OnItemClickListener {
//        void onItemClick(int position);
//        void onDeleteClick(int position);
//    }
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        mlistener = listener;
//    }
//
//
//
//    public class alarmViewHolder extends RecyclerView.ViewHolder{
//        public TextView mhour;
//        public TextView mminutes;
//        public Switch mSwitch;
//        public TextView mdays_date;
//        public TextView alarmName;
//        public ImageView DeleteB;
//        public alarmViewHolder( View itemView,final OnItemClickListener listener) {
//            super(itemView);
//            mhour=itemView.findViewById(R.id.hours);
//            mminutes=itemView.findViewById(R.id.minutes);
//            mdays_date=itemView.findViewById(R.id.date_days);
//            mSwitch=itemView.findViewById(R.id.Switch);
//            DeleteB=itemView.findViewById(R.id.DeleteButton);
//            alarmName=itemView.findViewById(R.id.alarm_title);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(position);
//
//                            //get the position of the edited alarm
//                            alarm_view currentAlarm=mAlram_view_list.get(position);
//                            Intent intent=new Intent(mcontext,alarm_clock.class);
//                            intent.putExtra("Key",currentAlarm.getKey());
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            mcontext.startActivity(intent);
////                            DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("Alarms").child(currentAlarm.getKey());
////                            ref.removeValue();
////                            mAlram_view_list.remove(position);
////                            notifyDataSetChanged();
////                            all_alarms AC = new all_alarms();
////                            AC.cancelAlarm(currentAlarm.getKey(),mcontext);
////                            Intent intent=new Intent(mcontext,alarm_clock.class);
////                            intent.setClass("alarm_view",currentAlarm);
//
////                            ref = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("Alarms");
////                            if (mSwitch.isChecked()) {
////                                ref.addValueEventListener(new ValueEventListener() {
////                                    @Override
////                                    public void onDataChange(DataSnapshot snapshot) {
////                                        for (DataSnapshot ds : snapshot.getChildren()) {
////                                            clock_alarm_entity alarm = ds.getValue(clock_alarm_entity.class);
////                                            // get the key pf the alarm
//////                                            if ((alarm.getHour() == mhour.getText().toString()) &&
//////                                                    (alarm.getMinuter() == mminutes.getText().toString())) {
//////                                                if((mdays_date.getText().toString().isEmpty()) &&
//////                                                        (alarm.getDays().isEmpty())){
//////                                                    AC.cancelAlarm(alarm.getKey(), mcontext);
//////                                                }
//////                                                else{
//////                                                    String[] days= mdays_date.getText().toString().split(",");
//////                                                    for(String day:alarm.getDays()){
//////
//////                                                    }
//////                                                    for(String day:days){
//////                                                        if (day.equals("Sun") )
//////                                                    }
//////                                                }
//////                                            }
////                                        }
////                                    }
////                                    @Override
////                                    public void onCancelled(DatabaseError error) {}
////                                });
////                                //                            setOnItemClickListener();
//////                                AC.cancelAlarm(, mcontext);
////                                //**********************
////                            }
//                        }
//                    }
//                }
//
//
//            });
//            DeleteB.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onDeleteClick(position);
//                        }
//                    }
//                }
//            });
//        }
//    }
//
//
//    public ExampleAdapter(Context context,ArrayList<alarm_view> alarm_view_list, String username){
//        mAlram_view_list=alarm_view_list;
//        this.username=username;
//        mcontext = context;
//    }
//    @Override
//    public alarmViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
//        View vi= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_alarm_view,parent,false);
//        alarmViewHolder evh=new alarmViewHolder(vi,mlistener);
//        return evh;
//    }
//
//
//
//    @Override
//    public void onBindViewHolder( alarmViewHolder holder, int position) {
//        alarm_view currentAlarm=mAlram_view_list.get(position);
//        holder.mdays_date.setText(Html.fromHtml(currentAlarm.getDays_date()));
//        holder.alarmName.setText(currentAlarm.getTitle());
//        if(Integer.toString(currentAlarm.getHour()).length()==1)
//            holder.mhour.setText("0"+Integer.toString(currentAlarm.getHour()));
//        else
//            holder.mhour.setText(Integer.toString(currentAlarm.getHour()));
//
//        if(Integer.toString(currentAlarm.getMinutes()).length()==1)
//            holder.mminutes.setText("0"+Integer.toString(currentAlarm.getMinutes()));
//        else
//            holder.mminutes.setText(Integer.toString(currentAlarm.getMinutes()));
//        if (currentAlarm.getSwitch()==true) {
//            System.out.println(currentAlarm.getSwitch());
//            holder.mSwitch.setChecked(true);
//        }
//        else{
//            holder.mSwitch.setChecked(false);
//        }
//        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("Alarms").child(currentAlarm.getKey());
//        holder.DeleteB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////               alarm_clock.cancelAlarm(currentAlarm.getKey());
//                ref.removeValue();
//                mAlram_view_list.remove(position);
//                notifyDataSetChanged();
//                all_alarms AC = new all_alarms();
//                AC.cancelAlarm(currentAlarm.getKey(),mcontext);
//            }});
//        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("Alarms").child(currentAlarm.getKey()).child("mSwitch");
//        holder.mSwitch.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                ref1.addListenerForSingleValueEvent(new ValueEventListener(){
//                    @RequiresApi(api = Build.VERSION_CODES.M)
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if ((boolean)(dataSnapshot.getValue())==true){
//                            currentAlarm.setSwitch(false);
//                            ref1.setValue(false);
//                            all_alarms AC = new all_alarms();
//                            AC.cancelAlarm(currentAlarm.getKey(),mcontext);
//                        }
//                        else{
//                            Calendar c = Calendar.getInstance();
////                c.setTimeInMillis(System.currentTimeMillis());
//                            c.set(Calendar.HOUR_OF_DAY, currentAlarm.getHour());
//                            c.set(Calendar.MINUTE, currentAlarm.getMinutes());
//                            c.set(Calendar.SECOND, 0);
//                            all_alarms AC=new all_alarms();
//                            AC.startAlarm(c,currentAlarm.getKey(),mcontext);
//                            currentAlarm.setSwitch(true);
//                            ref1.setValue(true);
//                        }
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {}
//                });
//            }
//        });
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return mAlram_view_list.size();
//    }
//
//
//
//}
//
