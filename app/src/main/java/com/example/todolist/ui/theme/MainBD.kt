package com.example.todolist.ui.theme

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [NoteList::class], version = 1)
abstract class MainBD : RoomDatabase() {
    abstract fun getDao(): Dao
    companion object {
        fun connectBD(context: Context  ): MainBD{
            return Room.databaseBuilder(
                context.applicationContext,
                MainBD::class.java,
                "notepad.bd"
            ).build()
        }
    }
}