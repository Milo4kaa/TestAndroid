package com.example.todolist.ui.theme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.todolist.MainActivity
import com.example.todolist.Menu
import com.example.todolist.Note
import com.example.todolist.databinding.ActivityNoteBinding


class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
        val db = MainBD.connectBD(this)

        binding.BackAndSave.setOnClickListener {
            val note = Note(binding.Tittle.text.toString(),
                binding.MainText.text.toString())
            db.getDao().insertNote(note)
        }
    }
    fun initButtons() = with(binding){
        BackAndSave.setOnClickListener {
            val note = Note(Tittle.text.toString(), MainText.text.toString())
            val editIntent = Intent().apply {
                putExtra("note", note)
            }
            setResult(RESULT_OK , editIntent)
            finish()
        }
    }
    fun  onClickBackToMenu(view: View)
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun onClickOpenNoteActivity (view : View){
        val intent = Intent(this, NoteActivity::class.java)
        startActivity(intent)
    }
    fun onClickOpenMenuActivity (view : View){
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }
}