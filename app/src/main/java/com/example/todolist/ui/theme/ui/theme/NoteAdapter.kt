package com.example.todolist.ui.theme.ui.theme

import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.Note
import com.example.todolist.R
import java.util.Collections.addAll

    class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var list = mutableListOf<Note>()
    private var actionEdit: ((Note)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)

        return NoteViewHolder(view)
    }

    override fun getItemCount() = list.size


    fun setData(data: List<Note>){
        list.apply {
            clear()
            addAll(data)
        }
    }

    fun setOnActionEditListener(callback:(Note)->Unit){
        this.actionEdit = callback
    }

    class NoteViewHolder(noteView: View): RecyclerView.ViewHolder(noteView){
        val TitleView: TextView = noteView.findViewById(R.id.TitleView)
        val MainTextView: TextView = noteView.findViewById(R.id.MainTextView)
        val actionEdit: LinearLayout = noteView.findViewById(R.id.Liner)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       val note = list[position]
        holder.TitleView.text = note.Title
        holder.MainTextView.text = note.MainText
        holder.actionEdit.setOnClickListener{actionEdit?.invoke(note)}
    }
}