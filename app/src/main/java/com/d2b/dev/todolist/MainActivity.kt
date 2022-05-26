package com.d2b.dev.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.d2b.dev.todolist.ui.screen.add.AddTaskScreenView
import com.d2b.dev.todolist.ui.screen.home.HomeScreenView
import com.d2b.dev.todolist.ui.screen.navitem.MainNavigationItem
import com.d2b.dev.todolist.ui.theme.TodoListTheme

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoListTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = MainNavigationItem.Home.route) {
                    composable(MainNavigationItem.Home.route) { HomeScreenView(mainNavController = navController) }
                    composable(MainNavigationItem.Add.route) { AddTaskScreenView(navController) }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TodoListTheme {
        Greeting("Android")
    }
}