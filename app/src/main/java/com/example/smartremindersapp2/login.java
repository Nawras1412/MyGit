//package com.example.smartremindersapp2;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import androidx.appcompat.app.AppCompatActivity;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//
//
//public class login extends AppCompatActivity implements View.OnClickListener{
//    private EditText UserNameText;
//    private EditText PasswordText;
//    private DatabaseReference ref;
//    private MySharedPreferences mySharedPreferences;
////    private SharedPreferences sharedPreferences;
////    private SharedPreferences.Editor editor;
////    private boolean savelogin;
//
//
//    public login(){}
//
//    public boolean checkLogIn(EditText UserName, EditText Password) {
//        if (Password.getText().toString().isEmpty() || UserName.getText().toString().isEmpty()) {
//            if (Password.getText().toString().isEmpty()) {
//                AuxiliaryFunctions.SetErrorOnTextView(Password, "enter password");
//                if (UserName.getText().toString().isEmpty()) {
//                    AuxiliaryFunctions.SetErrorOnTextView(UserName, "enter user name");
//                }
//            }
//            return false;
//        }
//        return true;
//    }
//
//
//
//    public void retrieve_password(View view) {
//        AuxiliaryFunctions openpage=AuxiliaryFunctions.getInstance();
//        openpage.openNewPage(getApplicationContext(),retrieve_password.class);
//    }
//
//
//    public void registration(View view) {
//        AuxiliaryFunctions openpage = AuxiliaryFunctions.getInstance();
//        openpage.openNewPage(getApplicationContext(), registration.class);
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        UserNameText = findViewById(R.id.UserName_r);
//        PasswordText = findViewById(R.id.Password);
//        editSharedPreferences(UserNameText.getText().toString());
//    }
//
//
//
//
//    private void editSharedPreferences(String username){
//        mySharedPreferences= new MySharedPreferences();
//        if (getIntent().getBooleanExtra("logout",false)==true){
//            mySharedPreferences.getEditor().putBoolean("saveLogIn",false);
//            mySharedPreferences.getEditor().commit();
//        }
//        if (mySharedPreferences.getMySharedPreferences().getBoolean("saveLogIn",false)==true){
//            UserNameText.setText(mySharedPreferences.getMySharedPreferences().getString("username",null));
//            PasswordText.setText(mySharedPreferences.getMySharedPreferences().getString("password",null));
//            AuxiliaryFunctions openpage=AuxiliaryFunctions.getInstance();
//            openpage.openNewPage(getApplicationContext(),HomePage.class);
//        }
//        else{
//            UserNameText.setText("");
//            PasswordText.setText("");
//        }
//    }
//
//
//    public void log_in_button(View view){
//        boolean legal= checkLogIn(UserNameText,PasswordText);
//        if (legal==true){
//            ref=FirebaseDatabase.getInstance().getReference().child("Users").child(UserNameText.getText().toString());
//            ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if (!dataSnapshot.exists()) {
//                        AuxiliaryFunctions.SetErrorOnTextView(UserNameText, "user name is not exist");
//                    } else {
//                        if (((String) dataSnapshot.child("password").getValue()).contentEquals(PasswordText.getText().toString())) {
//                            mySharedPreferences.SaveLoginData(UserNameText.getText().toString(),PasswordText.getText().toString());
//                            SaveInDatabase.UpdateUserData(UserNameText.getText().toString(),"status","1");
//                            AuxiliaryFunctions openpage=AuxiliaryFunctions.getInstance();
//                            openpage.openNewPage(getApplicationContext(),HomePage.class);
//                        }
//                        else{
//                            AuxiliaryFunctions.SetErrorOnTextView(PasswordText, "Incorrect Password");
//                        }
//                    }
//                }
//                @Override
//                public void onCancelled(DatabaseError databaseError) {}
//            });
//        }
//    }
//
//
//
//
//    @Override
//    public void onClick(View v) {}
//}




