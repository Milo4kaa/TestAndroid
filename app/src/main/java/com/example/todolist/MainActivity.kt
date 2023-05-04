package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.todolist.ui.theme.NoteActivity

class MainActivity : AppCompatActivity() {
    public var i = ""
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onClickOpenNoteActivity (view : View){

        val intent = Intent(this, NoteActivity::class.java)
        startActivity(intent)
    }
}