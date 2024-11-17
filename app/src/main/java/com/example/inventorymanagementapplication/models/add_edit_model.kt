package com.example.inventorymanagementapplication.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymanagementapplication.data.InventoryItem
import kotlinx.coroutines.launch

class InventoryViewModel(application: Application) : AndroidViewModel(application) {
    private val inventoryDao = DatabaseProvider.getDatabase(application).inventoryDao()

    // Insert item into the database
    suspend fun insertItem(item: InventoryItem) {
        viewModelScope.launch {
            inventoryDao.insertItem(item)
        }
    }

    //Retrieve one item
    suspend fun getOneItem(name: String): InventoryItem? {
        return inventoryDao.getOneItem(name) // Example repository function
    }

    suspend fun update(item: InventoryItem)
    {
        viewModelScope.launch {
            inventoryDao.update(item)
        }
    }

    suspend fun getHighestId():Int?{
        return inventoryDao.getHighestId()
    }


    // Retrieve all items
    suspend fun getAllItems() = inventoryDao.getAllItems()

    // Delete an item by its ID
    suspend fun deleteItem(name:String): Result<Boolean> {
        return try {
            inventoryDao.deleteItem(name)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}

