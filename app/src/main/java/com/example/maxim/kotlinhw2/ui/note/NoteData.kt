package com.example.maxim.kotlinhw2.ui.note

import com.example.maxim.kotlinhw2.data.model.Note

data class NoteData(val isDeleted: Boolean = false, val note: Note? = null)