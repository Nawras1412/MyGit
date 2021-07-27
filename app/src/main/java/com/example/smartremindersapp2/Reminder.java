package com.example.smartremindersapp2;

//import android.arch.persistence.room.ColumnInfo;
//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.PrimaryKey;
//import android.support.annotation.NonNull;

import android.app.Dialog;
import android.location.Location;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import java.util.Date;

public class Reminder {
    public int id;
    private String Message;
    private Date  RemindDate;
    private String Key;
    private String Description;
    private Double LAT;
    private Double LNG;
    private boolean state;
    private String LocationAsString;





    public String getLocationAsString(){return LocationAsString;}

    public void setLocationAsString(String Location){this.LocationAsString=Location;}

    public Double getLAT() {
        return LAT;
    }

    public void setLAT(Double LAT) {
        this.LAT = LAT;
    }

    public Double getLNG() {
        return LNG;
    }

    public void setLNG(Double LNG) {
        this.LNG = LNG;
    }

    String MyType;
    public String getMyType() {
        return MyType;
    }

    public void setMyType(String myType) {
        MyType = myType;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Reminder(String message, Date remindDate) {
        Message =message ;
        RemindDate = remindDate;
        setState(true);
        Description="";
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Reminder() {}

    public String getMessage() {
        return Message;
    }

    public Date getRemindDate() {
        return RemindDate;
    }

    public int getId() {
        return id;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public void setRemindDate(Date remindDate) {
        this.RemindDate = remindDate;
    }

    public void setId(int id) {
        this.id = id;
    }
}









//package com.example.smartremindersapp2;
//
////import android.arch.persistence.room.ColumnInfo;
////import android.arch.persistence.room.Entity;
////import android.arch.persistence.room.PrimaryKey;
////import android.support.annotation.NonNull;
//
//import androidx.annotation.NonNull;
//import com.google.firebase.database.DatabaseReference;
//import java.util.Date;
//
//public class Reminder {
//    @NonNull
//    public int id;
//    String Message;
//    Date  RemindDate;
//    String Key;
//    String Description;
//
//    public String getKey() {
//        return Key;
//    }
//
//    public void setKey(String key) {
//        Key = key;
//    }
//
//    public String getDescription() {
//        return Description;
//    }
//
//    public void setDescription(String description) {
//        Description = description;
//    }
//
//    public Reminder(String message, Date remindDate) {
//        Message =message ;
//        RemindDate = remindDate;
//    }
//
//    public Reminder() {
//    }
//
//    public String getMessage() {
//        return Message;
//    }
//
//    public Date getRemindDate() {
//        return RemindDate;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setMessage(String message) {
//        this.Message = message;
//    }
//
//    public void setRemindDate(Date remindDate) {
//        this.RemindDate = remindDate;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//}