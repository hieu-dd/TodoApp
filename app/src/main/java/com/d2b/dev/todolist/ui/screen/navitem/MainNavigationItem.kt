package com.d2b.dev.todolist.ui.screen.navitem

sealed class MainNavigationItem(val route: String) {
    object Home : MainNavigationItem("home")
    object Add : MainNavigationItem("add")
}