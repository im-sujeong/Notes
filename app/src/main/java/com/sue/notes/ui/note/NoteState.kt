package com.sue.notes.ui.note

import com.sue.notes.domain.model.Note
import com.sue.notes.domain.util.NoteOrder
import com.sue.notes.domain.util.OrderType

sealed class NoteState{
    data class Success(
        val notes: List<Note>,
        val noteOrder: NoteOrder
    ): NoteState()
}
