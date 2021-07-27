package com.example.smartremindersapp2;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addTodoList {
    private String UserName;
    private Dialog todoList_dialog;
    private EditText AddDescriptionEditText;
    private TextView title;
    private Button add_btn;
    private ImageButton cancel_btn;

    public addTodoList(String userName) { UserName=userName; }

    public void openDialog(boolean edit,reminders_view oldReminder,int position) {
        todoList_dialog=new Dialog(HomePage.getInstance());
        todoList_dialog.setContentView(R.layout.todo_list);
        add_btn = todoList_dialog.findViewById(R.id.addButton);
        cancel_btn = todoList_dialog.findViewById(R.id.cancel_Btn);
        title = todoList_dialog.findViewById(R.id.Title);
        AddDescriptionEditText=todoList_dialog.findViewById(R.id.descriptionTextView);

        if(edit){
            title.setText(oldReminder.getTitle());
            AddDescriptionEditText.setText(oldReminder.getDescription());
        }

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoList_dialog.cancel();
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit){
                    ReminderAdapter.getmRemind_view_list().remove(position);
                    DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").
                            child(UserName).child("reminder_list").child(oldReminder.getKey());
                    ref.removeValue();
                }
                Reminder reminder=new Reminder();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(UserName).child("reminder_list");
                DatabaseReference keyRef =ref.push();
                reminder.setKey(keyRef.getKey());
                reminder.setMyType("Todo List");
                reminder.setState(true);
                if(title.getText().toString().isEmpty())
                    reminder.setMessage("Todo list");
                else
                    reminder.setMessage(title.getText().toString().trim());
                reminder.setDescription(AddDescriptionEditText.getText().toString());
                keyRef.setValue(reminder);
                ArrayAdapter<CharSequence> KindsAdapter = ArrayAdapter.createFromResource(HomePage.getInstance(), R.array.kinds, android.R.layout.simple_spinner_item);
                KindsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                int spinnerPosition = KindsAdapter.getPosition("all");
                HomePage.getInstance().getRemindersKindSpinner().setSelection(spinnerPosition);
                HomePage.getInstance().get_all_reminders_by_kind("all");
                HomePage.getInstance().getmRecyclerView().setHasFixedSize(true);
                todoList_dialog.cancel();
            }
        });

        todoList_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        todoList_dialog.show();

    }

}
