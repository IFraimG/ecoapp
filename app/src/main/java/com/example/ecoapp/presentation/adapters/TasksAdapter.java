package com.example.ecoapp.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoapp.data.models.Task;
import com.example.ecoapp.R;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private List<Task> taskList;
    public TasksAdapter(List<Task> taskList){
        this.taskList = taskList;
    }
    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_layout , parent , false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        holder.mName.setText(taskList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TasksViewHolder extends RecyclerView.ViewHolder{

        private TextView mName;
        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.task_tv);
        }
    }
}