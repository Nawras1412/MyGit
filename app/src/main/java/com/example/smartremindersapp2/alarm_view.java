package com.example.smartremindersapp2;
import android.widget.ImageView;

import java.util.Date;
import java.util.List;

public class alarm_view {
    private String key;
    private int hour;
    private int minutes;
    private boolean mSwitch;
    private List<String> days_date;
    private String Title;
    private Date date;



    public alarm_view(String Key,String title,int Hour, int Minutes, List<String> Days_date,boolean isSwitched,Date datex) {
        key=Key;
        Title=title;
        hour = Hour;
        minutes = Minutes;
        days_date = Days_date;
        mSwitch=isSwitched;
        date=datex;
    }

    public alarm_view(){}

    public void setDate(Date date) {this.date = date;}

    public String getKey() {
        return key;
    }

    public int getHour() {
        return hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public boolean isChecked() {
        return mSwitch;
    }

    public List<String> getDays_date() {
        return days_date;
    }

    public String getTitle() {
        return Title;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSwitch(boolean aSwitch) {
        this.mSwitch=aSwitch;
    }

    public void setDays_date(List<String> days_date) {
        this.days_date = days_date;
    }

    public void setTitle(String title) {
        this.Title = title;
    }


    public Date getDate() {
        return date;
    }
}



//package com.example.smartremindersapp2;
//import android.widget.ImageView;
//
//import java.util.Date;
//
//public class alarm_view {
//    private String key;
//    private int hour;
//    private int minutes;
//    private boolean mSwitch;
//    private String days_date;
//    private String Title;
//    private Date date;
//
//
//
//    public alarm_view(String Key,String title,int hour, int minutes, String days_date,boolean isSwitched,Date datex) {
//        this.key=Key;
//        this.Title=title;
//        this.hour = hour;
//        this.minutes = minutes;
//        this.days_date = days_date;
//        this.mSwitch=isSwitched;
//        this.date=datex;
//
//
//
//    }
//
//    public alarm_view(){}
//
//
//
//    public String getKey() {
//        return key;
//    }
//
//    public int getHour() {
//        return hour;
//    }
//
//    public int getMinutes() {
//        return minutes;
//    }
//
//    public boolean getSwitch() {
//        return this.mSwitch;
//    }
//
//    public String getDays_date() {
//        return days_date;
//    }
//
//    public String getTitle() {
//        return Title;
//    }
//
//    public void setHour(int hour) {
//        this.hour = hour;
//    }
//
//    public void setMinutes(int minutes) {
//        this.minutes = minutes;
//    }
//
//    public void setSwitch(boolean aSwitch) {
//        this.mSwitch=aSwitch;
//    }
//
//    public void setDays_date(String days_date) {
//        this.days_date = days_date;
//    }
//
//    public void setTitle(String title) {
//        this.Title = title;
//    }
//
//
//    public Date getDate() {
//        return date;
//    }
//}
//



