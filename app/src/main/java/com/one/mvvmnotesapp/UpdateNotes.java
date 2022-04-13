package com.one.mvvmnotesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.one.mvvmnotesapp.databinding.ActivityUpdateNotesBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateNotes extends AppCompatActivity {

    String title, priority, notes, date;
    int id;
    ActivityUpdateNotesBinding binding;
    NotesViewModel notesViewModel;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_update_notes);


        backBtn = findViewById(R.id.backBtn_updateNotes);

        //Get Intent
        id = getIntent().getIntExtra("id",0);
        title = getIntent().getStringExtra("title");
        priority = getIntent().getStringExtra("priority");
        notes = getIntent().getStringExtra("notes");
        date = getIntent().getStringExtra("date");

        //View Model
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        //Set Data from Intent
        binding.titleUpdateNotes.getEditText().setText(title);
        binding.notesUpdateNotes.getEditText().setText(notes);
        if(priority.equals("1")){
            binding.highPriorityUpdateNotes.setImageResource(R.drawable.ic_tick);
            binding.mediumPriorityUpdateNotes.setImageResource(0);
            binding.lowPriorityUpdateNotes.setImageResource(0);
            binding.priorityLabelUpdateNotes.setText("High Priority");
        }else if(priority.equals("2")){
            binding.highPriorityUpdateNotes.setImageResource(0);
            binding.mediumPriorityUpdateNotes.setImageResource(R.drawable.ic_tick);
            binding.lowPriorityUpdateNotes.setImageResource(0);
            binding.priorityLabelUpdateNotes.setText("Medium Priority");
        }else{
            binding.highPriorityUpdateNotes.setImageResource(0);
            binding.mediumPriorityUpdateNotes.setImageResource(0);
            binding.lowPriorityUpdateNotes.setImageResource(R.drawable.ic_tick);
            binding.priorityLabelUpdateNotes.setText("Low Priority");
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateNotes.super.onBackPressed();
            }
        });

        //Get Data From User
        binding.highPriorityUpdateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.highPriorityUpdateNotes.setImageResource(R.drawable.ic_tick);
                binding.mediumPriorityUpdateNotes.setImageResource(0);
                binding.lowPriorityUpdateNotes.setImageResource(0);
                binding.priorityLabelUpdateNotes.setText("High Priority");
                priority = "1";
            }
        });
        binding.mediumPriorityUpdateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.highPriorityUpdateNotes.setImageResource(0);
                binding.mediumPriorityUpdateNotes.setImageResource(R.drawable.ic_tick);
                binding.lowPriorityUpdateNotes.setImageResource(0);
                binding.priorityLabelUpdateNotes.setText("Medium Priority");
                priority = "2";
            }
        });
        binding.lowPriorityUpdateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.highPriorityUpdateNotes.setImageResource(0);
                binding.mediumPriorityUpdateNotes.setImageResource(0);
                binding.lowPriorityUpdateNotes.setImageResource(R.drawable.ic_tick);
                binding.priorityLabelUpdateNotes.setText("Low Priority");
                priority = "3";
            }
        });
        binding.saveBtnUpdateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleValidator()) {
                    title = binding.titleUpdateNotes.getEditText().getText().toString();
                    notes = binding.notesUpdateNotes.getEditText().getText().toString();
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
                    date = dateformat.format(c.getTime());
                    updateData(title, priority, notes, date);
                }
            }
        });
        binding.deleteBtnUpdateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(UpdateNotes.this);
                View bottomsheetView = LayoutInflater.from(UpdateNotes.this)
                        .inflate(R.layout.delete_bottomsheet,(ConstraintLayout)findViewById(R.id.bottomsheet_layout));

                bottomSheetDialog.setContentView(bottomsheetView);
                MaterialButton yes, no;
                yes = bottomsheetView.findViewById(R.id.yes_deleteBottomSheet);
                no = bottomsheetView.findViewById(R.id.no_deleteBottomSheet);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        notesViewModel.deleteNotes(id);
                        bottomSheetDialog.dismiss();
                        finish();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.show();

            }
        });
    }

    private boolean titleValidator() {
        if (binding.titleUpdateNotes.getEditText().getText().toString().isEmpty()) {
            binding.titleUpdateNotes.setError("Title cannot be empty!");
            return false;
        } else
            binding.titleUpdateNotes.setError(null);
            binding.titleUpdateNotes.setErrorEnabled(false);
            return true;
    }

    private void updateData(String title, String priority, String notes, String date) {
        NotesEntity notesEntity = new NotesEntity();
        notesEntity.id = id;
        notesEntity.notesTitle = title;
        notesEntity.notesPriority = priority;
        notesEntity.notes = notes;
        notesEntity.notesDate = date;
        notesViewModel.updateNotes(notesEntity);
        Toast.makeText(this, "Update Successfully...", Toast.LENGTH_SHORT).show();
        finish();
    }
}