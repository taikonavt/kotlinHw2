package com.example.maxim.kotlinhw2.ui.main

import android.arch.lifecycle.Observer
import com.example.maxim.kotlinhw2.data.Repository
import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.data.model.Result
import com.example.maxim.kotlinhw2.ui.base.BaseViewModel

class MainViewModel(private val repository: Repository) : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = object : Observer<Result>{
        override fun onChanged(t: Result?) {
            if (t == null) return
            when(t) {
                is com.example.maxim.kotlinhw2.data.model.Result.Success<*> -> {
                    viewStateLiveData.value = MainViewState(notes = t.data as? List<Note>)
                }
                is com.example.maxim.kotlinhw2.data.model.Result.Error -> {
                    viewStateLiveData.value = MainViewState(error = t.error)
                }
            }
        }
    }

    private val repositoryNotes = repository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
    }
}