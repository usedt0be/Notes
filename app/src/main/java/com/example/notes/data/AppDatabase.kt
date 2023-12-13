package com.example.notes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 3, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private var INSTANCE: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context = context, AppDataBase::class.java, "notes_Db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as AppDataBase
        }
    }



}

