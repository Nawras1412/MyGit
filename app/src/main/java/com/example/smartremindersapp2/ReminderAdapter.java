package com.example.smartremindersapp2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ramindViewHolder> {
    private ArrayList<remindres_view> mRemind_view_list;
    private String username;
    private Context mcontext;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }


    public static class ramindViewHolder extends RecyclerView.ViewHolder {
        public TextView mhour;
        public TextView mminutes;
        public Switch mSwitch;
        public TextView mtitle;
        public ImageView DeleteB;
        public ImageView EditB;

        public ramindViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mhour = itemView.findViewById(R.id.hours);
            mminutes = itemView.findViewById(R.id.minutes);
            mtitle = itemView.findViewById(R.id.title2);
            mSwitch = itemView.findViewById(R.id.Switch);
            DeleteB = itemView.findViewById(R.id.DeleteButton);
            EditB = itemView.findViewById(R.id.EditButton);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            DeleteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }


    public ReminderAdapter(Context context, ArrayList<remindres_view> alarm_view_list, String username) {
        mRemind_view_list = alarm_view_list;
        this.username = username;
        mcontext = context;
    }

    @Override
    public ramindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item2, parent, false);
        ramindViewHolder evh = new ramindViewHolder(vi, mlistener);
        return evh;
    }


    @Override
    public void onBindViewHolder(ramindViewHolder holder, int position) {
        remindres_view currentReminer = mRemind_view_list.get(position);
        System.out.println(currentReminer.getMassege());
        // holder.mtitle.setText(currentReminer.getDate());
        // holder.mminutes.setText((CharSequence) currentReminer.getDate());
        holder.mhour.setText((currentReminer.getMassege()));
        System.out.println(username);
        System.out.println(currentReminer.getKey());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("reminder_list").child(currentReminer.getKey());


        holder.DeleteB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ref.removeValue();
                mRemind_view_list.remove(position);
                if(mRemind_view_list.isEmpty())
                    HomePage.setInstruction(0);
                notifyDataSetChanged();
            }
        });
//        holder.EditB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                ref.removeValue();
//                Intent intent = new Intent(mcontext, Reminder.class);
//                intent.putExtra("User Name", username);
//                mcontext.startActivity(intent);
//            }
//        });

    }


    @Override
    public int getItemCount() {
        return mRemind_view_list.size();
    }

}






//package com.example.smartremindersapp2;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.Switch;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.ArrayList;
//
//
//public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ramindViewHolder> {
//    private ArrayList<remindres_view> mRemind_view_list;
//    private String username;
//    private Context mcontext;
//    private OnItemClickListener mlistener;
//
//    public interface OnItemClickListener {
//        void onItemClick(int position);
//        void onDeleteClick(int position);
//    }
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        mlistener = listener;
//    }
//
//
//    public static class ramindViewHolder extends RecyclerView.ViewHolder{
//        public TextView mhour;
//        public TextView mminutes;
//        public Switch mSwitch;
//        public TextView mdays_date;
//        public ImageView DeleteB;
//        public ImageView EditB;
//        public ramindViewHolder(View itemView, final OnItemClickListener listener) {
//            super(itemView);
//            mhour=itemView.findViewById(R.id.hours);
//            mminutes=itemView.findViewById(R.id.minutes);
//            mdays_date=itemView.findViewById(R.id.date_days);
//            mSwitch=itemView.findViewById(R.id.Switch);
//            DeleteB=itemView.findViewById(R.id.DeleteButton);
//            EditB=itemView.findViewById(R.id.EditButton);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });
//            DeleteB.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onDeleteClick(position);
//                        }
//                    }
//                }
//            });
//        }
//    }
//
//
//    public ReminderAdapter(Context context, ArrayList<remindres_view> alarm_view_list, String username){
//        mRemind_view_list =alarm_view_list;
//        this.username=username;
//        mcontext = context;
//    }
//    @Override
//    public ramindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View vi= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_alarm_view,parent,false);
//        ramindViewHolder evh=new ramindViewHolder(vi,mlistener);
//        return evh;
//    }
//
//
//
//    @Override
//    public void onBindViewHolder(ramindViewHolder holder, int position) {
//        remindres_view currentReminer= mRemind_view_list.get(position);
//        System.out.println(currentReminer.getMassege());
//        holder.mdays_date.setText(currentReminer.getKey());
//       // holder.mminutes.setText((CharSequence) currentReminer.getDate());
//        holder.mhour.setText((currentReminer.getDiscription()));
//        System.out.println(username);
//        System.out.println(currentReminer.getKey());
//        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("reminder_list").child(currentReminer.getKey());
//
//
//        holder.DeleteB.setOnClickListener(new View.OnClickListener() {
//
//           @Override
//           public void onClick(View v) {
//               ref.removeValue();
//               mRemind_view_list.remove(position);
//               notifyDataSetChanged();
//           }});
////        holder.EditB.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v){
////                ref.removeValue();
////                Intent intent = new Intent(mcontext, Reminder.class);
////                intent.putExtra("User Name", username);
////                mcontext.startActivity(intent);
////            }
////        });
//
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return mRemind_view_list.size();
//    }
//}


