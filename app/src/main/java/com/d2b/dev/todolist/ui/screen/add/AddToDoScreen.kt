package com.d2b.dev.todolist.ui.screen.add

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.d2b.dev.todolist.MainViewModel

@Composable
fun AddToDoScreenView(
    viewModel: MainViewModel
) {
    Scaffold() {
        Column {
            Text(text = "Add")
        }
    }
}