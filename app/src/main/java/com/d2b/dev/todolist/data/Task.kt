package com.d2b.dev.todolist.data

import java.time.Instant

data class Task(
    val name: String,
    val note: String,
    val dueDate: Instant,
    val status: TaskStatus
) {
    enum class TaskStatus {
        Complete,
        Incomplete
    }
}