//package com.example.smartremindersapp2;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.Switch;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.ArrayList;
//
//
//public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ramindViewHolder> {
//    private ArrayList<remindres_view> mRemind_view_list;
//    private String username;
//    private Context mcontext;
//    private OnItemClickListener mlistener;
//
//    public interface OnItemClickListener {
//        void onItemClick(int position);
//        void onDeleteClick(int position);
//    }
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        mlistener = listener;
//    }
//
//
//    public static class ramindViewHolder extends RecyclerView.ViewHolder{
//        public TextView mhour;
//        public TextView mminutes;
//        public Switch mSwitch;
//        public TextView mdays_date;
//        public ImageView DeleteB;
//        public ImageView EditB;
//        public ramindViewHolder(View itemView, final OnItemClickListener listener) {
//            super(itemView);
//            mhour=itemView.findViewById(R.id..hours);
//            mminutes=itemView..findViewById(R.id.minutes);
//            mdays_date=itemView.findViewById(R.id.date_days);
//            mSwitch=itemView.findViewById(R.id.Switch);
//            DeleteB=itemView.findViewById(R.id.DeleteButton);
//            EditB=itemView.findViewById(R.id.EditButton);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });
//            DeleteB.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onDeleteClick(position);
//                        }
//                    }
//                }
//            });
//        }
//    }
//
//
//    public ReminderAdapter(Context context, ArrayList<remindres_view> alarm_view_list, String username){
//        mRemind_view_list =alarm_view_list;
//        this.username=username;
//        mcontext = context;
//    }
//    @Override
//    public ramindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View vi= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_alarm_view,parent,false);
//        ramindViewHolder evh=new ramindViewHolder(vi,mlistener);
//        return evh;
//    }
//
//
//
//    @Override
//    public void onBindViewHolder(ramindViewHolder holder, int position) {
//        remindres_view currentReminer= mRemind_view_list.get(position);
//        System.out.println(currentReminer.getMassege());
//        holder.mdays_date.setText(currentReminer.getKey());
//       // holder.mminutes.setText((CharSequence) currentReminer.getDate());
//        holder.mhour.setText((currentReminer.getDiscription()));
//        System.out.println(username);
//        System..out.println(currentReminer.getKey());
//        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("reminder_list").child(currentReminer.getKey());
//
//
//        holder.DeleteB.setOnClickListener(new View.OnClickListener() {
//
//           @Override
//           public void onClick(View v) {
//               ref.removeValue();
//               mRemind_view_list.remove(position);
//               notifyDataSetChanged();
//           }});
////        holder.EditB.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v){
////                ref.removeValue();
////                Intent intent = new Intent(mcontext, Reminder.class);
////                intent.putExtra("User Name", username);
////                mcontext.startActivity(intent);
////            }
////        });
//
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return mRemind_view_list.size();
//    }
//}













