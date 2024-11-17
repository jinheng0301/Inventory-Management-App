package com.example.inventorymanagementapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.inventorymanagementapplication.models.InventoryViewModel
import com.example.inventorymanagementapplication.screens.NavigationScreen
import com.example.inventorymanagementapplication.ui.theme.InventoryManagementApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Create an instance of InventoryViewModel
        val inventoryViewModel = ViewModelProvider(this)[InventoryViewModel::class.java]

        setContent {
            InventoryManagementApplicationTheme {
                // Pass the InventoryViewModel to NavigationScreen
                NavigationScreen(inventoryViewModel = inventoryViewModel)
            }
        }
    }
}
