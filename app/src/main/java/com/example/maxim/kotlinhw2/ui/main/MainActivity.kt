package com.example.maxim.kotlinhw2.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.example.maxim.kotlinhw2.R
import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.ui.base.BaseActivity
import com.example.maxim.kotlinhw2.ui.main.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    override val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override val layoutRes: Int = R.layout.activity_main
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar_activity_main)

        adapter = MainAdapter(object : MainAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                openNoteScreen(note)
            }
        })
        mainRecycler.adapter = adapter

        fab_activity_main.setOnClickListener {
            View.OnClickListener { openNoteScreen(null) }
        }
    }

    override fun renderData(data: List<Note>?) {
        if (data == null) return
        adapter.notes = data
    }

    private fun openNoteScreen(note: Note?) {
        NoteActivity.startIntent(this, note?.id)
    }
}
