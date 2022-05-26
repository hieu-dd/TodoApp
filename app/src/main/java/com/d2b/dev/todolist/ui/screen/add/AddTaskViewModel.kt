package com.d2b.dev.todolist.ui.screen.add

import androidx.lifecycle.viewModelScope
import com.d2b.dev.todolist.data.model.Task
import com.d2b.dev.todolist.service.TaskManager
import com.d2b.dev.todolist.ui.BaseViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.lang.Exception

class AddTaskViewModel : BaseViewModel() {
    private val taskManager = TaskManager.Instance

    fun addTask(name: String, note: String, due: Instant) {
        safelyLaunch {
            val now = Clock.System.now()
            val errorMessage = when {
                name.isBlank() -> "Name can't empty"
                note.isBlank() -> "Note can't empty"
                due < now -> "Due can't be in pass"
                else -> null
            }
            errorMessage?.let {
                sendError(Exception(it))
            } ?: run {
                taskManager.addNewTask(
                    Task(
                        id = now.epochSeconds.toString(),
                        name = name,
                        note = note,
                        dueDate = due,
                    )
                )
            }
        }
    }
}