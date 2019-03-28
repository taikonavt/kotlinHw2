package com.example.maxim.kotlinhw2

import android.support.multidex.MultiDexApplication
import com.example.maxim.kotlinhw2.di.appModule
import com.example.maxim.kotlinhw2.di.mainModule
import com.example.maxim.kotlinhw2.di.noteModule
import com.example.maxim.kotlinhw2.di.splashModule
import org.koin.android.ext.android.startKoin

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, splashModule, mainModule, noteModule))
    }
}