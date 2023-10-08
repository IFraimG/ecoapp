package com.example.ecoapp.presentation.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.data.models.MyEvents;
import com.example.ecoapp.R;
import com.squareup.picasso.Picasso;

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

        String url = "https://test123-production-e08e.up.railway.app/image/" + myEventsList.get(position).getImage();
        Picasso.get().load(url).into(holder.mImageview);

        holder.cardView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", myEventsList.get(position).getId());
            Navigation.findNavController(v).navigate(R.id.eventFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return myEventsList.size();
    }

    public class MyEventsViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageview;
        private TextView mName;
        private CardView cardView;

        public MyEventsViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageview = itemView.findViewById(R.id.my_event_image);
            mName = itemView.findViewById(R.id.my_event_name_tv);
            cardView = itemView.findViewById(R.id.events_card_view_my);
        }
    }
}