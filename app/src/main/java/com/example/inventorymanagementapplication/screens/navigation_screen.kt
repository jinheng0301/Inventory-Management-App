package com.example.inventorymanagementapplication.screens

import AppDatabase
import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.inventorymanagementapplication.R

enum class NavigationScreen(@StringRes val title: Int) {
    Start(R.string.app_name),
    Home(R.string.home),
    Add(R.string.add_item),
    ItemDetail(R.string.item_detail)
}

val LocalAppDatabase = compositionLocalOf<AppDatabase> { error("AppDatabase not provided") }

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun NavigationScreen(
//    navController: NavHostController = rememberNavController(),
//) {
//    val backStackEntry by navController.currentBackStackEntryAsState()
//    val currentScreen = try {
//        NavigationScreen.valueOf(backStackEntry?.destination?.route ?: NavigationScreen.Start.name)
//    } catch (e: IllegalArgumentException) {
//        NavigationScreen.Start
//    }
//
//    // Initialize AppDatabase instance
//    val context = LocalContext.current.applicationContext
//    val appDatabase = remember { AppDatabase.getDatabase(context) }
//
//    CompositionLocalProvider(LocalAppDatabase provides appDatabase) {
//        Scaffold(
//            bottomBar = {
//                if (currentScreen != NavigationScreen.Start) {
//                    BottomNavigationBar(navController)  // Removed appDatabase parameter
//                }
//            }
//        ) { innerPadding ->
//            NavHost(
//                navController = navController,
//                startDestination = NavigationScreen.Start.name,
//                modifier = Modifier.padding(innerPadding)
//            ) {
//                composable(route = NavigationScreen.Start.name) {
//                    StartScreen(
//                        onNextButtonClicked = {
//                            navController.navigate(NavigationScreen.Home.name) {
//                                popUpTo(NavigationScreen.Start.name) {
//                                    inclusive = true
//                                }
//                            }
//                        }
//                    )
//                }
//                composable(route = NavigationScreen.Home.name) {
//                    HomeScreen(
//                        onNextButtonClicked = {},
//                        modifier = Modifier.fillMaxSize(),
//                        navigateToAddEdit = {
//                            navController.navigate(NavigationScreen.Add.name)
//                        }
//                    )
//                }
//
//                composable(route = NavigationScreen.Add.name) {
//                    AddEditScreen(
//                        onNextButtonClicked = {},
//                        modifier = Modifier.fillMaxSize(),
//                        onNavigateToHome = {
//                            navController.navigate(NavigationScreen.Home.name)
//                        }
//                    )
//                }
//
//                composable(route = NavigationScreen.ItemDetail.name) {
//                    ItemDetailScreen(
//                        onNextButtonClicked = {},
//                        modifier = Modifier.fillMaxSize()
//                    )
//                }
//            }
//        }
//    }
//}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationScreen(
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = NavigationScreen.valueOf(
        backStackEntry?.destination?.route ?: NavigationScreen.Start.name
    )

    Scaffold(
        bottomBar = {
            if (currentScreen != NavigationScreen.Start) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = NavigationScreen.Start.name) {
                StartScreen(
                    onNextButtonClicked = {
                        navController.navigate(NavigationScreen.Home.name) {
                            popUpTo(NavigationScreen.Start.name) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(route = NavigationScreen.Home.name) {
                HomeScreen(
                    onNextButtonClicked = {},
                    modifier = Modifier.fillMaxSize(),
                    navigateToAddEdit = {
                        navController.navigate(NavigationScreen.Add.name)
                    }
                )
            }
            composable(route = NavigationScreen.Add.name) {
                AddEditScreen(
                    onNextButtonClicked = {},
                    modifier = Modifier.fillMaxSize(),
                    onNavigateToHome = {
                        navController.navigate(NavigationScreen.Home.name)
                    }
                )
            }
            composable(route = NavigationScreen.ItemDetail.name) {
                ItemDetailScreen(
                    onNextButtonClicked = {},
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {  // Removed appDatabase parameter
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val items = listOf(
            NavigationScreen.Home to Icons.Filled.Home,
            NavigationScreen.Add to Icons.Filled.Add,
            NavigationScreen.ItemDetail to Icons.Filled.Info,
        )

        items.forEach { (screen, icon) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = null) },
                label = { Text(stringResource(screen.title)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.name } == true,
                onClick = {
                    navController.navigate(screen.name) {
                        popUpTo(navController.graph.startDestinationId) {
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