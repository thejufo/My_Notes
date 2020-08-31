// Created by abdif on 8/27/2020

package com.glunode.notesapp.data

import com.glunode.notesapp.data.source.NotesDataSource
import com.glunode.notesapp.model.Note
import javax.inject.Inject

class DefaultNotesRepository @Inject constructor(
    private val firebaseDataSource: NotesDataSource
) : NotesRepository {

    override suspend fun addNote(note: Note) = firebaseDataSource.addNote(note)

    override fun observerNote(id: String) = firebaseDataSource.observerNote(id)

    override suspend fun updateNote(note: Note) = firebaseDataSource.updateNote(note)

    override fun observerNotes() = firebaseDataSource.observerNotes()

    override suspend fun deleteNote(id: String) = firebaseDataSource.deleteNote(id)

    override suspend fun deleteAllNotes() = firebaseDataSource.deleteAllNotes()
}