package com.one.mvvmnotesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.one.mvvmnotesapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NotesViewModel notesViewModel;
    NotesAdapter adapter;
    ChipGroup chipgroup;
    boolean isGrid = true;
    int filterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        //ViewModel
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        //Grid Layout SharedPrefernces
        SharedPreferences isGridPreferences = getSharedPreferences("isGridPreferences", MODE_PRIVATE);
        SharedPreferences.Editor gridEditor = isGridPreferences.edit();
        isGrid = isGridPreferences.getBoolean("isGrid", true);

        //Filter ID SharedPrefernces
        SharedPreferences filterIdPreferences = getSharedPreferences("filterIdPreferences", MODE_PRIVATE);
        SharedPreferences.Editor filterIdEditor = filterIdPreferences.edit();
        filterId = filterIdPreferences.getInt("filterId", R.id.noFilter);

        //Default users Setting
        layout(isGrid);
        filter(filterId);

        //Add Notes
        binding.addNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNotes.class));
            }
        });

        //Change Layout
        binding.chanegLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isGrid) {
                    isGrid = false;
                    gridEditor.putBoolean("isGrid", isGrid);
                    gridEditor.apply();
                    layout(isGrid);
                } else {
                    isGrid = true;
                    gridEditor.putBoolean("isGrid", isGrid);
                    gridEditor.apply();
                    layout(isGrid);
                }
            }
        });

        //Filter Chip Group
        chipgroup = findViewById(R.id.chipGroup);
        chipgroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                for (int filterID : checkedIds) {
                    filterId = filterID;
                    filterIdEditor.putInt("filterId", filterId);
                    filterIdEditor.apply();
                    filter(filterID);
                }
            }
        });

    }

    //Layout Manager
    private void layout(boolean isGrid) {
        if (isGrid) {
            binding.chanegLayout.setImageResource(R.drawable.ic_linearlayout);
            binding.notesRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        } else {
            binding.chanegLayout.setImageResource(R.drawable.ic_gridlayout);
            binding.notesRecyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        }
    }

    //Filter
    private void filter(int filterId) {
        if (filterId == R.id.noFilter) {
            //No Filter
            notesViewModel.getAllNotes.observe(MainActivity.this, new Observer<List<NotesEntity>>() {
                @Override
                public void onChanged(List<NotesEntity> notesEntities) {
                    binding.noFilter.setChecked(true);
                    setadapter(notesEntities, isGrid);
                }
            });
        } else if (filterId == R.id.highToLow) {
            //High To Low
            notesViewModel.getHighToLow.observe(MainActivity.this, new Observer<List<NotesEntity>>() {
                @Override
                public void onChanged(List<NotesEntity> notesEntities) {
                    binding.highToLow.setChecked(true);
                    setadapter(notesEntities, isGrid);
                }
            });
        } else if (filterId == R.id.lowToHigh) {
            //Low To High
            notesViewModel.getLowToHigh.observe(MainActivity.this, new Observer<List<NotesEntity>>() {
                @Override
                public void onChanged(List<NotesEntity> notesEntities) {
                    binding.lowToHigh.setChecked(true);
                    setadapter(notesEntities, isGrid);
                }
            });
        }
    }

    //Set Adapter
    private void setadapter(List<NotesEntity> notesEntities, boolean isGrid) {
        if (isGrid) {
            binding.chanegLayout.setImageResource(R.drawable.ic_linearlayout);
            binding.notesRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        } else {
            binding.chanegLayout.setImageResource(R.drawable.ic_gridlayout);
            binding.notesRecyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        }
        adapter = new NotesAdapter(MainActivity.this, notesEntities);
        binding.notesRecyclerview.setAdapter(adapter);
    }


}