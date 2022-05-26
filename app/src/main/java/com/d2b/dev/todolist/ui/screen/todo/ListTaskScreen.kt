package com.d2b.dev.todolist.ui.screen.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.d2b.dev.todolist.data.model.Task

@Composable
fun ListTaskScreenView(
    typeScreen: TypeScreen = TypeScreen.All,
    viewModel: ListTaskViewModel = ListTaskViewModel(),
) {
    val tasks by viewModel.getTasksFlow().collectAsState()
    val displayTasks = tasks.filter {
        typeScreen == TypeScreen.All ||
                (typeScreen == TypeScreen.Complete && it.status == Task.TaskStatus.Complete) ||
                (typeScreen == TypeScreen.Incomplete && it.status == Task.TaskStatus.Incomplete)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(typeScreen.title, style = MaterialTheme.typography.h6)
                }
            )
        }
    ) {
        Column {
            displayTasks.forEach {
                Button(onClick = { viewModel.updateTask(it.id) }) {
                    Text(it.id + ": " + it.status.name)
                }
            }
        }
    }
}

@Preview
@Composable
fun ListTaskScreenViewPreview() {
    ListTaskScreenView()
}