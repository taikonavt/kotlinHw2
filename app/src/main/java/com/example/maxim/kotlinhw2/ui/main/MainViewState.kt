package com.example.maxim.kotlinhw2.ui.main

import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.ui.base.BaseViewState

class MainViewState(notes: List<Note>? = null, error: Throwable? = null) : BaseViewState<List<Note>?>(notes, error)