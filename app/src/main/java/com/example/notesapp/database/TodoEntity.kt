package com.example.notesapp.database

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date

@Entity("todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("detail")
    val detail: String = "",
    @ColumnInfo("done")
    val done: Boolean = false,
    @ColumnInfo("added")
    val added: Long = System.currentTimeMillis(),
    @ColumnInfo("recycle")
    val recycle : Boolean = false
)

val TodoEntity.addDate: String @SuppressLint("SimpleDateFormat")
get() = SimpleDateFormat("yyyy/MM/dd hh:mm").format(Date(added))
