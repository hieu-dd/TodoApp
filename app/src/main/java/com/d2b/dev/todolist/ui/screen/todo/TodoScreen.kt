package com.d2b.dev.todolist.ui.screen.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun TodoScreenView(text: String) {
    Scaffold() {
        Column() {
            Text(text)
        }
    }
}