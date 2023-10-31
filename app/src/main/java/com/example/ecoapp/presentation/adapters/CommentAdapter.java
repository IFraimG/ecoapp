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

import com.example.ecoapp.data.models.Comment;
import com.example.ecoapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> commentList;
    public CommentAdapter(List<Comment> commentList){
        this.commentList = commentList;
    }
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout , parent , false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.mName.setText(commentList.get(position).getName());

        String url = "http://178.21.8.29:8080/image/" + commentList.get(position).getImage();
        Picasso.get().load(url).into(holder.mImageview);

        holder.cardView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", commentList.get(position).getId());
            Navigation.findNavController(v).navigate(R.id.eventFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private ImageView mProfileImageview;
        private TextView mProfileName;
        private TextView mContentTextView;
        private TextView mDate;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            mProfileImageview = itemView.findViewById(R.id.comment_profile_image);
            mProfileName = itemView.findViewById(R.id.comment_profile_name_tv);
            mContentTextView = itemView.findViewById(R.id.comment_content_tv);
            mDate = itemView.findViewById(R.id.comment_date);
        }
    }
}