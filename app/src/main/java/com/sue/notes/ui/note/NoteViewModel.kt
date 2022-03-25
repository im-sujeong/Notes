package com.sue.notes.ui.note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sue.notes.domain.model.Note
import com.sue.notes.domain.usecase.NoteUseCase
import com.sue.notes.domain.util.NoteOrder
import com.sue.notes.domain.util.OrderType
import com.sue.notes.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase
): BaseViewModel() {
    private val _state = MutableLiveData<NoteState>()
    val state: LiveData<NoteState> = _state

    private val notesFlow: MutableStateFlow<List<Note>> = MutableStateFlow(emptyList())
    private val keywordFlow: MutableStateFlow<String> = MutableStateFlow("")

    private var getNotesJob: Job? = null

    override fun fetchData(): Job = viewModelScope.launch{
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCase.getNotes(noteOrder)
            .combine(keywordFlow){ notes, keyword ->
                if( keyword.isBlank() ) {
                    notes
                }else {
                    notes.filter {
                        it.title.contains(keyword) || it.content.contains(keyword)
                    }
                }
            }
            .onEach {
                notesFlow.value = it

                setState(
                    NoteState.Success(
                        it,
                        noteOrder
                    )
                )
            }
            .launchIn(viewModelScope)
    }

    fun searchNote(keyword: String) = viewModelScope.launch{
        keywordFlow.emit(keyword)
    }

    fun fixNote(note: Note) = viewModelScope.launch{
        noteUseCase.insertNote(
            note.copy(
                isFixed = !note.isFixed
            )
        )
    }

    fun orderNotes(noteOrder: NoteOrder) {
        state.value?.let {
            if( it is NoteState.Success ) {
                getNotes(
                    noteOrder.copy(
                        orderType = it.noteOrder.orderType
                    )
                )
            }
        }
    }

    fun orderNotes(isAscending: Boolean) {
        state.value?.let {
            if( it is NoteState.Success ) {
                getNotes(
                    it.noteOrder.copy(
                        orderType = if(isAscending) {
                            OrderType.Ascending
                        }else {
                            OrderType.Descending
                        }
                    )
                )
            }
        }
    }

    fun delete(note: Note) = viewModelScope.launch{
        noteUseCase.deleteNote(note)
    }

    private fun setState(state: NoteState) {
        _state.value = state
    }
}