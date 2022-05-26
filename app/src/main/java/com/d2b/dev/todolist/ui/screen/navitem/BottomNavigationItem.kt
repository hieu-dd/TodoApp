package com.d2b.dev.todolist.ui.screen.navitem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title: String, var icon: ImageVector, var screen_route: String) {
    object Home : BottomNavItem("All", Icons.Default.List, "all")
    object Complete : BottomNavItem("Complete", Icons.Default.Done, "complete")
    object Incomplete : BottomNavItem("Incomplete", Icons.Default.Warning, "incomplete")
}