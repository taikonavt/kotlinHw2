package com.example.maxim.kotlinhw2.ui.main.note;

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import com.example.maxim.kotlinhw2.R
import com.example.maxim.kotlinhw2.data.model.Color
import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.ui.base.BaseActivity
import com.example.maxim.kotlinhw2.ui.base.BaseViewModel
import kotlinx.android.synthetic.main.activity_note.*
import java.text.SimpleDateFormat
import java.util.*

private const val SAVE_DELAY = 2000L

class NoteActivity : BaseActivity<Note?, NoteViewState>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"

        fun startIntent(context: Context, noteId: String? = null) {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, noteId)
            context.startActivity(intent)
        }
    }

    override val viewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }
    override val layoutRes: Int = R.layout.activity_note

    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        val noteId = intent.getStringExtra(EXTRA_NOTE)
        setSupportActionBar(toolbar_activity_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        noteId?.let{
            viewModel.loadNote(it)
        }

        if (noteId == null) supportActionBar?.title = getString(R.string.new_note_title)

        titleEt_activity_note.addTextChangedListener(textChangeListener)
        bodyEt_activity_note.addTextChangedListener(textChangeListener)
    }

    override fun renderData(data: Note?) {
        this.note = data
        initView()
    }

    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            triggerSaveNote()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
    }

    private fun triggerSaveNote() {
        if (titleEt_activity_note.text!!.length < 3) return

        Handler().postDelayed({
            note = note?.copy(title = titleEt_activity_note.text.toString(),
                    note = bodyEt_activity_note.text.toString(),
                    lastChanged = Date())
                    ?: createNewNote()

            if (note != null) viewModel.saveChanges(note!!)
        }, SAVE_DELAY)
    }

    private fun initView() {
        titleEt_activity_note.addTextChangedListener(textChangeListener)
        bodyEt_activity_note.addTextChangedListener(textChangeListener)
        if (note != null) {
            titleEt_activity_note.setText(note?.title ?: "")
            bodyEt_activity_note.setText(note?.note ?: "")
            val color = when(note!!.color) {
                Color.WHITE -> R.color.color_white
                Color.VIOLET -> R.color.color_violet
                Color.YELLOW -> R.color.color_yellow
                Color.RED -> R.color.color_red
                Color.PINK -> R.color.color_pink
                Color.GREEN -> R.color.color_green
                Color.BLUE -> R.color.color_blue
            }
            toolbar_activity_note.setBackgroundColor(
                    ContextCompat.getColor(this, color))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when(item.itemId){
            android.R.id.home -> {
            onBackPressed()
            true
        }
                else -> super.onOptionsItemSelected(item)
    }

    private fun createNewNote() : Note = Note(UUID.randomUUID().toString(),
            titleEt_activity_note.text.toString(),
            bodyEt_activity_note.text.toString())
}