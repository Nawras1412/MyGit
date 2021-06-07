package com.example.smartremindersapp2;

import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class remindres_view {
    private String massege;
    private Date date;
    private Double LAT;
    private Double LNG;

    private String discription;
    private String key;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private Switch Switch;
    private String type;
    private ImageView Delet_Button;
//    private ImageView Edit_Button;



    public remindres_view(String Key, String massege, String discription ,String type) {
        this.key = Key;
        this.massege = massege;
        this.type=type;
        this.discription = discription;
    }

    public remindres_view(){}


    public String getMassege() {
        return massege;
    }

    public void setMassege(String massege) {
        this.massege = massege;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public boolean getSwitch() {
        return Switch.isChecked();
    }

    public void setSwitch(boolean aSwitch) {
        Switch.setChecked(aSwitch);
    }

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
