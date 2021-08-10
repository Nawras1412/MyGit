package com.example.smartremindersapp2;

import android.app.Dialog;
import android.location.Location;
import android.widget.ImageView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class reminders_view {
    private String Title;
    private Date date;
    private Double LAT;
    private Double LNG;
    private String description;
    private String key;
    private Switch Switch;
    private String type;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String location;
    private String LocationAsString;
    private ImageView Delete_Button;
    private List<String> audios;


    public reminders_view(String Key, String title, String description , String type,Date date,
                          String location,String address,List<String> audioArr) {
        this.key = Key;
        this.Title = title;
        this.type=type;
        this.location=address;
        this.description = description;
        this.date=date;
        this.LocationAsString=location;
//        this.audios=new ArrayList<>();
        this.audios=audioArr;
    }
    public reminders_view() {}
    public List<String> getAudios(){return audios;}
    public void setAudios(List<String> audios1){ this.audios=audios1;}
    public String getLocationAsString(){return LocationAsString;}
    public void setLocationAsString(String Location){
        this.LocationAsString=Location;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        this.Title = title;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }
//    public boolean getSwitch() {
//        return Switch.isChecked();
//    }
//    public void setSwitch(boolean aSwitch) {
//        Switch.setChecked(aSwitch);
//    }
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
}








//package com.example.smartremindersapp2;
//
//import android.widget.ImageView;
//import android.widget.Switch;
//
//import java.util.Date;
//
//public class remindres_view {
//    private String massege;
//    private Date date;
//    private String discription;
//    private String key;
//    private Switch Switch;
//    private ImageView Delet_Button;
////    private ImageView Edit_Button;
//
//
//
//    public remindres_view(String Key, String massege, Date date, String discription) {
//        this.key = Key;
//        this.massege = massege;
//        this.date = date;
//        this.discription = discription;
//    }
//
//    public remindres_view(){}
//
//
//    public String getMassege() {
//        return massege;
//    }
//
//    public void setMassege(String massege) {
//        this.massege = massege;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public String getDiscription() {
//        return discription;
//    }
//
//    public void setDiscription(String discription) {
//        this.discription = discription;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }
//
//    public String getKey() {
//        return key;
//    }
//
//    public boolean getSwitch() {
//        return Switch.isChecked();
//    }
//
//    public void setSwitch(boolean aSwitch) {
//        Switch.setChecked(aSwitch);
//    }
//
//
//}
