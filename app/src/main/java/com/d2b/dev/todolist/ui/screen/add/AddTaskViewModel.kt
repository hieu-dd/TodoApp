package com.d2b.dev.todolist.ui.screen.add

import com.d2b.dev.todolist.data.model.Task
import com.d2b.dev.todolist.service.TaskManager
import com.d2b.dev.todolist.ui.BaseViewModel
import com.d2b.dev.todolist.utils.isOutDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskManager: TaskManager,
) : BaseViewModel() {

    fun addTask(name: String, note: String, due: Instant, onSuccess: () -> Unit) {
        safelyLaunch {
            val now = Clock.System.now()
            val errorMessage = when {
                name.isBlank() -> "Name can't empty"
                note.isBlank() -> "Note can't empty"
                due.isOutDate() -> "Due can't be in pass"
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
                onSuccess()
            }
        }
    }
}