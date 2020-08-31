// Created by abdif on 8/27/2020

package com.glunode.notesapp.data

import androidx.lifecycle.LiveData
import com.glunode.notesapp.model.Note

interface NotesRepository {

    suspend fun addNote(note: Note): Boolean

    fun observerNote(id: String): LiveData<Note?>

    suspend fun updateNote(note: Note): Boolean

    fun observerNotes(): LiveData<List<Note>>

    suspend fun deleteNote(id: String) : Boolean

    suspend fun deleteAllNotes() : Boolean
}