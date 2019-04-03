package com.example.maxim.kotlinhw2.ui.main

import android.arch.lifecycle.Observer
import com.example.maxim.kotlinhw2.data.Repository
import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.data.model.Result
import com.example.maxim.kotlinhw2.ui.base.BaseViewModel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : BaseViewModel<List<Note>?>() {

    private val notesChannel = repository.getNotes()

    init {
        launch {
            notesChannel.consumeEach {
                when(it){
                    is Result.Success<*> -> setData(it.data as? List<Note>)
                    is Result.Error -> setError(it.error)
                }
            }
        }
    }

    override fun onCleared() {
        notesChannel.cancel()
        super.onCleared()
    }
}