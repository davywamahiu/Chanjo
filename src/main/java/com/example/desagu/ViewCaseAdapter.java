package com.example.desagu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewCaseAdapter extends RecyclerView.Adapter<ViewCaseAdapter.ViewCaseViewHolder>{
    View view;
    Context mcontext;
    List<ViewCase> viewCaseList;

    public ViewCaseAdapter(Context mcontext, List<ViewCase> viewCaseList) {
        this.mcontext = mcontext;
        this.viewCaseList = viewCaseList;
    }

    @Override
    public ViewCaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mcontext).inflate(R.layout.cases_layout,parent,false);
        ViewCaseViewHolder viewCaseViewHolder = new ViewCaseViewHolder(view);

        return viewCaseViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewCaseViewHolder holder, int position) {
        ViewCase viewCase = viewCaseList.get(position);
        holder.contact.setText(viewCase.getContact());
        holder.decriptionCase.setText(viewCase.getDescription());
        holder.titleCase.setText(viewCase.getTitle());
    }

    @Override
    public int getItemCount() {
        return viewCaseList.size();
    }

    class ViewCaseViewHolder extends RecyclerView.ViewHolder{

        TextView contact,titleCase,decriptionCase;
        public ViewCaseViewHolder(View itemView) {
            super(itemView);
            contact = itemView.findViewById(R.id.textViewCasePhone);
            titleCase =itemView.findViewById(R.id.textViewCaseTitle);
            decriptionCase  = itemView.findViewById(R.id.textViewCaseDesc);
        }
    }
}
