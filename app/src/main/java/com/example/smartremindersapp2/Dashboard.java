package com.example.smartremindersapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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
      recreate();
    }
    public void ClickAboutUs(View view){
        HomePage.redirectActivity(this,AboutUs.class, getIntent().getStringExtra("User Name"));
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