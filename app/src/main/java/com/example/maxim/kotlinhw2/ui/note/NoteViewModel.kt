package com.example.maxim.kotlinhw2.ui.note

import com.example.maxim.kotlinhw2.data.Repository
import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.data.model.Result
import com.example.maxim.kotlinhw2.ui.base.BaseViewModel

class NoteViewModel (private val repository: Repository): BaseViewModel<NoteViewState.Data, NoteViewState>(){

    private val currentNote: Note?
        get() = viewStateLiveData.value?.data?.note

    fun saveChanges(note: Note){
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
    }

    override fun onCleared(){
        currentNote?.let { repository.saveNote(it) }
    }

    fun loadNote(noteId: String){
        repository.getNoteById(noteId).observeForever{t ->
            t?.let {
                viewStateLiveData.value = when (t) {
                    is Result.Success<*> -> NoteViewState(NoteViewState.Data(note = t.data as? Note))
                    is Result.Error -> NoteViewState(error = t.error)
                }
            }
        }
    }

    fun deleteNote(){
        currentNote?.let{
            repository.deleteNote(it.id).observeForever{ t ->
                t?.let{
                    viewStateLiveData.value = when (it) {
                        is Result.Success<*> -> NoteViewState(NoteViewState.Data(isDeleted = true))
                        is Result.Error -> NoteViewState(error = it.error)
                    }
                }
            }
        }
    }
}