package com.example.todolist.ui.theme

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Notes")
data  class NoteTable (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "Title")
    var title: String,

    @ColumnInfo(name = "MainText")
    var maintext: String,
        )