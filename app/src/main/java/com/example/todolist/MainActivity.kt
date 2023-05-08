@file:Suppress("DEPRECATION")

package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.ui.theme.NoteActivity

class MainActivity : AppCompatActivity() {

     lateinit var  binding: ActivityMainBinding
     private var adapter = NoteAdapter()
    private var  editLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                adapter.addNote(it.data?.getSerializableExtra("note") as Note)
            }
        }
    }
    private fun init(){
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(this@MainActivity)
            rcView.adapter = adapter
            imageButton2.setOnClickListener {
                editLauncher?.launch(Intent(this@MainActivity, NoteActivity::class.java))
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