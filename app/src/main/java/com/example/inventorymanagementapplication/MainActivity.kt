package com.example.inventorymanagementapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.inventorymanagementapplication.screens.NavigationScreen
import com.example.inventorymanagementapplication.ui.theme.InventoryManagementApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InventoryManagementApplicationTheme {
                NavigationScreen()
            }
        }
    }
}
