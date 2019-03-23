package com.example.maxim.kotlinhw2.ui.splash

import com.example.maxim.kotlinhw2.data.Repository
import com.example.maxim.kotlinhw2.data.errors.NoAuthException
import com.example.maxim.kotlinhw2.ui.base.BaseViewModel

class SplashViewModel (private val repository: Repository = Repository) :
        BaseViewModel<Boolean?, SplashViewState>(){

    fun requestUser(){
        repository.getCurrentUser().observeForever{
            viewStateLiveData.value = if (it != null){
                SplashViewState(isAuth = true)
            } else {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}