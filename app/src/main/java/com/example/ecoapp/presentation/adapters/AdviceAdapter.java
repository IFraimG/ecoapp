package com.example.ecoapp.presentation.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.data.models.Advice;
import com.example.ecoapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdviceAdapter extends RecyclerView.Adapter<AdviceAdapter.AdviceViewHolder> {

    private List<Advice> adviceList;

    public AdviceAdapter(List<Advice> adviceList) {
        this.adviceList = adviceList;
    }

    @NonNull
    @Override
    public AdviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advice_layout, parent, false);
        return new AdviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdviceViewHolder holder, int position) {
        holder.name.setText(adviceList.get(position).getName());
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("guideID", adviceList.get(position).getId());
            Navigation.findNavController(v).navigate(R.id.guideFragment, bundle);
        });
        String url = "https://test123-production-e08e.up.railway.app/image/" + adviceList.get(position).getImage();
        Picasso.get().load(url).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return adviceList.size();
    }

    public class AdviceViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView mImageView;
        public AdviceViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.advice_name_tv);
            mImageView = itemView.findViewById(R.id.advice_image);
        }
    }
}
