package com.one.mvvmnotesapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesRepository {

    public NotesDao notesDao;
    public LiveData<List<NotesEntity>> getAllNotes;
    public LiveData<List<NotesEntity>> getHighToLow;
    public LiveData<List<NotesEntity>> getLowToHigh;

    public NotesRepository(Application application) {
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        notesDao = database.notesDao();
        getAllNotes = notesDao.getAllNotes();
        getHighToLow = notesDao.getHighToLow();
        getLowToHigh = notesDao.getLowToHigh();
    }

    void insertNotes(NotesEntity notesEntity) {
        notesDao.insertNotes(notesEntity);
    }

    void deleteNotes(int id) {
        notesDao.deleteNotes(id);
    }

    void updateNotes(NotesEntity notesEntity) {
        notesDao.updateNotes(notesEntity);
    }

}
