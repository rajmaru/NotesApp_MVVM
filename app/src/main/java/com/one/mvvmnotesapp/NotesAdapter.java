package com.one.mvvmnotesapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    Context context;
    List<NotesEntity> notesEntityList;

    public NotesAdapter(Context context, List<NotesEntity> notesEntityList) {
        this.context = context;
        this.notesEntityList = notesEntityList;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_sample_layout, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        NotesEntity notesEntity = notesEntityList.get(position);
        holder.title.setText(notesEntity.notesTitle);
        holder.notes.setText(notesEntity.notes);
        holder.date.setText(notesEntity.notesDate);
        if(notesEntity.notesPriority.equals("1")){
            holder.priority.setBackgroundColor(context.getResources().getColor(R.color.navy_dark));
        }else if(notesEntity.notesPriority.equals("2")){
            holder.priority.setBackgroundColor(context.getResources().getColor(R.color.navy_medium));
        }else{
            holder.priority.setBackgroundColor(context.getResources().getColor(R.color.navy_light));
        }
    }

    @Override
    public int getItemCount() {
        return notesEntityList.size();
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder{

        TextView title, notes, date;
        View priority;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_sampleLayout);
            notes = itemView.findViewById(R.id.notes_sampleLayout);
            date = itemView.findViewById(R.id.date_sampleLayout);
            priority = itemView.findViewById(R.id.priority_sampleLayout);
        }
    }
}
