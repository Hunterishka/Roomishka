package com.example.room

import androidx.room.*
import com.example.room.model.Note

@Dao
interface NoteDao {
    @Insert
      fun insertNote(note: Note)

    @Delete
      fun deleteNote(note: Note)

    @Update
      fun updateNote(note: Note)

    @Query("SELECT * FROM note_table")
     fun getAllNotes(): List<Note>
}