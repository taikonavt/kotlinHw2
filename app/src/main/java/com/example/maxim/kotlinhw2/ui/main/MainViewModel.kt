package com.example.maxim.kotlinhw2.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.maxim.kotlinhw2.data.Repository

class MainViewModel(private val repository: Repository = Repository) : ViewModel() {

    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        repository.getNotes().observeForever{
            viewStateLiveData.value = viewStateLiveData.value?.copy(notes = it!!) ?:
                    MainViewState(it!!)
        }
    }

    fun viewState() : LiveData<MainViewState> = viewStateLiveData
}