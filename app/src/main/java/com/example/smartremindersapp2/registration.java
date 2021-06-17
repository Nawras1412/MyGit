package com.example.smartremindersapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class registration extends AppCompatActivity {
    /*
    variables
     */
    private EditText NameText;
    private EditText UserNameText;
    private EditText EmailText;
    private EditText PassWardText;
    private EditText ConfirmPassWardText;
    private  DatabaseReference ref;


    public  registration(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        NameText = findViewById(R.id.Name_r);
        UserNameText = findViewById(R.id.UserName_r);
        EmailText = findViewById(R.id.Email_r);
        PassWardText = findViewById(R.id.password_r);
        ConfirmPassWardText = findViewById(R.id.ConfirmPassword_r);
    }

    public void Registration(View view) {
        List<String> TheError=checkRegistrationData(NameText,UserNameText,EmailText,PassWardText,ConfirmPassWardText);
        boolean legal=Exeptions(TheError);
        if (legal==true){
            ref=FirebaseDatabase.getInstance().getReference().child("Users").child(UserNameText.getText().toString());
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        AuxiliaryFunctions.SetErrorOnTextView(UserNameText,"user name already exist");
                    } else {
                        User user = new User(NameText.getText().toString(), UserNameText.getText().toString(), EmailText.getText().toString(),
                                PassWardText.getText().toString());
                        SaveInDatabase.saveUser(user);
                        AuxiliaryFunctions opendialog=AuxiliaryFunctions.getInstance();
                        opendialog.openDialogD(getSupportFragmentManager());


                        SharedPreferences sharedPreferences=getSharedPreferences("U",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();;
                        editor.putInt("AlarmsNum",0);
                        editor.commit();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }
    }



    public boolean Exeptions(List<String> TheError) {
        for (String message : TheError) {
            if (message.contentEquals("success")) {
                return true;
            } else if (message.contentEquals("empty first name")) {
                AuxiliaryFunctions.SetErrorOnTextView(NameText,"Enter First Name");
            }
            else if (message.contentEquals("first name must contain only letters")) {
                AuxiliaryFunctions.SetErrorOnTextView(NameText,"Must Contain Only Letters");
            }
            else if (message.contentEquals("empty user name")) {
                AuxiliaryFunctions.SetErrorOnTextView(UserNameText,"Enter User Name");
            }
            else if (message.contentEquals("user name already exist")) {
                AuxiliaryFunctions.SetErrorOnTextView(UserNameText,"UserName Already Exist");
            }
            else if (message.contentEquals("empty email")) {
                AuxiliaryFunctions.SetErrorOnTextView(EmailText,"Enter An Email");
            }
            else if (message.contentEquals("invalid email")) {
                AuxiliaryFunctions.SetErrorOnTextView(EmailText,"Invalid Email");
            }
            else if (message.contentEquals("empty password")) {
                AuxiliaryFunctions.SetErrorOnTextView(PassWardText,"Empty Password");
            }
            else if (message.contentEquals("empty confirm password")) {
                AuxiliaryFunctions.SetErrorOnTextView(ConfirmPassWardText,"didn't confirm password");
            }
            else if (message.contentEquals("inappropriate passward")) {
                AuxiliaryFunctions.SetErrorOnTextView(ConfirmPassWardText,"inappropriate passward");
            }
        }
        return false;
    }


    public List<String> checkRegistrationData(EditText nameText, EditText userNameText, EditText emailText, EditText passWardText, EditText confirmPassWardText)  {
        List<String> errors = new ArrayList<>();
        if (userNameText.getText().toString().isEmpty()) {
            errors.add("empty user name");
        }
        if (nameText.getText().toString().isEmpty()) {
            errors.add("empty first name");
        } else if (!Pattern.matches("[a-zA-Z]+", nameText.getText().toString())) {
            errors.add("first name must contain only letters");
        }
        if (emailText.getText().toString().isEmpty()) {
            errors.add("empty email");
        } else {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";
            Pattern pat = Pattern.compile(emailRegex);
            if (pat.matcher(emailText.getText().toString()).matches() == false) {
                errors.add("invalid email");
            }
        }
        if (passWardText.getText().toString().isEmpty()) {
            errors.add("empty password");
        }
        if (confirmPassWardText.getText().toString().isEmpty()) {
            errors.add("empty confirm password");
        } else if (!(confirmPassWardText.getText().toString().contentEquals(passWardText.getText().toString()))) {
            errors.add("inappropriate passward");
        }

        System.out.println(errors);
        if (errors.isEmpty()) {
            errors.add("success");
        }
        return errors;
    }
}
