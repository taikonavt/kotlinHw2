package com.example.maxim.kotlinhw2.ui.note

import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.ui.base.BaseViewState

class NoteViewState (data: Data = Data(), error: Throwable? = null) : BaseViewState<NoteViewState.Data>(data, error){
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}