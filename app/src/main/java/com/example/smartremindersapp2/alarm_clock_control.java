package com.example.smartremindersapp2;

import android.widget.CheckBox;
import java.util.ArrayList;
import java.util.List;

public class alarm_clock_control {
    public static List<String> check_boxes(CheckBox sundayBox, CheckBox mondayBox, CheckBox tuesdayBox, CheckBox wednesdayBox, CheckBox thursdayBox, CheckBox fridayBox, CheckBox saturdayBox) {
        List<String> checkedBoxes=new ArrayList<>();
        if (sundayBox.isChecked()){ checkedBoxes.add("0");}
        if (mondayBox.isChecked()){ checkedBoxes.add("1");}
        if (tuesdayBox.isChecked()){ checkedBoxes.add("2");}
        if (wednesdayBox.isChecked()){ checkedBoxes.add("3");}
        if (thursdayBox.isChecked()){ checkedBoxes.add("4");}
        if (fridayBox.isChecked()){ checkedBoxes.add("5");}
        if (saturdayBox.isChecked()){ checkedBoxes.add("6");}
        return checkedBoxes;

    }
}