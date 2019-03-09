package com.example.maxim.kotlinhw2.ui.main.note

import android.arch.lifecycle.ViewModel
import com.example.maxim.kotlinhw2.data.Repository
import com.example.maxim.kotlinhw2.data.model.Note

class NoteViewModel (private val repository: Repository = Repository): ViewModel(){

    private var pendingNote: Note? = null

    fun saveChanges(note: Note){
        pendingNote = note
    }

    override fun onCleared(){
        if (pendingNote != null){
            repository.saveNote(pendingNote!!)
        }
    }
}