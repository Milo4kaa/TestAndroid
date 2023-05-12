@file:Suppress("DEPRECATION")

package com.example.todolist.ui.theme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.room.Update
import com.example.todolist.MainActivity
import com.example.todolist.Note
import com.example.todolist.databinding.ActivityNoteBinding
import com.example.todolist.ui.theme.ui.theme.AppDatabase
import kotlinx.coroutines.launch


class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    private var note: Note? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        note = intent.getSerializableExtra("Data") as Note
        if (note == null){
        }else{
            binding.Tittle.setText(note?.Title.toString())
            binding.MainText.setText(note?.MainText.toString())
        }
        binding.BackAndSave.setOnClickListener { addNote() }
    }

    private fun addNote() {
        val Title = binding.Tittle.text.toString()
        val MainText = binding.MainText.text.toString()

        lifecycleScope.launch {
            if(note == null){
            val note = Note(Title = Title, MainText = MainText)
            AppDatabase(this@NoteActivity).getNoteDao().addNote(note)
            finish()
        }else{
            val n = Note(Title, MainText)
                n.id = note?.id ?: 0
                AppDatabase(this@NoteActivity).getNoteDao().updateNote(n)
                finish()
          }
        }
    }

    fun  onClickBackToMenu(view: View)
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}