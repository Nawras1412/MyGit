package com.example.smartremindersapp2;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class Reminders_control {
    private static DatabaseReference Ref;
    public void saveReminder(String message, Date remindDate) {
        // List<String> TheError=registration_control.checkRegistrationData(NameText,UserNameText,EmailText,PassWardText,ConfirmPassWardText,ref);
        //for (String message:TheError){
        // if (message.contentEquals("success")){

        Reminder new_reminder = new Reminder(message, remindDate);
        DatabaseReference Reminders = FirebaseDatabase.getInstance().getReference().child("Reminders");
        Ref = FirebaseDatabase.getInstance().getReference().child("Reminders").child(message);
        Ref.setValue(new_reminder);
        final String[] returned = {""};
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(message).exists()) {

                    returned[0] = "TRY AGAIN;The reminders exits! ";
                } else {
                    Ref = FirebaseDatabase.getInstance().getReference().child("Reminders").child(message);
                    Ref.setValue(new_reminder);
                    returned[0] = "reminder added succesfuly";
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


}}