package com.example.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note(
    var Title:String ="",
    var MainText:String =""
){
    @PrimaryKey(autoGenerate = true) var id:Int= 0
}
