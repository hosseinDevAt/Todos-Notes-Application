package com.example.notesapp.screens

import android.graphics.text.LineBreaker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.database.TodoEntity
import com.example.notesapp.database.addDate
import com.example.notesapp.screens.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onGoToRecycle : () -> Unit
) {

    val todos by viewModel.todos.collectAsState()

    val (dialogOpen, setDialogOpen) = remember {
        mutableStateOf(false)
    }

    val (editDialogOpen, setEditDialogOpen) = remember {
        mutableStateOf(false)
    }
    val (selectTodo, setSelectTodo) = remember {
        mutableStateOf<TodoEntity?>(null)
    }

    if (editDialogOpen && selectTodo != null){
        EditDialog(
            todo = selectTodo,
            onDismiss = { setEditDialogOpen(false) },
            onUpdated = {title, detail ->
                viewModel.updateTodo(
                    selectTodo.copy(
                        id = selectTodo.id,
                        title = title,
                        detail = detail,
                        done = selectTodo.done,
                        added = selectTodo.added,
                        recycle = selectTodo.recycle
                    )
                )
            }
        )
    }

    if (dialogOpen) {
        val (title, setTitle) = remember {
            mutableStateOf("")
        }
        val (detail, setDetail) = remember {
            mutableStateOf("")
        }
        Dialog(onDismissRequest = { setDialogOpen(false) }) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                OutlinedTextField(
                    value = title,
                    onValueChange = { setTitle(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            "Title", fontSize = 16.sp
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedLabelColor = Color.White,
                        focusedTextColor = Color.Black
                    )
                )

                Spacer(Modifier.height(6.dp))

                OutlinedTextField(
                    value = detail,
                    onValueChange = { setDetail(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    singleLine = false,
                    maxLines = 5,
                    label = {
                        Text(
                            "Detail", fontSize = 18.sp
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedLabelColor = Color.White,
                        focusedTextColor = Color.Black
                    )
                )

                Spacer(Modifier.height(18.dp))

                Button(
                    onClick = {
                        if (title.isNotEmpty()) {
                            viewModel.addTodo(
                                TodoEntity(
                                    title = title, detail = detail
                                )
                            )
                            setDialogOpen(false)
                        }
                    }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ), shape = RoundedCornerShape(5.dp)
                ) {
                    Text(
                        "Submit", color = Color.White
                    )
                }

            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.secondary, topBar = {
            TopAppBar(
                title = {Text("todo list", color = Color.White)},
                actions = {
                    IconButton(onClick = onGoToRecycle) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "recycle bin",
                            modifier = Modifier
                                .size(45.dp),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        } ,floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    setDialogOpen(true)
                }, contentColor = Color.White, containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {

            AnimatedVisibility(
                visible = todos.isEmpty(),
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Text("No Todos Yet!", color = Color.White, fontSize = 22.sp)
            }

            AnimatedVisibility(
                visible = todos.isNotEmpty(),
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = innerPadding.calculateBottomPadding() + 8.dp,
                            top = 8.dp,
                            end = 8.dp,
                            start = 8.dp
                        ), verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(
                        items = todos.sortedBy { it.done },
                        key = { todo -> todo.id }
                    ) { todo ->

                        TodoItem(todo = todo, onClick = {
                            viewModel.updateTodo(
                                todo.copy(done = !todo.done)
                            )
                        }, onDelete = {
                            viewModel.deleteTodoRecycle(todo.id)
                        },
                            onLongClick = {
                                setSelectTodo(todo)
                                setEditDialogOpen(true)
                            }
                        )

                    }

                }
            }


        }

    }

}
@Composable
fun TodoItem(todo: TodoEntity, onClick: () -> Unit, onDelete: () -> Unit, onLongClick: () -> Unit) {

    val color by animateColorAsState(
        targetValue = if (todo.done) Color(0xff24d65f) else Color(
            0xffff6363
        ), animationSpec = tween(500)
    )

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(color)
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                )

                .padding(
                    horizontal = 8.dp, vertical = 16.dp
                ), horizontalArrangement = Arrangement.SpaceBetween) {

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Row {
                        AnimatedVisibility(
                            visible = todo.done,
                            enter = scaleIn() + fadeIn(),
                            exit = scaleOut() + fadeOut()
                        ) {
                            Icon(
                                Icons.Default.Check, contentDescription = null, tint = color
                            )
                        }
                    }

                    Row {
                        AnimatedVisibility(
                            visible = !todo.done,
                            enter = scaleIn() + fadeIn(),
                            exit = scaleOut() + fadeOut()
                        ) {
                            Icon(
                                Icons.Default.Close, contentDescription = null, tint = color
                            )
                        }
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = todo.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )

                    Text(
                        text = todo.detail,
                        fontSize = 12.sp,
                        color = Color(0xffebebeb),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(vertical = 6.dp),
                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .padding(4.dp)
                    .background(MaterialTheme.colorScheme.primary,RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Sharp.Delete,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        onDelete()
                    }.size(25.dp)
                )
            }
        }

        Text(
            modifier = Modifier.padding(top = 8.dp, end = 8.dp),
            text = todo.addDate,
            color = Color(0xffebebeb),
            fontSize = 10.sp,
        )

    }
}

@Composable
fun EditDialog(
    todo: TodoEntity,
    onDismiss: () -> Unit,
    onUpdated: (String, String) -> Unit
){

    var title by remember {
        mutableStateOf(todo.title)
    }

    var detail by remember {
        mutableStateOf(todo.detail)
    }

    Dialog(onDismissRequest = { onDismiss }) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        "Title", fontSize = 16.sp
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLabelColor = Color.White,
                    focusedTextColor = Color.Black
                )
            )

            Spacer(Modifier.height(6.dp))

            OutlinedTextField(
                value = detail,
                onValueChange = { detail = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                singleLine = false,
                maxLines = 5,
                label = {
                    Text(
                        "Detail", fontSize = 18.sp
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLabelColor = Color.White,
                    focusedTextColor = Color.Black
                )
            )

            Spacer(Modifier.height(18.dp))

            Button(
                onClick = {
                    if (title.isNotEmpty()) {
                        onUpdated(title, detail)
                        onDismiss()
                    }
                }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ), shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    "Update", color = Color.White
                )
            }

        }
    }

}