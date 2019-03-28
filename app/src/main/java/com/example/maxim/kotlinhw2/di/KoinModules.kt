package com.example.maxim.kotlinhw2.di

import com.example.maxim.kotlinhw2.data.Repository
import com.example.maxim.kotlinhw2.data.provider.FireStoreProvider
import com.example.maxim.kotlinhw2.data.provider.RemoteDataProvider
import com.example.maxim.kotlinhw2.ui.main.MainViewModel
import com.example.maxim.kotlinhw2.ui.note.NoteViewModel
import com.example.maxim.kotlinhw2.ui.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single {FirebaseAuth.getInstance()}
    single { FirebaseFirestore.getInstance() }
    single { FireStoreProvider(get(), get()) } bind RemoteDataProvider::class
    single { Repository(get()) }
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val noteModule = module {
    viewModel { NoteViewModel(get()) }
}