package com.example.todolist.ui.theme
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todolist.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertNote(note: Note)
    @Query("SELECT * FROM Notes")
    fun getAllNote(): Flow<List<NoteList>>
}