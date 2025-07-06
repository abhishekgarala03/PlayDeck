package app.abhishekgarala.playdeck.features.movie.presentation.userListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.abhishekgarala.playdeck.core.database.model.User
import app.abhishekgarala.playdeck.features.movie.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserListState())
    val uiState: StateFlow<UserListState> = _uiState.asStateFlow()

    val users: Flow<PagingData<User>> = userRepository.getUsers()
        .cachedIn(viewModelScope)

    fun showAddUserDialog() {
        _uiState.value = _uiState.value.copy(showAddUserDialog = true)
    }

    fun hideAddUserDialog() {
        _uiState.value = _uiState.value.copy(showAddUserDialog = false)
    }

    fun createUser(name: String, job: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = userRepository.createUser(name, job)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                showAddUserDialog = false,
                error = if (result.isFailure) result.exceptionOrNull()?.message else null
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}