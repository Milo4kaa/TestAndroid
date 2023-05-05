package com.example.todolist.ui.theme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.todolist.MainActivity
import com.example.todolist.R

class NoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
    }
    fun onClickBackToMain (view : View){

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun BackToMain(view: View) {}
}