//package com.example.smartremindersapp2;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.Switch;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.ArrayList;
//
//
//public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ramindViewHolder> {
//    private ArrayList<remindres_view> mRemind_view_list;
//    private String username;
//    private Context mcontext;
//    private OnItemClickListener mlistener;
//
//    public interface OnItemClickListener {
//        void onItemClick(int position);
//        void onDeleteClick(int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        mlistener = listener;
//    }
//
//
//    public static class ramindViewHolder extends RecyclerView.ViewHolder {
//        public TextView mhour;
//        public TextView mminutes;
//        public Switch mSwitch;
//        public TextView mtitle;
//        public ImageView DeleteB;
//        public ImageView EditB;
//
//        public ramindViewHolder(View itemView, final OnItemClickListener listener) {
//            super(itemView);
//            mhour = itemView.findViewById(R.id.hours);
//            mminutes = itemView.findViewById(R.id.minutes);
//            mtitle = itemView.findViewById(R.id.title2);
//            mSwitch = itemView.findViewById(R.id.Switch);
//            DeleteB = itemView.findViewById(R.id.DeleteButton);
//            EditB = itemView.findViewById(R.id.EditButton);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });
//            DeleteB.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onDeleteClick(position);
//                        }
//                    }
//                }
//            });
//        }
//    }
//
//
//    public ReminderAdapter(Context context, ArrayList<remindres_view> alarm_view_list, String username) {
//        mRemind_view_list = alarm_view_list;
//        this.username = username;
//        mcontext = context;
//    }
//
//    @Override
//    public ramindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item2, parent, false);
//        ramindViewHolder evh = new ramindViewHolder(vi, mlistener);
//        return evh;
//    }
//
//
//    @Override
//    public void onBindViewHolder(ramindViewHolder holder, int position) {
//        remindres_view currentReminer = mRemind_view_list.get(position);
//        System.out.println(currentReminer.getMassege());
//        // holder.mtitle.setText(currentReminer.getDate());
//        // holder.mminutes.setText((CharSequence) currentReminer.getDate());
//        holder.mhour.setText((currentReminer.getMassege()));
//        System.out.println(username);
//        System.out.println(currentReminer.getKey());
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("reminder_list").child(currentReminer.getKey());
//
//
//        holder.DeleteB.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                ref.removeValue();
//                mRemind_view_list.remove(position);
//                if(mRemind_view_list.isEmpty())
//                    HomePage.setInstruction(0);
//                notifyDataSetChanged();
//            }
//        });
////        holder.EditB.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v){
////                ref.removeValue();
////                Intent intent = new Intent(mcontext, Reminder.class);
////                intent.putExtra("User Name", username);
////                mcontext.startActivity(intent);
////            }
////        });
//
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return mRemind_view_list.size();
//    }
//
//}
//
//
//
//
//
//
////package com.example.smartremindersapp2;
////
////import android.content.Context;
////import android.content.Intent;
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.ViewGroup;
////import android.widget.ImageView;
////import android.widget.Switch;
////import android.widget.TextView;
////
////import androidx.recyclerview.widget.RecyclerView;
////
////import com.google.firebase.database.DatabaseReference;
////import com.google.firebase.database.FirebaseDatabase;
////
////import java.util.ArrayList;
////
////
////public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ramindViewHolder> {
////    private ArrayList<remindres_view> mRemind_view_list;
////    private String username;
////    private Context mcontext;
////    private OnItemClickListener mlistener;
////
////    public interface OnItemClickListener {
////        void onItemClick(int position);
////        void onDeleteClick(int position);
////    }
////    public void setOnItemClickListener(OnItemClickListener listener) {
////        mlistener = listener;
////    }
////
////
////    public static class ramindViewHolder extends RecyclerView.ViewHolder{
////        public TextView mhour;
////        public TextView mminutes;
////        public Switch mSwitch;
////        public TextView mdays_date;
////        public ImageView DeleteB;
////        public ImageView EditB;
////        public ramindViewHolder(View itemView, final OnItemClickListener listener) {
////            super(itemView);
////            mhour=itemView.findViewById(R.id.hours);
////            mminutes=itemView.findViewById(R.id.minutes);
////            mdays_date=itemView.findViewById(R.id.date_days);
////            mSwitch=itemView.findViewById(R.id.Switch);
////            DeleteB=itemView.findViewById(R.id.DeleteButton);
////            EditB=itemView.findViewById(R.id.EditButton);
////            itemView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    if (listener != null) {
////                        int position = getAdapterPosition();
////                        if (position != RecyclerView.NO_POSITION) {
////                            listener.onItemClick(position);
////                        }
////                    }
////                }
////            });
////            DeleteB.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    if (listener != null) {
////                        int position = getAdapterPosition();
////                        if (position != RecyclerView.NO_POSITION) {
////                            listener.onDeleteClick(position);
////                        }
////                    }
////                }
////            });
////        }
////    }
////
////
////    public ReminderAdapter(Context context, ArrayList<remindres_view> alarm_view_list, String username){
////        mRemind_view_list =alarm_view_list;
////        this.username=username;
////        mcontext = context;
////    }
////    @Override
////    public ramindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////        View vi= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_alarm_view,parent,false);
////        ramindViewHolder evh=new ramindViewHolder(vi,mlistener);
////        return evh;
////    }
////
////
////
////    @Override
////    public void onBindViewHolder(ramindViewHolder holder, int position) {
////        remindres_view currentReminer= mRemind_view_list.get(position);
////        System.out.println(currentReminer.getMassege());
////        holder.mdays_date.setText(currentReminer.getKey());
////       // holder.mminutes.setText((CharSequence) currentReminer.getDate());
////        holder.mhour.setText((currentReminer.getDiscription()));
////        System.out.println(username);
////        System.out.println(currentReminer.getKey());
////        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("reminder_list").child(currentReminer.getKey());
////
////
////        holder.DeleteB.setOnClickListener(new View.OnClickListener() {
////
////           @Override
////           public void onClick(View v) {
////               ref.removeValue();
////               mRemind_view_list.remove(position);
////               notifyDataSetChanged();
////           }});
//////        holder.EditB.setOnClickListener(new View.OnClickListener() {
//////            @Override
//////            public void onClick(View v){
//////                ref.removeValue();
//////                Intent intent = new Intent(mcontext, Reminder.class);
//////                intent.putExtra("User Name", username);
//////                mcontext.startActivity(intent);
//////            }
//////        });
////
////    }
////
////
////    @Override
////    public int getItemCount() {
////        return mRemind_view_list.size();
////    }
////}
