package com.example.notes.data

import com.example.notes.model.NoteEntity
import kotlinx.coroutines.flow.Flow


class NoteRepository(private val noteDao: NoteDao) {

    fun getAllFlow(): Flow<List<NoteEntity>> = noteDao.getAllNotes()

    suspend fun addNote(note:NoteEntity){
        noteDao.insert(note = note)
    }

    suspend fun update(note: NoteEntity) {
        noteDao.updateNote(note = note)
    }


    suspend fun delete(note:NoteEntity){
        noteDao.deleteNote(note = note)
    }


}