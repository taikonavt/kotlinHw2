package com.example.maxim.kotlinhw2.data.model

sealed class NoteResult {
    data class Success<out T>(val data: T) : NoteResult()
    data class Error(val error: Throwable) : NoteResult()
}