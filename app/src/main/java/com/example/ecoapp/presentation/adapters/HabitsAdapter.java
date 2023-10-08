package com.example.ecoapp.presentation.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.data.models.Habit;
import com.example.ecoapp.R;

import java.io.InputStream;
import java.util.List;

public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.HabitsViewHolder> {

    private List<Habit> habitsList;

    public HabitsAdapter(List<Habit> habitsList){
        this.habitsList = habitsList;
    }

    @NonNull
    @Override
    public HabitsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habits_layout , parent , false);
        return new HabitsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitsViewHolder holder, int position) {
        holder.mName.setText(habitsList.get(position).getTitle());
        holder.circle.setOnClickListener(v -> {
            holder.circle.setImageResource(R.drawable.green_check);

        });
    }

    @Override
    public int getItemCount() {
        return habitsList.size();
    }

    public class HabitsViewHolder extends RecyclerView.ViewHolder{

        private TextView mName;
        private ImageView circle;

        public HabitsViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.habit_tv);
            circle = itemView.findViewById(R.id.habit_circle_check);
        }
    }
}