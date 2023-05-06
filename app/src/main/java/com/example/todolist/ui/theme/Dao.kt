package com.example.todolist.ui.theme
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertNote(note: NoteList)
    @Query("SELECT * FROM Notes")
    fun getAllNote(): Flow<List<NoteList>>
}