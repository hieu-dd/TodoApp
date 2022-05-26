package com.d2b.dev.todolist.ui.screen.todo

sealed class TaskEvent {
    data class ToggleStatusTask(val id: String, val isComplete: Boolean) : TaskEvent()
    data class DeleteTask(val id: String) : TaskEvent()
}