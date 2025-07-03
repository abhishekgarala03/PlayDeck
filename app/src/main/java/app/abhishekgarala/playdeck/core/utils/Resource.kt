package app.abhishekgarala.playdeck.core.utils

import app.abhishekgarala.playdeck.core.utils.extension.Empty

sealed class Resource<out T> {
    data class Success<out R>(val data: R) : Resource<R>()

    data class Error(val message: String = String.Empty) : Resource<Nothing>()

    data object Loading : Resource<Nothing>()
}