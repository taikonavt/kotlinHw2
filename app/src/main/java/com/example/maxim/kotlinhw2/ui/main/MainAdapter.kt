package com.example.maxim.kotlinhw2.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.maxim.kotlinhw2.R
import com.example.maxim.kotlinhw2.data.model.Note

class MainAdapter : RecyclerView.Adapter<MainAdapter.NoteViewHolder>() {

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

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val title = itemView.findViewById<TextView>(R.id.title)
        private val body = itemView.findViewById<TextView>(R.id.body)

        fun bind(note : Note) {
            title.text = note.title
            body.text = note.note
            itemView.setBackgroundColor(note.color)
        }
    }
}