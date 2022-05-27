package com.d2b.dev.todolist.ui.screen.todo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.d2b.dev.todolist.R
import com.d2b.dev.todolist.data.model.Task
import com.d2b.dev.todolist.utils.formatDate
import com.d2b.dev.todolist.utils.isOutDate
import com.d2b.dev.todolist.utils.showToast
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Composable
fun ListTaskScreenView(
    typeScreen: TypeScreen = TypeScreen.All,
    viewModel: ListTaskViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

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
                    viewModel.toggleStatusTask(event.id, event.isComplete) {
                        context.showToast("Task is move to ${if (event.isComplete) "complete" else "incomplete"}")
                    }
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
            .map { it.dueDate }
            .distinctBy { it.formatDate() }
        if (displayTasks.isEmpty()) {
            EmptyTasks()
        } else {
            LazyColumn(modifier = Modifier.padding(12.dp)) {
                days.forEach { date ->
                    item() {
                        TaskItemTitle(date)
                    }
                    displayTasks.filter { it.dueDate.formatDate() == date.formatDate() }.forEach {
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
}

@Composable
fun EmptyTasks() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.searching
            ),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text("Nothing to do")
    }
}

@Composable
fun TaskItemView(task: Task, onToggleTask: (Boolean) -> Unit) {
    val now = Clock.System.now()
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
            Checkbox(
                checked = task.status == Task.TaskStatus.Complete,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.primary
                ),
                onCheckedChange = {
                    onToggleTask(it)
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column() {
                Text(task.name, style = MaterialTheme.typography.body1)
                Text(task.note, style = MaterialTheme.typography.caption)
            }
            if (task.dueDate.isOutDate()) {
                Spacer(modifier = Modifier.weight(1F))
                Text("Out date", color = Color.Red)
            }
        }
    }
}

@Composable
fun TaskItemTitle(date: Instant) {
    Text(
        date.formatDate(),
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(bottom = 12.dp),
        color = if (date.isOutDate()) Color.Red else Color.Black
    )
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