package com.example.smartremindersapp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;


public class login extends AppCompatActivity implements View.OnClickListener{
    private EditText UserNameText;
    private EditText PasswordText;
    private DatabaseReference ref;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean savelogin;


    public login(){}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserNameText = findViewById(R.id.UserName_r);
        PasswordText = findViewById(R.id.Password);
        editSharedPreferences(UserNameText.getText().toString());
    }

    public void retrieve_password(View view) {
        AuxiliaryFunctions openpage=AuxiliaryFunctions.getInstance();
        openpage.openNewPage(getApplicationContext(),retrieve_password.class);
    }


    public void registration(View view) {
        AuxiliaryFunctions openpage=AuxiliaryFunctions.getInstance();
        openpage.openNewPage(getApplicationContext(),registration.class);
    }


    private void editSharedPreferences(String username){
        // must check if it should be "U" or the user name in getSharedPreferences
        //sharedPreferences=getSharedPreferences("U",MODE_PRIVATE);
        sharedPreferences=getSharedPreferences("U",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if (getIntent().getBooleanExtra("logout",false)==true){
            editor.putBoolean("saveLogIn",false);
            editor.commit();
        }
        //editor.putBoolean("saveLogIn",false);
        savelogin=sharedPreferences.getBoolean("saveLogIn",false);
        if (savelogin==true){
            UserNameText.setText(sharedPreferences.getString("username",null));
            PasswordText.setText(sharedPreferences.getString("password",null));
            AuxiliaryFunctions openpage=AuxiliaryFunctions.getInstance();
            openpage.openNewPage(getApplicationContext(),HomePage.class);
//            openNewPage(HomePage.class);
        }
        else{
            UserNameText.setText("");
            PasswordText.setText("");
        }
    }


    public void openNewPage(Class classX){
        Intent intent=new Intent(this,classX);
        intent.putExtra("User Name", UserNameText.getText().toString());
        intent.putExtra("login", "login");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (classX==HomePage.class){
            intent.putExtra("editor", String.valueOf(editor));
        }
        startActivity(intent);
    }





    public boolean checkLogIn(EditText UserName, EditText Password) {
        if (Password.getText().toString().isEmpty() || UserName.getText().toString().isEmpty()) {
            if (Password.getText().toString().isEmpty()) {
                AuxiliaryFunctions.SetErrorOnTextView(Password, "enter password");
                if (UserName.getText().toString().isEmpty()) {
                    AuxiliaryFunctions.SetErrorOnTextView(UserName, "enter user name");
                }
            }
            return false;
        }
        return true;
    }

    public void log_in_button(View view){
        boolean legal= checkLogIn(UserNameText,PasswordText);
        if (legal==true){
            ref=FirebaseDatabase.getInstance().getReference().child("Users").child(UserNameText.getText().toString());
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println(dataSnapshot.exists());
                    if (!dataSnapshot.exists()) {
                        UserNameText.setError("user name is not exist");
                        UserNameText.requestFocus();
                    } else {
                        if (((String) dataSnapshot.child("password").getValue()).contentEquals(PasswordText.getText().toString())) {
                            HashMap hashmap = new HashMap();
                            hashmap.put("status", "1");
                            editor.putBoolean("saveLogIn",true);
                            editor.putString("username",UserNameText.getText().toString());
                            editor.putString("password",PasswordText.getText().toString());
                            editor.commit();
                            ref = FirebaseDatabase.getInstance().getReference().child("Users").child(UserNameText.getText().toString());
                            ref.updateChildren(hashmap);
//                            openNewPage(HomePage.class);
                            AuxiliaryFunctions openpage=AuxiliaryFunctions.getInstance();
                            openpage.openNewPage(getApplicationContext(),HomePage.class);
                        }
                        else{
                            PasswordText.setError("Incorrect Password");
                            PasswordText.requestFocus();
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }
    }

    @Override
    public void onClick(View v) {}
}
