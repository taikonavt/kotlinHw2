package com.example.maxim.kotlinhw2.common

import android.content.Context
import android.support.v4.content.ContextCompat
import com.example.maxim.kotlinhw2.R
import com.example.maxim.kotlinhw2.data.model.Note

fun Note.Color.getColorInt(context: Context) : Int =
    ContextCompat.getColor(
        context, getColorRes()
    )

fun Note.Color.getColorRes() : Int = when(this) {
    Note.Color.WHITE -> R.color.white
    Note.Color.YELLOW -> R.color.yellow
    Note.Color.GREEN -> R.color.green
    Note.Color.BLUE -> R.color.blue
    Note.Color.RED -> R.color.red
    Note.Color.VIOLET -> R.color.violet
    Note.Color.PINK -> R.color.pink
}