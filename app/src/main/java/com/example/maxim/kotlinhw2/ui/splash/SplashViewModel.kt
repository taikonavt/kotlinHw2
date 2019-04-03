package com.example.maxim.kotlinhw2.ui.splash

import com.example.maxim.kotlinhw2.data.Repository
import com.example.maxim.kotlinhw2.data.errors.NoAuthException
import com.example.maxim.kotlinhw2.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel (private val repository: Repository) :
        BaseViewModel<Boolean?>(){

    fun requestUser(){
        launch {
            repository.getCurrentUser()?.let {
                setData(true)
            } ?: setError(NoAuthException())
        }
    }
}