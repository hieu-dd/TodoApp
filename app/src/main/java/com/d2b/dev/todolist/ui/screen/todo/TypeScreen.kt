package com.d2b.dev.todolist.ui.screen.todo

sealed class TypeScreen(val title: String) {
    object All : TypeScreen("All Tasks")
    object Complete : TypeScreen("Complete Tasks")
    object Incomplete : TypeScreen("Incomplete Tasks")
}
