package com.sue.notes.di

import android.app.Application
import androidx.room.Room
import com.sue.notes.data.db.NoteDatabase
import com.sue.notes.data.repository.NoteRepositoryImpl
import com.sue.notes.domain.repository.NoteRepository
import com.sue.notes.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(
            Dispatchers.IO,
            db.noteDao
        )
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(noteRepository: NoteRepository): NoteUseCase {
        return NoteUseCase(
            getNotes = GetNotes(noteRepository),
            insertNote = InsertNote(noteRepository),
            deleteNote = DeleteNote(noteRepository),
            getNoteById = GetNoteById(noteRepository)
        )
    }
}