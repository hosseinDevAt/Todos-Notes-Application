package com.example.notesapp.screens.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.TodoEntity
import com.example.notesapp.repositories.TodoRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel: ViewModel(), KoinComponent {

    private val repo: TodoRepo by inject()
    private val _todos: MutableStateFlow<List<TodoEntity>> = MutableStateFlow(emptyList())
    val todos = _todos.asStateFlow()
    private val _deletedTodos: MutableStateFlow<List<TodoEntity>> = MutableStateFlow(emptyList())
    val deletedTodos = _deletedTodos.asStateFlow()

    init {
        getAllTodos()
    }

    fun getAllTodos(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllTodos().collect {data ->
                _todos.update { data }
            }
        }
    }

    fun getDeletedTodos(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getDeletedTodos().collect {data ->
                _deletedTodos.update { data }
            }
        }
    }

    fun updateTodo(todo: TodoEntity){
        viewModelScope.launch(Dispatchers.IO){
            repo.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: TodoEntity){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteTodo(todo)
        }
    }

    fun backFromRecycle(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repo.backFromRecycle(id)
        }
    }

    fun addTodo(todo: TodoEntity){
        viewModelScope.launch(Dispatchers.IO){
            repo.addTodo(todo)
        }
    }

    fun deleteTodoRecycle(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteTodoItem(id)
        }
    }

    fun deleteAllTodos(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAllTodos()
        }
    }
}