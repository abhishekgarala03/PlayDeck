package app.abhishekgarala.playdeck.features.movie.presentation.userListScreen

interface UserListEvent {

    data class SetPage(
        val page: Int? = 1
    ) : UserListEvent

    data object GetUsers : UserListEvent

}