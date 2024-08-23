package com.example.moviesapp.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Output
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.screens.MoviesViewModel
import com.example.moviesapp.screens.favorites.FavoritesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MainScreen(rootNav: NavHostController,favoritesViewModel: FavoritesViewModel, moviesViewModel: MoviesViewModel) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("sp", Context.MODE_PRIVATE)
    val lastConnection = sharedPreferences.getString("connection", "")
    ModalDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Column(modifier = Modifier.padding(horizontal = 30.dp)) {
                Text(
                    text = "Movie App",
                    modifier = Modifier.padding(top = 30.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
                Button(
                    onClick = {
                        deleteUserId(context)
                        rootNav.apply {
                            val route = "login"
                            navigate(route = route) {
                                popUpTo(graph.startDestinationId) { inclusive = true }
                            }
                            graph.setStartDestination(route)
                        }
                    },
                    modifier = Modifier.padding(top = 30.dp)
                )
                {
                    Text(text = "Logout", style = MaterialTheme.typography.bodyMedium)
                }
                Text(text = "Last connection: $lastConnection", style = MaterialTheme.typography.bodyMedium)
            }
        },
        content = {
            Column {
                TopAppBar(
                    title = { Text(text = "") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isOpen) {
                                        drawerState.close()
                                    } else {
                                        drawerState.open()
                                    }
                                }

                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Output,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
                Scaffold(
                    bottomBar = { BottomBar(navHostController = navController) },
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        BottomNavGraph(navHostController = navController, favoritesViewModel, moviesViewModel)
                    }

                }
            }
        }
    )
}

@Composable
fun BottomBar(navHostController: NavHostController) {
    val screens = listOf(
        BottomNavItem.SearchMovie,
        BottomNavItem.Favorites
    )

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navHostController,
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavItem,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        icon = {
            Icon(
                imageVector = screen.icon, contentDescription = "Navigation Icon",
                modifier = Modifier.size(35.dp)
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route)
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.onBackground,
            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
            indicatorColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

private fun deleteUserId(context: Context) {
    val sharedPreferences = context.getSharedPreferences("sp", Context.MODE_PRIVATE)
    sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
    sharedPreferences.edit().putInt("userId", 0).apply()
}