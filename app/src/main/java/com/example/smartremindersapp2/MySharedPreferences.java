package com.example.smartremindersapp2;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class MySharedPreferences extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    private String userNAme;
    private String Password;
    private boolean savelogin;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;



    public MySharedPreferences(){
        if (sharedPreferences==null)
            sharedPreferences=getSharedPreferences("U",MODE_PRIVATE);
            editor=sharedPreferences.edit();
    }


    public String getUserNAme() { return userNAme; }
    public String getPassword() { return Password; }
    public boolean isSavelogin() { return savelogin; }
    public SharedPreferences getMySharedPreferences() { return sharedPreferences; }
    public SharedPreferences.Editor getEditor() { return editor; }

    public void setUserNAme(String userNAme) { this.userNAme = userNAme; }
    public void setPassword(String password) { Password = password; }
    public void setSavelogin(boolean savelogin) { this.savelogin = savelogin; }
    public void setSharedPreferences(SharedPreferences sharedPreferences) { this.sharedPreferences = sharedPreferences; }
    public void setEditor(SharedPreferences.Editor editor) { this.editor = editor; }

    public void SaveLoginData(String username, String password){
        editor.putBoolean("saveLogIn",true);
        editor.putString("username",username);
        editor.putString("password",password);
        editor.commit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
