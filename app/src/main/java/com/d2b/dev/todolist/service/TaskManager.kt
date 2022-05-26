package com.d2b.dev.todolist.service

import com.d2b.dev.todolist.data.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TaskManager : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = job

    private val job = Dispatchers.Main.immediate + SupervisorJob()

    private val _tasks = MutableStateFlow<MutableList<Task>>(mutableListOf())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun addNewTask(task: Task) {
        launch {
            _tasks.emit(_tasks.value.apply {
                add(task)
            })
        }
    }
}