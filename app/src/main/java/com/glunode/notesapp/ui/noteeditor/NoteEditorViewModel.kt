// Created by abdif on 8/28/2020

package com.glunode.notesapp.ui.noteeditor

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glunode.notesapp.data.NotesRepository
import com.glunode.notesapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteEditorViewModel @ViewModelInject constructor(private val notesRepository: NotesRepository) :
    ViewModel() {

    private val _noteState = MutableLiveData(NOTE_STATE_IDLE)
    val noteState: LiveData<Int>
        get() = _noteState

    fun saveNote(note: Note) {
        _noteState.postValue(NOTE_STATE_SAVING)
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.addNote(note)
            _noteState.postValue(NOTE_STATE_SAVED)
        }
    }

    companion object {

        const val NOTE_STATE_IDLE = 0

        const val NOTE_STATE_SAVING = 1

        const val NOTE_STATE_SAVED = 2
    }
}