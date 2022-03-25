package com.sue.notes.domain.usecase

import com.sue.notes.domain.model.Note
import com.sue.notes.domain.repository.NoteRepository

class DeleteNote(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        noteRepository.deleteNote(note)
    }
}