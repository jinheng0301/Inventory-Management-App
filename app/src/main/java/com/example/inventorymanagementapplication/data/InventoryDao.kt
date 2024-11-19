import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.inventorymanagementapplication.data.InventoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {
    @Query("SELECT MAX(id) FROM inventory_items")
    suspend fun getHighestId(): Int?

    @Update
    suspend fun update(item: InventoryItem)

    @Insert
    fun insertItem(item: InventoryItem)

    @Query("SELECT * FROM inventory_items WHERE name = :name LIMIT 1")
    suspend fun getOneItem(name: String): InventoryItem?

    @Query("SELECT * FROM inventory_items")
    fun getAllItems(): Flow<List<InventoryItem>>

    @Query("DELETE FROM inventory_items WHERE name = :name")
    suspend fun deleteItem(name:String)
}

