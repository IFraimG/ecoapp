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

import com.example.ecoapp.data.models.Search;
import com.example.ecoapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Search> searchList;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void OnItemClick(Search search);
    }


    public SearchAdapter(List<Search> searchList) {
        this.searchList = searchList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.found_layout, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.name.setText(searchList.get(position).getName());
        String url = "http://178.21.8.29:8080/image/" + searchList.get(position).getImage();
        Picasso.get().load(url).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView mImageView;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.found_name_tv);
            mImageView = itemView.findViewById(R.id.found_image);

            itemView.setOnClickListener(View -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(searchList.get(position));
                    }
                }
            });
        }
    }
}
