package app.abhishekgarala.playdeck.features.movie.presentation.userListScreen

data class UserListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val showAddUserDialog: Boolean = false
)