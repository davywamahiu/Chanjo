package com.example.desagu;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {
    View view;
    Context mContext;
    List<Notification> notificationList;

    public NotificationsAdapter(Context mContext, List<Notification> notificationList) {
        this.mContext = mContext;
        this.notificationList = notificationList;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       view = LayoutInflater.from(mContext).inflate(R.layout.notifications_layout,parent,false);

        NotificationViewHolder notificationViewHolder = new NotificationViewHolder(view);

        return notificationViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(NotificationViewHolder holder, final int position) {

        Notification notification = notificationList.get(position);
        holder.textViewTitle.setText(notification.getTitle());
        holder.textViewDescription.setText(notification.getDescription());
        holder.textViewDate.setText(notification.getDate());
        holder.textViewBy.setText(notification.getBy());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext,noti_description.class);
                i.putExtra("brian",notificationList.get(position).getDescription());
                i.putExtra("brian1",notificationList.get(position).getTitle());
                i.putExtra("brian2",notificationList.get(position).getDate());
                i.putExtra("brian3",notificationList.get(position).getBy());
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {

        return notificationList.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle,textViewDescription,textViewDate,textViewBy;
        public NotificationViewHolder(View itemView) {
            super(itemView);
            textViewTitle =itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewBy = itemView.findViewById(R.id.textViewBy);


        }
    }
}