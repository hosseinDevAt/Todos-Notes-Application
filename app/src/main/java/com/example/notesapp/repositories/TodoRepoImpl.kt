package com.example.notesapp.repositories

import com.example.notesapp.database.TodoDatabase
import com.example.notesapp.database.TodoEntity
import kotlinx.coroutines.flow.Flow

class TodoRepoImpl(private val database: TodoDatabase): TodoRepo {
    private val dao = database.todoDao()

    override suspend fun getAllTodos(): Flow<List<TodoEntity>> = dao.getAllTodos()
    override suspend fun getDeletedTodos(): Flow<List<TodoEntity>> = dao.getDeletedTodos()
    override suspend fun addTodo(todo: TodoEntity) = dao.addTodo(todo)
    override suspend fun updateTodo(todo: TodoEntity) = dao.updateTodo(todo)
    override suspend fun deleteTodo(todo: TodoEntity) = dao.deleteTodo(todo)
    override suspend fun deleteTodoItem(id: Int) = dao.firstDeleteTodo(id)
    override suspend fun backFromRecycle(id: Int) = dao.backTodo(id)
    override suspend fun deleteAllTodos() = dao.deleteAll()

}