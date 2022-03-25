package com.sue.notes.domain.usecase

data class NoteUseCase(
    val getNotes: GetNotes,
    val insertNote: InsertNote,
    val deleteNote: DeleteNote,
    val getNoteById: GetNoteById
)
