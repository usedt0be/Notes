package com.example.notes.data

import androidx.room.*
import com.example.notes.model.NoteEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Insert
    fun insert(note: NoteEntity)

    @Delete
    fun deleteNote(note: NoteEntity)

    @Update
    fun updateNote(note: NoteEntity)
}