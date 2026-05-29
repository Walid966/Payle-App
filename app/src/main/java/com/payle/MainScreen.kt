package com.payle

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.payle.ui.theme.PayleTheme

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Posts : Screen("posts", "البوستات", Icons.Default.Edit)
    object Replies : Screen("replies", "الردود", Icons.Default.Chat)
    object Profit : Screen("profit", "المكسب", Icons.Default.AttachMoney)
    object Images : Screen("images", "الصور", Icons.Default.Image)
    object Debts : Screen("debts", "الديون", Icons.Default.DateRange)
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Posts,
        Screen.Replies,
        Screen.Profit,
        Screen.Images,
        Screen.Debts
    )

    PayleTheme {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Posts.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Posts.route) { PostsScreen() }
                composable(Screen.Replies.route) { RepliesScreen() }
                composable(Screen.Profit.route) { ProfitScreen() }
                composable(Screen.Images.route) { ImagesScreen() }
                composable(Screen.Debts.route) { DebtsScreen() }
            }
        }
    }
}
