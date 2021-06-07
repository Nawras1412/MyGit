package com.example.smartremindersapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class AboutUs extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        drawerLayout=findViewById(R.id.drawer_layout);

    }


    public void ClickMenu(View view){
        HomePage.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        HomePage.closeDrawer(drawerLayout);
    }
    public void ClickHome(View view){
        HomePage.redirectActivity(this,HomePage.class, getIntent().getStringExtra("User Name"));
    }
    public void ClickDashboard(View view){
        HomePage.redirectActivity(this,Dashboard.class, getIntent().getStringExtra("User Name"));
    }
    public void ClickAboutUs(View view){

        recreate();
    }
    public void ClickLogout(View view){

        HomePage.logout(this);
    }
    @Override
    protected void onPause(){
        super.onPause();
        HomePage.closeDrawer(drawerLayout);
    }
}