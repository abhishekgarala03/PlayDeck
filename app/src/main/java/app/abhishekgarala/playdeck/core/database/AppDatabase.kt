package app.abhishekgarala.playdeck.core.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import app.abhishekgarala.playdeck.core.database.dao.PendingUserDao
import app.abhishekgarala.playdeck.core.database.dao.UserDao
import app.abhishekgarala.playdeck.core.database.model.PendingUser
import app.abhishekgarala.playdeck.core.database.model.User

@Database(
    entities = [User::class, PendingUser::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pendingUserDao(): PendingUserDao
}