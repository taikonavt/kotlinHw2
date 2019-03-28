package com.example.maxim.kotlinhw2.data

import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.data.provider.RemoteDataProvider

class Repository(private val remoteProvider: RemoteDataProvider) {

//    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
    fun deleteNote(noteId: String) = remoteProvider.deleteNote(noteId)
}