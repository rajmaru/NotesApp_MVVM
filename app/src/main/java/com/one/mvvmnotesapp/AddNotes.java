package com.one.mvvmnotesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.one.mvvmnotesapp.databinding.ActivityAddNotesBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNotes extends AppCompatActivity {

    ActivityAddNotesBinding binding;
    String title, priority = "1", notes, date;
    NotesViewModel notesViewModel;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_notes);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        backBtn = findViewById(R.id.backBtn_addNotes);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNotes.super.onBackPressed();
            }
        });

        binding.highPriorityAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.highPriorityAddNotes.setImageResource(R.drawable.ic_tick);
                binding.mediumPriorityAddNotes.setImageResource(0);
                binding.lowPriorityAddNotes.setImageResource(0);
                binding.priorityLabelAddNotes.setText("High Priority");
                priority = "1";
            }
        });


        binding.mediumPriorityAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.highPriorityAddNotes.setImageResource(0);
                binding.mediumPriorityAddNotes.setImageResource(R.drawable.ic_tick);
                binding.lowPriorityAddNotes.setImageResource(0);
                binding.priorityLabelAddNotes.setText("Medium Priority");
                priority = "2";
            }
        });


        binding.lowPriorityAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.highPriorityAddNotes.setImageResource(0);
                binding.mediumPriorityAddNotes.setImageResource(0);
                binding.lowPriorityAddNotes.setImageResource(R.drawable.ic_tick);
                binding.priorityLabelAddNotes.setText("Low Priority");
                priority = "3";
            }
        });

        binding.saveBtnAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleValidator()) {
                    title = binding.titleAddNotes.getEditText().getText().toString();
                    notes = binding.notesAddNotes.getEditText().getText().toString();
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
                    date = dateformat.format(c.getTime());
                    insertData(title, priority, notes, date);
                }
            }
        });
    }

    private boolean titleValidator() {
        if (binding.titleAddNotes.getEditText().getText().toString().isEmpty()) {
            binding.titleAddNotes.setError("Title cannot be empty!");
            return false;
        } else
            binding.titleAddNotes.setError(null);
            binding.titleAddNotes.setErrorEnabled(false);
            return true;
    }

    private void insertData(String title, String priority, String notes, String date) {
        NotesEntity notesEntity = new NotesEntity();
        notesEntity.notesTitle = title;
        notesEntity.notesPriority = priority;
        notesEntity.notes = notes;
        notesEntity.notesDate = date;
        notesViewModel.insertNotes(notesEntity);
        Toast.makeText(this, "Saved Successfully...", Toast.LENGTH_SHORT).show();
        finish();
    }
}