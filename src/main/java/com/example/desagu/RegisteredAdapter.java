package com.example.desagu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RegisteredAdapter extends RecyclerView.Adapter<RegisteredAdapter.RegisteredViewHolder>{

    View view;
    Context mContext;
    List<Registered> registeredList;

    public RegisteredAdapter(Context mContext, List<Registered> registeredList) {
        this.mContext = mContext;
        this.registeredList = registeredList;
    }

    @Override
    public RegisteredViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.registered_layout,parent,false);
        RegisteredViewHolder registeredViewHolder = new RegisteredViewHolder(view);
        return registeredViewHolder;
    }

    @Override
    public void onBindViewHolder(RegisteredViewHolder holder, final int position) {
        final Registered registered = registeredList.get(position);
        holder.textViewFullName.setText(registered.getFullName());
        holder.textViewGender.setText(registered.getGender());
        holder.textViewBirtCert.setText(registered.getBirthCert());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent history = new Intent(mContext,HistoryActivity.class);
                history.putExtra("birthNumber",registeredList.get(position).getBirthCert());
                mContext.startActivity(history);
            }
        });

    }

    @Override
    public int getItemCount() {
        return registeredList.size();
    }

    class RegisteredViewHolder extends  RecyclerView.ViewHolder{
        TextView textViewFullName,textViewGender,textViewBirtCert;
        public RegisteredViewHolder(View itemView) {

            super(itemView);
            textViewFullName = itemView.findViewById(R.id.textViewFullName);
            textViewBirtCert = itemView.findViewById(R.id.textViewBirthCert);
            textViewGender = itemView.findViewById(R.id.textViewGender);
        }
    }
}
