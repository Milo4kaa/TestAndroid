package com.example.todolist.ui.theme
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Insert
   suspend fun  addNote(note: Note)
   @Query("SELECT * FROM note ORDER BY id DESC")
   suspend fun getAllNote(): List<Note>

   @Update
   suspend fun updateNote(note: Note)

   @Delete
   suspend fun  deleteNote(note: Note)
}