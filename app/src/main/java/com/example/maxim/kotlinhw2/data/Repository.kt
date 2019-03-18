package com.example.maxim.kotlinhw2.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.maxim.kotlinhw2.data.model.Color
import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.data.provider.FireStoreProvider
import com.example.maxim.kotlinhw2.data.provider.RemoteDataProvider
import java.util.*

object Repository {

    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
}