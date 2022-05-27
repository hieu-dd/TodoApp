package com.d2b.dev.todolist.ui.screen.todo

import com.d2b.dev.todolist.data.model.Task
import com.d2b.dev.todolist.service.TaskManager
import com.d2b.dev.todolist.ui.BaseViewModel
import kotlinx.coroutines.flow.StateFlow

class ListTaskViewModel : BaseViewModel() {
    private val taskManager = TaskManager.Instance

    fun getTasksFlow(): StateFlow<List<Task>> = taskManager.tasks

    fun toggleStatusTask(id: String, isComplete: Boolean, onSuccess: () -> Unit) {
        safelyLaunch {
            taskManager.updateStatusTask(id, isComplete)
            onSuccess()
        }
    }
}