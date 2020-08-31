// Created by abdif on 8/27/2020

package com.glunode.notesapp.data.source.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.glunode.notesapp.data.source.NotesDataSource
import com.glunode.notesapp.model.Note
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class NotesFirebaseDataSource @Inject constructor(firebaseDatabase: FirebaseFirestore) :
    NotesDataSource {

    private val notesCol = firebaseDatabase.collection(("notes"))

    override suspend fun addNote(note: Note) =
        try {
            notesCol.document(note.id).set(note).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    override fun observerNote(id: String): LiveData<Note?> {
        Timber.e("Observing note")
        val result = MutableLiveData<Note>()
        notesCol.document(id).addSnapshotListener { value, error ->
            val note = value?.toObject<Note>()
            result.postValue(note)
        }
        return result
    }

    override suspend fun updateNote(note: Note) =
        try {
            notesCol.document(note.id.toString()).set(note).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    override fun observerNotes(): LiveData<List<Note>> {
        val result = MutableLiveData<List<Note>>()
        notesCol.addSnapshotListener { value, error ->
            if (error == null) {
                val notes = value?.toObjects<Note>()
                result.postValue(notes)
            } else {
                Timber.e("error: ${error.message}")
            }
        }
        return result
    }

    override suspend fun deleteNote(id: String) =
        try {
            Timber.e("Deleting note $id")
            notesCol.document(id).delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    override suspend fun deleteAllNotes() =
        try {
            Timber.e("Deleting all notes")
            notesCol.get().await().documents.forEach {snapshot ->
                val id = snapshot.toObject(Note::class.java)?.id
                Timber.e("$id")
                id.takeIf { it != null }?.apply { deleteNote(this.toString()) }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
}