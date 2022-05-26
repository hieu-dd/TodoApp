package com.d2b.dev.todolist.ui.screen.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d2b.dev.todolist.data.model.Task
import com.d2b.dev.todolist.service.TaskManager
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListTaskViewModel : ViewModel() {
    private val taskManager = TaskManager.Instance

    fun getTasksFlow(): StateFlow<List<Task>> = taskManager.tasks

    fun updateTask(id:String){
        viewModelScope.launch {
            taskManager.updateTask(id)
        }
    }
}