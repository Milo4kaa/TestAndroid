package com.example.todolist.ui.theme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.todolist.MainActivity
import com.example.todolist.Note
import com.example.todolist.databinding.ActivityNoteBinding
import com.example.todolist.ui.theme.ui.theme.AppDatabase
import kotlinx.coroutines.launch


class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //initButtons()
        binding.BackAndSave.setOnClickListener { addNote() }
    }

    private fun addNote() {
        val Title = binding.Tittle.text.toString()
        val MainText = binding.MainText.text.toString()

        lifecycleScope.launch {
            val note = Note(Title = Title, MainText = MainText)
            AppDatabase(this@NoteActivity).getNoteDao().addNote(note)
            finish()
        }
    }

    /*fun initButtons() = with(binding){
        BackAndSave.setOnClickListener {
            val note = Note(Tittle.text.toString(), MainText.text.toString())
            val editIntent = Intent().apply {
                putExtra("note", note)
            }
            setResult(RESULT_OK , editIntent)
            finish()
        }
    }*/
    fun  onClickBackToMenu(view: View)
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}