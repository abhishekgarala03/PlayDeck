package app.abhishekgarala.playdeck.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.abhishekgarala.playdeck.core.database.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getAllUsers(): PagingSource<Int, User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)

    @Query("DELETE FROM users")
    suspend fun clearUsers()

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int
}