package com.sue.notes.ui.detail_note

import com.sue.notes.domain.model.Note

sealed class DetailNoteState {
    data class Success(
        val note: Note?
    ): DetailNoteState()
}
