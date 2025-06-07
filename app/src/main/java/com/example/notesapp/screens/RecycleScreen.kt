package com.example.notesapp.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material.icons.sharp.Refresh
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapp.database.TodoEntity
import com.example.notesapp.database.addDate
import com.example.notesapp.screens.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleScreen(
    viewModel: HomeViewModel = viewModel(),
    onBack: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getDeletedTodos()
    }

    val deletedTodos by viewModel.deletedTodos.collectAsState()

    val context = LocalContext.current

    val (selectTodo, setSelectTodo) = remember {
        mutableStateOf<TodoEntity?>(null)
    }

    val (dialogOpen, setDialog) = remember {
        mutableStateOf(false)
    }

    val (backDialogOpen, backSetDialog) = remember {
        mutableStateOf(false)
    }

    if (dialogOpen && selectTodo != null) {

        AlertDialog(
            onDismissRequest = { setDialog(false) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteTodo(selectTodo!!)
                        setDialog(false)
                    }
                ) {
                    Text("Yse")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { setDialog(false) }
                ) {
                    Text("No")
                }
            },
            title = {
                Text(
                    "Delete this Todo",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    "Are you sure you want delete?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            icon = {
                Icon(
                    Icons.TwoTone.Warning,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(35.dp)
                )
            }
        )
    }

    if (backDialogOpen && selectTodo != null) {

        AlertDialog(
            onDismissRequest = { backSetDialog(false) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.backFromRecycle(selectTodo.id)
                        backSetDialog(false)
                    }
                ) {
                    Text("Yse")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { backSetDialog(false) }
                ) {
                    Text(
                        "No"
                    )
                }
            },
            title = {
                Text(
                    "Back this Todo",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    "Are you sure you want to back?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            icon = {
                Icon(
                    Icons.TwoTone.CheckCircle,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(35.dp)
                )
            }
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.secondary,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text("Recycle", color = Color.White)
                },
                actions = {
                    Button(
                        onClick = {
                            viewModel.deleteAllTodos()
                            Toast.makeText(context, "Successfully Deleted All Todos!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "Delete All Todos!",
                            color = Color.White
                        )
                    }
                }
            )
        }

    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {

            AnimatedVisibility(
                visible = deletedTodos.isEmpty(),
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Text("Recycle is Empty", color = Color.White, fontSize = 22.sp)
            }

            AnimatedVisibility(
                visible = deletedTodos.isNotEmpty(),
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
                        items = deletedTodos,
                        key = { todo -> todo.id }
                    ) { todo ->

                        TodoItemRecycle(
                            todo = todo,
                            onClick = {
                                setSelectTodo(todo)
                                backSetDialog(true)
                            },
                            onDelete = {
                                setSelectTodo(todo)
                                setDialog(true)
                            }
                        )

                    }

                }
            }
        }

    }

}

@Composable
fun TodoItemRecycle(todo: TodoEntity, onClick: () -> Unit, onDelete: () -> Unit) {

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(Color(0xffff6363))
                .padding(
                    horizontal = 8.dp, vertical = 16.dp
                ), horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(modifier = Modifier.weight(1f),) {
                    Text(
                        text = todo.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        softWrap = false,
                        color = Color.White
                    )

                    Text(
                        text = todo.detail.makeBreak(10),
                        fontSize = 12.sp,
                        color = Color(0xffebebeb),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(vertical = 6.dp),

                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .padding(4.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Sharp.Refresh,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .clickable {
                                onClick()
                            }
                            .size(35.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .padding(4.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Sharp.Delete,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .clickable {
                                onDelete()
                            }
                            .size(35.dp)
                    )
                }
            }
        }

        Text(
            modifier = Modifier.padding(top = 8.dp, end = 8.dp),
            text = todo.addDate,
            color = Color(0xffebebeb),
            fontSize = 10.sp
        )

    }
}

private fun String.makeBreak(chunk : Int = 15): String {
    if (this.length > chunk && !this.contains(" ")) {
        return this.chunked(chunk).joinToString("\u200B")
    }
    return this
}