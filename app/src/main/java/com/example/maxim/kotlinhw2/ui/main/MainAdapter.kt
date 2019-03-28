package com.example.maxim.kotlinhw2.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.maxim.kotlinhw2.R
import com.example.maxim.kotlinhw2.data.model.Note
import com.example.maxim.kotlinhw2.data.model.Note.Color
import kotlinx.android.extensions.LayoutContainer

class MainAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<MainAdapter.NoteViewHolder>() {

    var notes: List<Note> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder : NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    inner class NoteViewHolder(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer{

        fun bind(note : Note) {

            val title = itemView.findViewById<TextView>(R.id.title)
            val body = itemView.findViewById<TextView>(R.id.body)

            val color = when(note.color) {
                Color.WHITE -> R.color.white
                Color.VIOLET -> R.color.violet
                Color.YELLOW -> R.color.yellow
                Color.RED -> R.color.red
                Color.PINK -> R.color.pink
                Color.GREEN -> R.color.green
                Color.BLUE -> R.color.blue
            }

            title.text = note.title
            body.text = note.note
            itemView.setBackgroundColor(color)
            itemView.setOnClickListener{onItemClickListener.onItemClick(note)}
        }
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }
}