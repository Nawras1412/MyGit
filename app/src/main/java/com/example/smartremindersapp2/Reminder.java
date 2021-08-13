package com.example.smartremindersapp2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String Location;
    private List<String> audios;
    private String MyType;
    private User Person;
    private boolean DateState;



    public Reminder(String message, Date remindDate) {
        Message =message ;
        RemindDate = remindDate;
        setState(true);
        Description="";
        audios=new ArrayList<>();
        DateState=false;
    }
    public Reminder() {}


    /*
    getters
     */
    public String getLocation() {
        return Location;
    }
    public User getPerson() { return Person; }
    public String getLocationAsString(){return LocationAsString;}
    public Double getLAT() {
        return LAT;
    }
    public Double getLNG() {
        return LNG;
    }
    public String getMyType() {
        return MyType;
    }
    public String getKey() {
        return Key;
    }
    public String getDescription() {
        return Description;
    }
    public List<String> getAudios() {
        return audios;
    }
    public boolean isState() {
        return state;
    }
    public String getMessage() {
        return Message;
    }
    public Date getRemindDate() {
        return RemindDate;
    }
    public int getId() {
        return id;
    }
    public boolean isDateState() {return DateState;}



    /*
    setters
     */
    public void setLocation(String location) {
        Location = location;
    }
    public void setPerson(User person) { Person = person; }
    public void setLocationAsString(String Location){this.LocationAsString=Location;}
    public void setLAT(Double LAT) {
        this.LAT = LAT;
    }
    public void setLNG(Double LNG) {
        this.LNG = LNG;
    }
    public void setMyType(String myType) {
        MyType = myType;
    }
    public void setKey(String key) {
        Key = key;
    }
    public void setDescription(String description) {
        Description = description;
    }
    public void setAudios(List<String> audios) {
        this.audios = audios;
    }
    public void setState(boolean state) {
        this.state = state;
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
    public void setDateState(boolean dateState) {DateState = dateState;}
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