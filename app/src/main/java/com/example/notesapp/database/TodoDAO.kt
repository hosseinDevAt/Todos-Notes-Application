package com.example.notesapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDAO {

    @Insert
    fun addTodo(todo: TodoEntity)

    @Query("SELECT * FROM `todos` WHERE recycle = 0")
    fun getAllTodos() : Flow<List<TodoEntity>>

    @Query("SELECT * FROM `todos` WHERE recycle = 1")
    fun getDeletedTodos() : Flow<List<TodoEntity>>

    @Update
    fun updateTodo(todo: TodoEntity)

    @Delete
    fun deleteTodo(todo: TodoEntity)

    @Query("UPDATE `todos` SET recycle = 1 WHERE id = :id")
    fun firstDeleteTodo(id : Int)

    @Query("UPDATE `todos` SET recycle = 0 WHERE id = :id")
    fun backTodo(id : Int)

    @Query("DELETE FROM `todos`")
    fun deleteAll()
}