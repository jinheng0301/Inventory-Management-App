package com.example.inventorymanagementapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.inventorymanagementapplication.screens.NavigationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        // Create an instance of InventoryViewModel
        //val inventoryViewModel = ViewModelProvider(this)[InventoryViewModel::class.java]

        setContent {
            NavigationScreen()
            //InventoryManagementApplicationTheme {
                // Pass the InventoryViewModel to NavigationScreen
                //NavigationScreen(inventoryViewModel = inventoryViewModel)
           // }
        }
    }
}
