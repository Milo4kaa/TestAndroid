@file:Suppress("DEPRECATION")

package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.SearchEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SearchView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.ui.theme.NoteActivity
import com.example.todolist.ui.theme.NoteTable
import com.example.todolist.ui.theme.ui.theme.AppDatabase
import com.example.todolist.ui.theme.ui.theme.NoteAdapter
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromButton: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_button_anim) }
    private val toButton: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_button_anim) }
    private var clicked = false
     lateinit var  binding: ActivityMainBinding
    private var  editLauncher: ActivityResultLauncher<Intent>? = null
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    var searchList = ArrayList<Note>()
    var dataList = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        val dateFormat = DateFormat.getDateFormat(
            applicationContext
        )
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addBtn.setOnClickListener {
            onAddButtonClicked()
        }
        recyclerView = findViewById(R.id.rcView)
        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    dataList.forEach {
                        if (it.Title.toLowerCase(Locale.getDefault()).contains(searchText)){
                            searchList.add(it)
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()
                }else {
                    searchList.clear()
                    searchList.addAll(dataList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }

        })
    }

    override fun onResume(){
      super.onResume()
        lifecycleScope.launch {
           val noteList= AppDatabase(this@MainActivity).getNoteDao().getAllNote()

            binding.rcView.apply {
                searchList.addAll(dataList)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = NoteAdapter().apply {
                    setData(noteList)
                    setOnActionEditListener {
                        val intent = Intent(this@MainActivity, NoteActivity::class.java)
                        intent.putExtra("Data",it)
                        startActivity(intent)
                    }
                }
            }
        }
    }
    private  fun onAddButtonClicked(){
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }
    private  fun setVisibility(clicked: Boolean) {
        if(!clicked){
            binding.apply{
                noteBtn.visibility = View.VISIBLE
                dataBtn.visibility = View.VISIBLE
            }
        }
        else{
            binding.apply{
                noteBtn.visibility = View.INVISIBLE
                dataBtn.visibility = View.INVISIBLE
            }
        }
    }
    private fun setAnimation(clicked: Boolean) {
        if (!clicked){
            binding.apply{
                noteBtn.startAnimation(fromButton)
                dataBtn.startAnimation(fromButton)
                addBtn.startAnimation(rotateOpen)
            }
        }else{
            binding.apply{
                noteBtn.startAnimation(toButton)
                dataBtn.startAnimation(toButton)
                addBtn.startAnimation(rotateClose)
            }
        }
    }

    fun onClickOpenNoteActivity (view : View){
        val intent = Intent(this, NoteActivity::class.java)
        startActivity(intent)
    }
    fun  onClickBackToMenu(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}