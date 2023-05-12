@file:Suppress("DEPRECATION")

package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SearchView
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.ui.theme.NoteActivity
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
    private var mAdapter:NoteAdapter? = null
     lateinit var  binding: ActivityMainBinding
    private var  editLauncher: ActivityResultLauncher<Intent>? = null
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    var searchList = mutableListOf<Note>()
    var dataList = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        val dateFormat = DateFormat.getDateFormat(
            applicationContext
        )
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        swipeToDelete()
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
    private fun setAdapter(list: List<Note>){
        mAdapter?.setData(list)
    }

    override fun onResume(){
      super.onResume()
        lifecycleScope.launch {
           val noteList= AppDatabase(this@MainActivity).getNoteDao().getAllNote()

            mAdapter = NoteAdapter()
            binding.rcView.apply {
                searchList.addAll(dataList)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = mAdapter
                  setAdapter(noteList)
                   mAdapter?.setOnActionEditListener {
                        val intent = Intent(this@MainActivity, NoteActivity::class.java)
                        intent.putExtra("Data",it)
                        startActivity(intent)
                    }
            }
        }
    }
    private fun swipeToDelete(){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                lifecycleScope.launch {
                    val noteList= AppDatabase(this@MainActivity).getNoteDao().getAllNote()
                mAdapter?.setOnActionDeleteListener {
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setMessage("Вы хотите удалить записку?")
                    builder.setPositiveButton("Да"){p0, p1 ->
                        lifecycleScope.launch {
                            AppDatabase(this@MainActivity).getNoteDao().deleteNote(it)
                            val list= AppDatabase(this@MainActivity).getNoteDao().getAllNote()
                            setAdapter(list)
                        }
                        p0.dismiss()
                    }
                    builder.setNegativeButton("Нет"){p0,p1->
                        p0.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()
                }
            }
           }

        }).attachToRecyclerView(binding.rcView)
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