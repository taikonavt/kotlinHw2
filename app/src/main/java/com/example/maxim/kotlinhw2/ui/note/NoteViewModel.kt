package com.example.maxim.kotlinhw2.ui.note

import com.example.maxim.kotlinhw2.data.Repository
import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.data.model.Result
import com.example.maxim.kotlinhw2.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class NoteViewModel (private val repository: Repository): BaseViewModel<NoteData>(){

    private val currentNote: Note?
        get() = getViewState().poll()?.note

    fun saveChanges(note: Note){
        setData(NoteData(note = note))
    }

    override fun onCleared() {
        launch {
            currentNote?.let { repository.saveNote(it) }
            super.onCleared()
        }
    }

    fun loadNote(noteId: String){
        launch {
            try {
                repository.getNoteById(noteId).let {
                    setData(NoteData(note = it))
                }
            } catch (e: Throwable){
                setError(e)
            }
        }
    }

    fun deleteNote() {
        launch {
            try {
                currentNote?.let { repository.deleteNote(it.id) }
                setData(NoteData(isDeleted = true))
            } catch (e: Throwable){
                setError(e)
            }
        }
    }
}