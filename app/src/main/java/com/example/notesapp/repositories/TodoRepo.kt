package com.example.notesapp.repositories

import com.example.notesapp.database.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepo {
    suspend fun getAllTodos(): Flow<List<TodoEntity>>
    suspend fun getDeletedTodos(): Flow<List<TodoEntity>>
    suspend fun addTodo(todo: TodoEntity)
    suspend fun updateTodo(todo: TodoEntity)
    suspend fun deleteTodo(todo: TodoEntity)
    suspend fun deleteTodoItem(id : Int)
    suspend fun backFromRecycle(id : Int)
    suspend fun deleteAllTodos()
}