package com.example.notesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TodoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase: RoomDatabase(){
    abstract fun todoDao(): TodoDAO
}