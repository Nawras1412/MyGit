package com.example.smartremindersapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class retrieve_password extends AppCompatActivity {
    EditText UserName;
    EditText Password;
    EditText ConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);
        UserName=findViewById(R.id.UserName);
        Password=findViewById(R.id.password);
        ConfirmPassword=findViewById(R.id.ConfirmPassword);
    }


    public void retrievePasswordFunc(View view) {
        List<String> errors = CheckIfLegal(UserName.getText().toString(), Password.getText().toString(), ConfirmPassword.getText().toString());
        for (String message :errors){
            if (message.contentEquals("user name empty")){
                AuxiliaryFunctions.SetErrorOnTextView(UserName,"Enter User Name");
            }
            else if(message.contentEquals("empty password")){
                AuxiliaryFunctions.SetErrorOnTextView(Password,"Enter Password");
            }
            else if(message.contentEquals("empty confirm password")){
                AuxiliaryFunctions.SetErrorOnTextView(ConfirmPassword,"Confirm Password");
            }
            else if(message.contentEquals("inappropriate password")){
                AuxiliaryFunctions.SetErrorOnTextView(ConfirmPassword,"Enter Appropriate Password");
            }
            else if(message.contentEquals("incorrect username")){
                AuxiliaryFunctions.SetErrorOnTextView(UserName,"incorrect username");
            }
            else if(message.contentEquals("success")){

                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(UserName.getText().toString()).exists()) {
                            SaveInDatabase.UpdateUserData(UserName.getText().toString(),"password",Password.getText().toString());
                            AuxiliaryFunctions openpage=AuxiliaryFunctions.getInstance();
                            openpage.openNewPage(getApplicationContext(),login.class);
                        }
                        else{
                            AuxiliaryFunctions.SetErrorOnTextView(UserName,"incorrect username");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        }
    }


    public List<String> CheckIfLegal(String UserName, String Password, String ConfirmPassword){
        List<String> errors=new ArrayList<>();
        if (UserName.isEmpty()) errors.add("user name empty");
        if (Password.isEmpty())  errors.add("empty password");
        if (ConfirmPassword.isEmpty())  errors.add("empty confirm password");
        if (!Password.isEmpty() && !ConfirmPassword.isEmpty())
            if (!Password.contentEquals(ConfirmPassword))
                errors.add("inappropriate password");
        if (errors.isEmpty()) errors.add("success");
        return errors;
    }
}