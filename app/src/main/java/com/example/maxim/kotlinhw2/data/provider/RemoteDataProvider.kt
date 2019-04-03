package com.example.maxim.kotlinhw2.data.provider

import android.arch.lifecycle.LiveData
import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.data.model.Result
import com.example.maxim.kotlinhw2.data.model.User
import kotlinx.coroutines.channels.ReceiveChannel

interface RemoteDataProvider {

    fun subscribeToAllNotes() : ReceiveChannel<Result>
    suspend fun getNoteById(id: String) : Note
    suspend fun saveNote(note: Note) : Note
    suspend fun getCurrentUser(): User?
    suspend fun deleteNote(noteId: String)
}