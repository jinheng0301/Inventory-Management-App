package com.example.inventorymanagementapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Data class for item details
@Entity(tableName = "inventory_items")
data class InventoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String,
    val quantity: Int,
    val price: Double,
    val minimumStock: Int,
    val location: String,
    val description: String
)