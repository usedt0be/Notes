package com.example.notes.di

import android.app.Application
import com.example.notes.data.AppDataBase
import com.example.notes.data.NoteDao
import com.example.notes.data.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent:: class)
class AppModule {

    @Singleton
    @Provides
    fun provideNoteRepository(
        noteDao:NoteDao
    ): NoteRepository {
        return NoteRepository(noteDao = noteDao)
    }

    @Singleton
    @Provides
    fun provideNoteDao(appDataBase: AppDataBase): NoteDao{
        return appDataBase.noteDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDataBase {
        return AppDataBase.getInstance(context = app)
    }


}