package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.ui.theme.NoteActivity

class MainActivity : AppCompatActivity() {

     lateinit var  binding: ActivityMainBinding
     private var adapter = NoteAdapter()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(this@MainActivity)
            rcView.adapter = adapter
            imageButton2.setOnClickListener {
                val note = Note("222222","122222")
                adapter.addNote(note)
            }
        }
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