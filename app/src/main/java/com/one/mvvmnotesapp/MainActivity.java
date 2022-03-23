package com.one.mvvmnotesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.one.mvvmnotesapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NotesViewModel notesViewModel;
    NotesAdapter adapter;
    Boolean isGrid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        SharedPreferences isGridPreferences = getSharedPreferences("isGridPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = isGridPreferences.edit();
        isGrid = isGridPreferences.getBoolean("isGrid", true);

        binding.addNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNotes.class));
            }
        });

        if (isGrid) {
            binding.chanegLayout.setImageResource(R.drawable.ic_linearlayout);
            binding.notesRecyclerview.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        } else {
            binding.chanegLayout.setImageResource(R.drawable.ic_gridlayout);
            binding.notesRecyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        }

        binding.chanegLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isGrid) {
                    isGrid = false;
                    editor.putBoolean("isGrid", isGrid);
                    editor.apply();
                    binding.chanegLayout.setImageResource(R.drawable.ic_gridlayout);
                    binding.notesRecyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
                } else {
                    isGrid = true;
                    editor.putBoolean("isGrid", isGrid);
                    editor.apply();
                    binding.chanegLayout.setImageResource(R.drawable.ic_linearlayout);
                    binding.notesRecyclerview.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                }
            }
        });

        notesViewModel.getAllNotes.observe(this, notesEntities -> {
            adapter = new NotesAdapter(this, notesEntities);
            binding.notesRecyclerview.setAdapter(adapter);
        });

    }
}