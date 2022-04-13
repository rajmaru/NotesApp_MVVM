package com.one.mvvmnotesapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    MainActivity mainActivity;
    List<NotesEntity> notesEntityList;

    public NotesAdapter(MainActivity mainActivity, List<NotesEntity> notesEntityList) {
        this.mainActivity = mainActivity;
        this.notesEntityList = notesEntityList;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.notes_sample_layout, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        NotesEntity notesEntity = notesEntityList.get(position);
        holder.title.setText(notesEntity.notesTitle);
        holder.notes.setText(notesEntity.notes);
        String sDate = notesEntity.notesDate;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", Locale.ENGLISH);
        try {
            Date mDate = sdf.parse(sDate);
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.ENGLISH);
            if (mDate != null) {
                sDate = sdf2.format(mDate);
            }
            sDate = sDate.replace("PM", "pm").replace("AM", "am");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.date.setText(sDate);
        if(notesEntity.notesPriority.equals("1")){
            holder.priority.setBackgroundColor(mainActivity.getResources().getColor(R.color.high_priority_label));
        }else if(notesEntity.notesPriority.equals("2")){
            holder.priority.setBackgroundColor(mainActivity.getResources().getColor(R.color.medium_priority_label));
        }else{
            holder.priority.setBackgroundColor(mainActivity.getResources().getColor(R.color.low_priority_label));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, UpdateNotes.class);
                intent.putExtra("id", notesEntity.id);
                intent.putExtra("title", notesEntity.notesTitle);
                intent.putExtra("priority", notesEntity.notesPriority);
                intent.putExtra("notes", notesEntity.notes);
                intent.putExtra("date", notesEntity.notesDate);
                mainActivity.startActivity(intent);
            }
        });

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
