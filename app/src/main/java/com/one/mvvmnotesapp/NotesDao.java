package com.one.mvvmnotesapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM Notes_Database")
    LiveData<List<NotesEntity>> getAllNotes();

    @Insert
    void insertNotes(NotesEntity... notesEntity);

    @Query("DELETE FROM Notes_Database WHERE id=:id")
    void deleteNotes(int id);

    @Update
    void updateNotes(NotesEntity notesEntity);

}
