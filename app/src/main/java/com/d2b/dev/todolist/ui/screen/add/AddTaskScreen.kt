package com.d2b.dev.todolist.ui.screen.add

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.d2b.dev.todolist.utils.DateTime
import com.d2b.dev.todolist.utils.showDatePicker
import kotlinx.datetime.Clock

@Composable
fun AddTaskScreenView(
    navController: NavController,
    viewModel: AddTaskViewModel = AddTaskViewModel()
) {
    val context = LocalContext.current

    val dateInteractionSource = remember {
        MutableInteractionSource()
    }
    val error by viewModel.error.collectAsState(null)

    var taskName by remember {
        mutableStateOf("")
    }
    var note by remember {
        mutableStateOf("")
    }
    var dueDate by remember {
        mutableStateOf(Clock.System.now())
    }

    if (dateInteractionSource.collectIsPressedAsState().value) {
        context.showDatePicker(dueDate) { dueDate = it }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text("New Tasks", style = MaterialTheme.typography.h6)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.addTask(name = taskName, note = note, due = dueDate)
                },
                backgroundColor = Color.White,
            ) {
                Icon(Icons.Default.Done, contentDescription = null, tint = MaterialTheme.colors.primary)
            }
        }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("What is to be done?", style = MaterialTheme.typography.h6)
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = taskName,
                onValueChange = {
                    taskName = it
                },
                placeholder = {
                    Text("Enter task name")
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("Note", style = MaterialTheme.typography.h6)
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = note,
                onValueChange = {
                    note = it
                },
                placeholder = {
                    Text("Enter note for this task")
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("Due date", style = MaterialTheme.typography.h6)
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = DateTime.formatDate(dueDate),
                readOnly = true,
                onValueChange = {},
                placeholder = {
                    Text("Date not set")
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                ),
                interactionSource = dateInteractionSource
            )
        }
        error?.let {
            AlertDialog(
                onDismissRequest = { viewModel.clearError() },
                title = { Text("Error") },
                text = { Text(it.message.orEmpty()) },
                buttons = {},
            )
        }
    }
}

@Preview
@Composable
fun AddTaskScreenViewPreview() {
    AddTaskScreenView(navController = rememberNavController())
}