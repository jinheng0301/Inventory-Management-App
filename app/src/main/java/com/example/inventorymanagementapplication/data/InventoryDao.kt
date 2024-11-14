import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.inventorymanagementapplication.data.ItemDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {
    @Insert
    suspend fun insertItem(item: ItemDetail)

    @Query("SELECT * FROM item_detail")
    fun getAllItems(): Flow<List<ItemDetail>>

    @Query("SELECT * FROM item_detail WHERE id = :id")
    suspend fun getItemById(id: Int): ItemDetail
}

