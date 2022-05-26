package com.d2b.dev.todolist.ui.screen.todo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d2b.dev.todolist.data.model.Task
import com.d2b.dev.todolist.utils.DateTime
import kotlinx.datetime.Clock

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
    }.sortedBy { it.dueDate }
    ListTaskScreenContent(typeScreen = typeScreen,
        displayTasks = displayTasks,
        handleEvent = { event ->
            when (event) {
                is TaskEvent.ToggleStatusTask -> {
                    viewModel.toggleStatusTask(event.id, event.isComplete)
                }
                is TaskEvent.DeleteTask -> {

                }
            }
        }
    )
}

@Composable
fun ListTaskScreenContent(
    typeScreen: TypeScreen,
    displayTasks: List<Task>,
    handleEvent: (TaskEvent) -> Unit
) {
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
        val days = displayTasks
            .map { DateTime.formatDate(it.dueDate) }
            .distinct()
        LazyColumn(modifier = Modifier.padding(12.dp)) {
            days.forEach { date ->
                item() {
                    Text(
                        date,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
                displayTasks.filter { DateTime.formatDate(it.dueDate) == date }.forEach {
                    item(key = it.id) {
                        TaskItemView(task = it) { isComplete ->
                            handleEvent(TaskEvent.ToggleStatusTask(it.id, isComplete))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItemView(task: Task, onToggleTask: (Boolean) -> Unit) {
    Card(
        modifier = Modifier
            .padding(bottom = 6.dp)
            .fillMaxWidth(),
        elevation = 6.dp,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = task.status == Task.TaskStatus.Complete, onCheckedChange = {
                onToggleTask(it)
            })
            Spacer(modifier = Modifier.width(12.dp))
            Column() {
                Text(task.name, style = MaterialTheme.typography.body1)
                Text(task.note, style = MaterialTheme.typography.caption)
            }
        }
    }
}

@Preview
@Composable
fun ListTaskScreenViewPreview() {
    ListTaskScreenView()
}

@Preview
@Composable
fun TaskItemPreview() {
    TaskItemView(task = Task("", "1234", "1234", Clock.System.now()), {})
}