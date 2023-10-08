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

import com.example.ecoapp.data.models.EventCustom;
import com.example.ecoapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.NearbyViewHolder> {

    private List<EventCustom> nearbyList;

    public NearbyAdapter(List<EventCustom> nearbyList) {
        this.nearbyList = nearbyList;
    }

    @NonNull
    @Override
    public NearbyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_layout , parent , false);
        return new NearbyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NearbyViewHolder holder, int position) {
        holder.mName.setText(nearbyList.get(position).getTitle());

        String url = "https://test123-production-e08e.up.railway.app/image/" + nearbyList.get(position).getPhoto();
        Picasso.get().load(url).into(holder.mImageview);

        holder.cardView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", nearbyList.get(position).getEventID());

            Navigation.findNavController(v).navigate(R.id.eventFragment, bundle);
        });
    }

    public void updateAdverts(List<EventCustom> events) {
        try {
            this.nearbyList.addAll(events);

            notifyDataSetChanged();
        } catch (NullPointerException err) {
            err.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return nearbyList.size();
    }

    public class NearbyViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageview;
        private TextView mName;
        private CardView cardView;

        public NearbyViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageview = itemView.findViewById(R.id.nearby_image);
            mName = itemView.findViewById(R.id.nearby_name_tv);
            cardView = itemView.findViewById(R.id.nearby_item);
        }
    }
}