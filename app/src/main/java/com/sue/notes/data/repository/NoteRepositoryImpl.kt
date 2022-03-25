package com.sue.notes.data.repository

import com.sue.notes.data.db.NoteDao
import com.sue.notes.domain.model.Note
import com.sue.notes.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn

class NoteRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val noteDao: NoteDao
): NoteRepository {
    override fun getNotes(): Flow<List<Note>> =
        noteDao.getNotes()
            .flowOn(ioDispatcher)

    override suspend fun getNoteById(id: Int): Note? = noteDao.getNoteById(id)

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}