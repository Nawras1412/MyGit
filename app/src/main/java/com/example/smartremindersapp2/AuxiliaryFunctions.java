package com.example.smartremindersapp2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;


public class AuxiliaryFunctions extends AppCompatActivity {
    private static AuxiliaryFunctions MyAuxiliaryFunctions;
    private static DatabaseReference ref;

    private AuxiliaryFunctions(){}

    public static AuxiliaryFunctions getInstance(){
        if (MyAuxiliaryFunctions==null)
            MyAuxiliaryFunctions=new AuxiliaryFunctions();
        return MyAuxiliaryFunctions;
    }

    public void openNewPage(Context context, Class classX){
        Intent intent=new Intent(context, classX);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void openDialogD( androidx.fragment.app.FragmentManager SF){
        openDialog dialog=new openDialog();
        dialog.show(SF,"example");
    }

    public static void SetErrorOnTextView(TextView textView,String error){
        textView.setError(error);
        textView.requestFocus();
    }

    public static void MakeToast(Context context,String text){
        Toast.makeText(context, "inside", Toast.LENGTH_LONG).show();
    }



    public void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void closeDrawer(DrawerLayout drawerLayout) {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
        drawerLayout.closeDrawer(GravityCompat.START);
//        }
    }


    public static void logout(Activity activity,String userName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("are you sure you want to log out?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ref= FirebaseDatabase.getInstance().getReference().child("Users").child(userName);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap hashmap = new HashMap();
                        hashmap.put("status", "0");
                        ref.updateChildren(hashmap);
                        activity.finishAffinity();
                        Intent intent = new Intent(activity, login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("logout",true);
                        activity.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });



                //              Intent intent=new Intent(this, login.class);
//                startActivity(intent);


            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
