package com.example.notes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val roomId: Int? = null,

    @ColumnInfo(name = "Note_text") val text : String

)