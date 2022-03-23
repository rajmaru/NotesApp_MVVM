package com.one.mvvmnotesapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    public NotesRepository repository;
    public LiveData<List<NotesEntity>> getAllNotes;

    public NotesViewModel(Application application) {
        super(application);
        repository = new NotesRepository(application);
        getAllNotes = repository.getAllNotes;
    }

    public void insertNotes(NotesEntity notesEntity) {
        repository.insertNotes(notesEntity);
    }

    public void deleteNotes(int id) {
        repository.deleteNotes(id);
    }

    public void updateNotes(NotesEntity notesEntity) {
        repository.updateNotes(notesEntity);
    }
}
