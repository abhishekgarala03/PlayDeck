package app.abhishekgarala.playdeck.core.database.dao

// PendingUserDao.kt
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import app.abhishekgarala.playdeck.core.database.model.PendingUser
import kotlinx.coroutines.flow.Flow

@Dao
interface PendingUserDao {
    @Query("SELECT * FROM pending_users ORDER BY createdAt ASC")
    fun getAllPendingUsers(): Flow<List<PendingUser>>

    @Insert
    suspend fun insertPendingUser(user: PendingUser)

    @Delete
    suspend fun deletePendingUser(user: PendingUser)

    @Query("DELETE FROM pending_users")
    suspend fun clearPendingUsers()
}