// Created by abdif on 8/28/2020

package com.glunode.notesapp.ui.notedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.glunode.notesapp.data.NotesRepository
import com.glunode.notesapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteDetailsViewModel constructor(private val notesRepository: NotesRepository, noteId: String) :
    ViewModel() {

    val note = notesRepository.observerNote(noteId)

    private val _navigateToEditor = MutableLiveData<Note>()
    val navigateToEditor: LiveData<Note>
        get() = _navigateToEditor

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.deleteNote(note.id)
        }
    }

    fun navigateToEditor(note: Note) {
        _navigateToEditor.value = note
    }

    fun navigateToEditorDone() {
        _navigateToEditor.value = null
    }

    class Factory(private val notesRepository: NotesRepository, private val noteId: String) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NoteDetailsViewModel(notesRepository, noteId) as T
        }
    }
}