package com.example.desagu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.MyViewHolder>{

    private Context mContext;
    private CustomItemClickListener clickListener;
    private ArrayList<String> titleList;

    public TitleAdapter(Context mContext, CustomItemClickListener clickListener, ArrayList<String> titleList) {
        this.mContext = mContext;
        this.clickListener = clickListener;
        this.titleList = titleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.title_layout,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(view,viewHolder.getPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.titleText.setText(titleList.get(position).replace("_"," "));
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText=(TextView) itemView.findViewById(R.id.title_text);

        }
    }
}
