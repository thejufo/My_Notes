// Created by abdif on 8/28/2020

package com.glunode.notesapp.ui.noteslist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.glunode.notesapp.data.NotesRepository

class NotesListViewModel @ViewModelInject constructor(notesRepository: NotesRepository) :
    ViewModel() {

    val notesList = notesRepository.observerNotes()

    val isEmpty = Transformations.map(notesList) {
        it.isEmpty()
    }

    private val _navigateToEditor = MutableLiveData<Boolean>()
    val navigateToEditor: LiveData<Boolean>
        get() = _navigateToEditor

    fun navigateToEditor() {
        _navigateToEditor.value = true
    }

    fun navigateToEditorDone() {
        _navigateToEditor.value = false
    }
}