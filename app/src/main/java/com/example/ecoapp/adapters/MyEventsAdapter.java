package com.example.ecoapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.models.MyEvents;
import com.example.ecoapp.R;

import java.util.List;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.MyEventsViewHolder> {

    private List<MyEvents> myEventsList;
    public MyEventsAdapter(List<MyEvents> myEventsList){
        this.myEventsList = myEventsList;
    }
    @NonNull
    @Override
    public MyEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_events_layout , parent , false);
        return new MyEventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventsViewHolder holder, int position) {
        holder.mName.setText(myEventsList.get(position).getName());
        holder.mImageview.setImageResource(myEventsList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return myEventsList.size();
    }

    public class MyEventsViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageview;
        private TextView mName;
        public MyEventsViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageview = itemView.findViewById(R.id.my_event_image);
            mName = itemView.findViewById(R.id.my_event_name_tv);
        }
    }
}