package com.example.inventorymanagementapplication.screens

import InventoryViewModel
import android.annotation.SuppressLint
import android.app.Application
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.inventorymanagementapplication.R
import com.example.project.ui.screens.HomeScreen

enum class NavigationScreen(@StringRes val title: Int) {
    Start(R.string.app_name),
    Home(R.string.home),
    Add(R.string.add_item),
    ItemDetail(R.string.item_detail)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationScreen(
    navController: NavHostController = rememberNavController(),
    //inventoryViewModel: InventoryViewModel
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val context = LocalContext.current
    val application = context.applicationContext as Application

    val inventoryViewModel: InventoryViewModel = viewModel(
        factory = InventoryViewModelFactory(application)
    )
    val currentScreen = try {
        NavigationScreen.valueOf(backStackEntry?.destination?.route ?: NavigationScreen.Start.name)
    } catch (e: IllegalArgumentException) {
        NavigationScreen.Start
    }


        Scaffold(
            bottomBar = {
                if (currentScreen != NavigationScreen.Start) {
                    BottomNavigationBar(navController)  // Removed appDatabase parameter
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
                        onNextButtonClicked = { id ->
                            navController.navigate("${NavigationScreen.ItemDetail.name}/$id")
                        },
                        modifier = Modifier.fillMaxSize(),
                        inventoryViewModel = inventoryViewModel,
                        navigateToAddEdit = {
                            navController.navigate(NavigationScreen.Add.name)
                        }
                    )
                }

                composable(route = NavigationScreen.Add.name) {
                    AddEditScreen(
                        onNextButtonClicked = {},
                        modifier = Modifier.fillMaxSize(),
                        InventoryViewModel = inventoryViewModel,
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

class InventoryViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
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
