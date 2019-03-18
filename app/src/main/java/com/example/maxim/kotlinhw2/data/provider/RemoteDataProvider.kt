package com.example.maxim.kotlinhw2.data.provider

import android.arch.lifecycle.LiveData
import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.data.model.NoteResult

interface RemoteDataProvider {

    fun subscribeToAllNotes() : LiveData<NoteResult>
    fun getNoteById(id: String) : LiveData<NoteResult>
    fun saveNote(note: Note) : LiveData<NoteResult>
}