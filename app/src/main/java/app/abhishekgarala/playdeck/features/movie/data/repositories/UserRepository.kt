package app.abhishekgarala.playdeck.features.movie.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import app.abhishekgarala.playdeck.core.database.dao.PendingUserDao
import app.abhishekgarala.playdeck.core.database.dao.UserDao
import app.abhishekgarala.playdeck.core.database.model.PendingUser
import app.abhishekgarala.playdeck.core.database.model.User
import app.abhishekgarala.playdeck.core.database.request.CreateUserRequest
import app.abhishekgarala.playdeck.core.observer.NetworkConnectivityObserver
import app.abhishekgarala.playdeck.core.worker.UserSyncWorker
import app.abhishekgarala.playdeck.features.movie.data.dataSource.UserPagingSource
import app.abhishekgarala.playdeck.features.movie.data.dataSource.remote.UserApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val pendingUserDao: PendingUserDao,
    private val workManager: WorkManager,
    private val networkConnectivityObserver: NetworkConnectivityObserver
) {

    fun getUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UserPagingSource(userApi, userDao)
            }
        ).flow
    }

    suspend fun createUser(name: String, job: String): Result<String> {
        return try {
            if (networkConnectivityObserver.isConnected()) {
                val response = userApi.createUser(request = CreateUserRequest(name, job))
                if (response.isSuccessful) {
                    Result.success("User created successfully")
                } else {
                    // Store offline and schedule sync
                    pendingUserDao.insertPendingUser(PendingUser(name = name, job = job))
                    scheduleUserSync()
                    Result.success("User will be synced when online")
                }
            } else {
                // Store offline and schedule sync
                pendingUserDao.insertPendingUser(PendingUser(name = name, job = job))
                scheduleUserSync()
                Result.success("User will be synced when online")
            }
        } catch (e: Exception) {
            // Store offline and schedule sync
            pendingUserDao.insertPendingUser(PendingUser(name = name, job = job))
            scheduleUserSync()
            Result.success("User will be synced when online")
        }
    }

    private fun scheduleUserSync() {
        val syncWork = OneTimeWorkRequestBuilder<UserSyncWorker>().build()
        workManager.enqueue(syncWork)
    }

    suspend fun syncPendingUsers() {
        val pendingUsers = pendingUserDao.getAllPendingUsers().first()

        for (pendingUser in pendingUsers) {
            try {
                val response = userApi.createUser(
                    request = CreateUserRequest(pendingUser.name, pendingUser.job)
                )
                if (response.isSuccessful) {
                    pendingUserDao.deletePendingUser(pendingUser)
                }
            } catch (e: Exception) {
                // Keep in pending state for next sync attempt
                break
            }
        }
    }
}