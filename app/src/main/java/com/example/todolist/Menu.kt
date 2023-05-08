package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.todolist.ui.theme.NoteActivity

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }
    fun onClickOpenNoteActivity (view : View){
        val intent = Intent(this, NoteActivity::class.java)
        startActivity(intent)
    }
    fun onClickOpenMainActivity (view : View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}