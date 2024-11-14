package com.example.inventorymanagementapplication.models

import android.content.Context
import com.example.inventorymanagementapplication.data.ItemDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEditModel(context: Context) {
    private val inventoryDao = AppDatabase.getDatabase(context).inventoryDao()

    // Save the item using Room
    fun saveItem(item: ItemDetail, onSaved: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                inventoryDao.insertItem(item)
                onSaved(true)
            } catch (e: Exception) {
                e.printStackTrace() // Log for debugging
                onSaved(false)
            }
        }
    }
}
