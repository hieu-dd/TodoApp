package com.d2b.dev.todolist.ui.screen.home

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.d2b.dev.todolist.R
import com.d2b.dev.todolist.ui.screen.navitem.BottomNavItem
import com.d2b.dev.todolist.ui.screen.navitem.MainNavigationItem
import com.d2b.dev.todolist.ui.screen.todo.ListTaskScreenView
import com.d2b.dev.todolist.ui.screen.todo.TypeScreen

@Composable
fun HomeScreenView(mainNavController: NavController) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = Color.White,
                onClick = {
                    mainNavController.navigate(MainNavigationItem.Add.route)
                }) {
                Icon(Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colors.primary)
            }
        }
    ) {
        NavigationGraph(navController = navController)
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Complete,
        BottomNavItem.Incomplete,
    )
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp
                    )
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            ListTaskScreenView(typeScreen = TypeScreen.All)
        }
        composable(BottomNavItem.Complete.screen_route) {
            ListTaskScreenView(typeScreen = TypeScreen.Complete)
        }
        composable(BottomNavItem.Incomplete.screen_route) {
            ListTaskScreenView(typeScreen = TypeScreen.Incomplete)
        }
    }
}