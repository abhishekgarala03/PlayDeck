package app.abhishekgarala.playdeck.di

import androidx.room.Room
import android.content.Context
import app.abhishekgarala.playdeck.core.database.AppDatabase
import app.abhishekgarala.playdeck.core.database.dao.PendingUserDao
import app.abhishekgarala.playdeck.core.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()

    @Provides
    fun providePendingUserDao(database: AppDatabase): PendingUserDao = database.pendingUserDao()
}