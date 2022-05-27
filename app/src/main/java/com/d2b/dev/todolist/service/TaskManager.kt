package com.d2b.dev.todolist.service

import androidx.annotation.VisibleForTesting
import com.d2b.dev.todolist.data.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.coroutines.CoroutineContext

class TaskManager : CoroutineScope {

    companion object {
        val Instance: TaskManager by lazy { TaskManager() }
    }

    override val coroutineContext: CoroutineContext
        get() = job

    private val job = Dispatchers.Main.immediate + SupervisorJob()

    private val _tasks = MutableStateFlow<MutableList<Task>>(mutableListOf())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    suspend fun addNewTask(task: Task) {
        _tasks.emit(_tasks.value.apply {
            add(task)
        })
    }

    suspend fun updateStatusTask(taskId: String, isComplete: Boolean) {
        val newTasks = copyAndUpdateTasks(_tasks.value, taskId) {
            it.apply {
                status = if (isComplete) Task.TaskStatus.Complete else Task.TaskStatus.Incomplete
            }
        }
        _tasks.emit(newTasks.toMutableList())
    }

    private fun copyAndUpdateTasks(
        tasks: List<Task>,
        taskId: String,
        block: (Task) -> Task
    ): List<Task> {
        val result = tasks.map {
            if (it.id == taskId) {
                block(it.copy())
            } else {
                it.copy()
            }
        }
        return result
    }

    suspend fun clearTasks() {
        _tasks.emit(mutableListOf())
    }
}