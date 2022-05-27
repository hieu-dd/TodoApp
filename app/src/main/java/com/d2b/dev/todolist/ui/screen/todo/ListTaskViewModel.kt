package com.d2b.dev.todolist.ui.screen.todo

import com.d2b.dev.todolist.data.model.Task
import com.d2b.dev.todolist.service.TaskManager
import com.d2b.dev.todolist.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ListTaskViewModel @Inject constructor(
    private val taskManager: TaskManager,
) : BaseViewModel() {

    fun getTasksFlow(): StateFlow<List<Task>> = taskManager.tasks

    fun toggleStatusTask(id: String, isComplete: Boolean, onSuccess: () -> Unit) {
        safelyLaunch {
            taskManager.updateStatusTask(id, isComplete)
            onSuccess()
        }
    }
}