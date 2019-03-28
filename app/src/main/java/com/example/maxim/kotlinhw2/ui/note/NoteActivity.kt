package com.example.maxim.kotlinhw2.ui.note;

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import com.example.maxim.kotlinhw2.R
import com.example.maxim.kotlinhw2.common.format
import com.example.maxim.kotlinhw2.common.getColorInt
import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

private const val SAVE_DELAY = 2000L

class NoteActivity : BaseActivity<NoteViewState.Data, NoteViewState>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"

        fun startIntent(context: Context, noteId: String? = null) =
                context.startActivity<NoteActivity>(EXTRA_NOTE to noteId)
    }

    private var color = Note.Color.WHITE
    private var note: Note? = null
    override val layoutRes: Int = R.layout.activity_note

    override val model: NoteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        val noteId = intent.getStringExtra(EXTRA_NOTE)
        setSupportActionBar(toolbar_activity_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        colorPicker.onColorClickListener = {
            color = it
            setToolbarColor(it)
            triggerSaveNote()
        }

        noteId?.let{
            model.loadNote(it)
        }

        if (noteId == null) supportActionBar?.title = getString(R.string.new_note_title)

        titleEt_activity_note.addTextChangedListener(textChangeListener)
        bodyEt_activity_note.addTextChangedListener(textChangeListener)
    }

    override fun renderData(data: NoteViewState.Data) {
        if (data.isDeleted) finish()

        this.note = data.note
        data.note?.let{color = it.color}
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
                    lastChanged = Date(),
                    color = color)
                    ?: createNewNote()

            if (note != null) model.saveChanges(note!!)
        }, SAVE_DELAY)
    }

    private fun initView() {
        note?.run {
            supportActionBar?.title = lastChanged.format(DATE_FORMAT)

            removeEditListener()
            titleEt_activity_note.setText(title)
            bodyEt_activity_note.setText(note)
            setEditListener()

            toolbar_activity_note.setBackgroundColor(
                    color.getColorInt(this@NoteActivity))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        menuInflater.inflate(R.menu.note_menu, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when(item.itemId){
                android.R.id.home ->
                    super.onBackPressed().let { true }
                R.id.palette -> togglePalette().let{true}
                R.id.delete -> deleteNote().let{true}
                else -> super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (colorPicker.isOpen){
            colorPicker.close()
            return
        }
        super.onBackPressed()
    }

    private fun deleteNote() {
        alert {
            messageResource = R.string.delete_dialog_message
            negativeButton(R.string.cancel_btn_title){dialog -> dialog.dismiss()}
            positiveButton(R.string.ok_btn_title){model.deleteNote()}
        }.show()
    }

    private fun togglePalette() {
        if (colorPicker.isOpen){
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }

    private fun createNewNote() : Note = Note(UUID.randomUUID().toString(),
            titleEt_activity_note.text.toString(),
            bodyEt_activity_note.text.toString())

    private fun setEditListener(){
        titleEt_activity_note.addTextChangedListener(textChangeListener)
        bodyEt_activity_note.addTextChangedListener(textChangeListener)
    }

    private fun removeEditListener(){
        titleEt_activity_note.removeTextChangedListener(textChangeListener)
        bodyEt_activity_note.removeTextChangedListener(textChangeListener)
    }

    private fun setToolbarColor(color : Note.Color){
        toolbar_activity_note.setBackgroundColor(color.getColorInt(this))
    }
}