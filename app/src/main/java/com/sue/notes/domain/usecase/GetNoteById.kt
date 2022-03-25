package com.sue.notes.domain.usecase

import com.sue.notes.domain.model.Note
import com.sue.notes.domain.repository.NoteRepository

class GetNoteById(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(noteId: Int): Note? =
        noteRepository.getNoteById(noteId)
}