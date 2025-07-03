package app.abhishekgarala.playdeck.core.presentation

import android.os.Parcelable

sealed interface UiEvent {
    data object Success : UiEvent
    data class ShowToast(val message: String) : UiEvent
    data class ShowSnackBar(val message: String) : UiEvent
    data class ShowLoading(val message: String? = null) : UiEvent
    data class Navigate(
        val route: String,
        val data: Parcelable? = null,
        val key: String? = null
    ) : UiEvent

    data class NavigateAndRemoveBackStack(
        val route: String,
        val data: Parcelable? = null,
        val key: String? = null
    ) :
        UiEvent

    data object NavigateBack : UiEvent
}