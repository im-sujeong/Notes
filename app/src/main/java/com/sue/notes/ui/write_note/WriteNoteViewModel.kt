package com.sue.notes.ui.write_note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sue.notes.domain.model.Note
import com.sue.notes.domain.usecase.NoteUseCase
import com.sue.notes.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteNoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val _state = MutableLiveData<WriteNoteState>()
    val state: LiveData<WriteNoteState> = _state

    override fun fetchData() = viewModelScope.launch {
        val note = savedStateHandle.get<Note>("note")

        setState(
            WriteNoteState.Success(note)
        )
    }

    fun saveNote(title: String, content: String) = viewModelScope.launch{
        noteUseCase.insertNote(
            note = state.value?.let {
                if( it is WriteNoteState.Success ) {
                    it.note?.copy(
                        title = title,
                        content = content
                    )
                }else {
                    null
                }
            } ?: Note(
                title = title,
                content = content,
                createdAt = System.currentTimeMillis()
            )
        )

        setState(WriteNoteState.Save)
    }

    private fun setState(state: WriteNoteState) {
        _state.value = state
    }
}