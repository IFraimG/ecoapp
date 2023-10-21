package com.example.ecoapp.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.R;
import com.example.ecoapp.data.models.User;

import java.util.List;

public class UserScoresAdapter extends RecyclerView.Adapter<UserScoresAdapter.UserScoresViewHolder> {
    private List<User> userScoresList;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String scores);
    }


    public UserScoresAdapter(List<User> userScoresList) {
        this.userScoresList = userScoresList;
    }

    @NonNull
    @Override
    public UserScoresAdapter.UserScoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_scores_item, parent, false);
        return new UserScoresAdapter.UserScoresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserScoresViewHolder holder, int position) {
        holder.name.setText(userScoresList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return userScoresList.size();
    }

    public class UserScoresViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private EditText input;

        public UserScoresViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.users_scores_name);
            input = itemView.findViewById(R.id.users_scores_input);

            input.setOnEditorActionListener((textView, actionId, keyEvent) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position, input.getText().toString());
                        }
                    }

                    return true;
                }

                return false;
            });
        }
    }
}
