import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.inventorymanagementapplication.data.InventoryItem

object DatabaseProvider {
    @Volatile
    private var INSTANCE: InventoryDatabase? = null

    fun getDatabase(context: Context): InventoryDatabase {
        return INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(context.applicationContext, InventoryDatabase::class.java, "inventory_database")
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }

        }
    }
}

@Database(entities = [InventoryItem::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao
}