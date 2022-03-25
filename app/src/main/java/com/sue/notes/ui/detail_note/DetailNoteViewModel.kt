package com.sue.notes.ui.detail_note

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
class DetailNoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
    private val savedStateHandle: SavedStateHandle
): BaseViewModel() {
    private val _state =  MutableLiveData<DetailNoteState>()
    val state: LiveData<DetailNoteState> = _state

    override fun fetchData() = viewModelScope.launch {
        val noteId = savedStateHandle.get<Int>("noteId")!!

        setState(
            DetailNoteState.Success(noteUseCase.getNoteById(noteId))
        )
    }

    fun getNote(): Note? =
        state.value?.let {
            if( it is DetailNoteState.Success ) {
                it.note
            }else {
                null
            }
        }


    private fun setState(state: DetailNoteState) {
        _state.value = state
    }
}