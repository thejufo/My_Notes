// Created by abdif on 8/28/2020

package com.glunode.notesapp.ui.noteeditor

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glunode.notesapp.data.NotesRepository
import com.glunode.notesapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteEditorViewModel @ViewModelInject constructor(private val notesRepository: NotesRepository) :
    ViewModel() {

    fun saveNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.addNote(note)
        }
    }
}