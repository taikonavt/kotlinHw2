package com.example.maxim.kotlinhw2.data

import com.example.maxim.kotlinhw2.data.model.Note

object Repository {

    val notes: List<Note> = listOf(
            Note("Моя первая заметка",
                    "Kotlin очень краткий, но при этом выразительный язык",
                    0xfff06292.toInt()),
            Note("Моя первая заметка",
                    "Kotlin очень краткий, но при этом выразительный язык",
                    0xff9575cd.toInt()),
            Note("Моя первая заметка",
                    "Kotlin очень краткий, но при этом выразительный язык",
                    0xff64b5f6.toInt()),
            Note("Моя первая заметка",
                    "Kotlin очень краткий, но при этом выразительный язык",
                    0xff4db6ac.toInt()),
            Note("Моя первая заметка",
                    "Kotlin очень краткий, но при этом выразительный язык",
                    0xffb2ff59.toInt()),
            Note("Моя первая заметка",
                    "Kotlin очень краткий, но при этом выразительный язык",
                    0xffffeb3b.toInt()),
            Note("Моя первая заметка",
                    "Kotlin очень краткий, но при этом выразительный язык",
                    0xffff6e40.toInt())
    )
}