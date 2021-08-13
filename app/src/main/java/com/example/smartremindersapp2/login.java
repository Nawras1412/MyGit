package com.example.smartremindersapp2;
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
        sharedPreferences=getSharedPreferences("U",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putInt("AlarmsNum",0);
        editor.commit();
        if (getIntent().getBooleanExtra("logout",false)==true){
            editor.putBoolean("saveLogIn",false);
            editor.commit();
        }
        savelogin=sharedPreferences.getBoolean("saveLogIn",false);
        if (savelogin==true){
            UserNameText.setText(sharedPreferences.getString("username",null));
            PasswordText.setText(sharedPreferences.getString("password",null));
            AuxiliaryFunctions openpage=AuxiliaryFunctions.getInstance();
            openpage.openNewPage(getApplicationContext(),HomePage.class);
        }
        else{
            UserNameText.setText("");
            PasswordText.setText("");
        }
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
