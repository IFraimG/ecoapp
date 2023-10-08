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

import com.example.ecoapp.data.models.Coming;
import com.example.ecoapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComingAdapter extends RecyclerView.Adapter<ComingAdapter.ComingViewHolder> {

    private List<Coming> comingList;
    public ComingAdapter(List<Coming> comingList){
        this.comingList = comingList;
    }
    @NonNull
    @Override
    public ComingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coming_layout , parent , false);
        return new ComingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComingViewHolder holder, int position) {
        holder.mName.setText(comingList.get(position).getName());

        String url = "https://test123-production-e08e.up.railway.app/image/" + comingList.get(position).getImage();
        Picasso.get().load(url).into(holder.mImageview);

        holder.cardView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", comingList.get(position).getId());
            Navigation.findNavController(v).navigate(R.id.eventFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return comingList.size();
    }

    public class ComingViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageview;
        private TextView mName;
        private CardView cardView;
        public ComingViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.coming_card_view_wrapper);
            mImageview = itemView.findViewById(R.id.coming_image);
            mName = itemView.findViewById(R.id.coming_name_tv);

        }
    }
}