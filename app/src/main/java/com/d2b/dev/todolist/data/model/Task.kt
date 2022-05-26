package com.d2b.dev.todolist.data.model

import kotlinx.datetime.Instant

data class Task(
    val id: String,
    val name: String,
    val note: String,
    val dueDate: Instant,
    var status: TaskStatus = TaskStatus.Incomplete
) {
    enum class TaskStatus {
        Complete,
        Incomplete
    }
}