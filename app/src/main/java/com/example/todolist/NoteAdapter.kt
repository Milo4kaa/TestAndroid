package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.NoteItemBinding


class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    val noteList = ArrayList<Note>()
    lateinit var  binding: NoteItemBinding

    class NoteHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = NoteItemBinding.bind(item)
         fun bind(note: Note) = with(binding){
            TitleView.text = note.title
             MainTextView.text = note.MainText
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return  NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
       holder.bind(noteList[position])
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun addNote(note: Note){
        noteList.add(note)
        notifyDataSetChanged()
    }
}