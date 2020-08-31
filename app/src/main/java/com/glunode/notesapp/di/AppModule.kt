// Created by abdif on 8/28/2020

package com.glunode.notesapp.di

import com.glunode.notesapp.data.DefaultNotesRepository
import com.glunode.notesapp.data.NotesRepository
import com.glunode.notesapp.data.source.NotesDataSource
import com.glunode.notesapp.data.source.firebase.NotesFirebaseDataSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideNotesFirebaseDataSource(database: FirebaseFirestore): NotesDataSource {
        return NotesFirebaseDataSource(database)
    }

    @Provides
    @Singleton
    fun provideNotesRepository(dataSource: NotesDataSource): NotesRepository {
        return DefaultNotesRepository(dataSource)
    }
}