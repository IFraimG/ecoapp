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

public class SavedAdviceAdapter extends RecyclerView.Adapter<SavedAdviceAdapter.SavedAdviceViewHolder> {

    private List<Advice> savedAdviceList;

    public SavedAdviceAdapter(List<Advice> savedAdviceList) {
        this.savedAdviceList = savedAdviceList;
    }

    @NonNull
    @Override
    public SavedAdviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_advice_layout, parent, false);
        return new SavedAdviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdviceViewHolder holder, int position) {
        holder.name.setText(savedAdviceList.get(position).getName());
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("guideID", savedAdviceList.get(position).getId());
            Navigation.findNavController(v).navigate(R.id.guideFragment, bundle);
        });
        String url = "https://test123-production-e08e.up.railway.app/image/" + savedAdviceList.get(position).getImage();
        Picasso.get().load(url).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return savedAdviceList.size();
    }

    public class SavedAdviceViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView mImageView;
        public SavedAdviceViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.saved_advice_name_tv);
            mImageView = itemView.findViewById(R.id.saved_advice_image);
        }
    }
}
