package com.example.maxim.kotlinhw2.ui.note

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.example.maxim.kotlinhw2.data.Repository
import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.data.model.NoteResult
import com.example.maxim.kotlinhw2.ui.base.BaseViewModel

class NoteViewModel (private val repository: Repository = Repository): BaseViewModel<Note?, NoteViewState>(){

    private var pendingNote: Note? = null

    fun saveChanges(note: Note){
        pendingNote = note
    }

    override fun onCleared(){
        if (pendingNote != null){
            repository.saveNote(pendingNote!!)
        }
    }

    fun loadNote(noteId: String){
        repository.getNoteById(noteId).observeForever(object : Observer<NoteResult>{
            override fun onChanged(t: NoteResult?) {
                if (t == null) return

                when(t){
                    is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(note = t.data as? Note)
                    is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = t.error)
                }
            }
        })
    }
}