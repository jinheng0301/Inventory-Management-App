import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymanagementapplication.data.InventoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InventoryViewModel(application: Application) : AndroidViewModel(application) {
    private val inventoryDao = DatabaseProvider.getDatabase(application).inventoryDao()

    // StateFlow to hold inventory items
    private val _inventoryItems = MutableStateFlow<List<InventoryItem>>(emptyList())
    val inventoryItems: StateFlow<List<InventoryItem>> = _inventoryItems.asStateFlow()

    // Fetch items from the database
    fun fetchItems() {
        viewModelScope.launch {
            _inventoryItems.value = inventoryDao.getAllItems().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = emptyList()
            ).value
        }
    }

    //Retrieve one item
    suspend fun getOneItem(name: String): InventoryItem? {
        return inventoryDao.getOneItem(name) // Example repository function
    }

    // Insert item into the database
    fun insertItem(item: InventoryItem) {
        viewModelScope.launch {
            inventoryDao.insertItem(item)
        }
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
    fun allItems(): StateFlow<List<InventoryItem>> {
        return inventoryDao.getAllItems()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = emptyList()
            )
    }

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


