package com.example.maxim.kotlinhw2.data.provider

import android.arch.lifecycle.LiveData
import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.data.model.Result
import com.example.maxim.kotlinhw2.data.model.User

interface RemoteDataProvider {

    fun subscribeToAllNotes() : LiveData<Result>
    fun getNoteById(id: String) : LiveData<Result>
    fun saveNote(note: Note) : LiveData<Result>
    fun getCurrentUser(): LiveData<User?>
    fun deleteNote(noteId: String): LiveData<Result>
}