package com.sue.notes.ui.write_note

import com.sue.notes.domain.model.Note

sealed class WriteNoteState {
    data class Success(
        val note: Note?
    ): WriteNoteState()

    object Save: WriteNoteState()
}