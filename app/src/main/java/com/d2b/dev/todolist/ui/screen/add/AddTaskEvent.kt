package com.d2b.dev.todolist.ui.screen.add

import com.d2b.dev.todolist.data.model.Task
import kotlinx.datetime.Instant

sealed class AddTaskEvent {
    object Back : AddTaskEvent()
    data class AddTask(
        val name: String,
        val note: String,
        val dueDate: Instant,
    ) : AddTaskEvent()

    data class ShowDatePicker(
        val dueDate: Instant,
        val onSelect: (Instant) -> Unit
    ) : AddTaskEvent()
}
