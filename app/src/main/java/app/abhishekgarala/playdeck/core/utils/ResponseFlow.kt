package app.abhishekgarala.playdeck.core.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

inline fun <reified T> responseFlow(
    crossinline call: suspend () -> Resource<T>
): Flow<Resource<T>> = flow {
    emit(Resource.Loading)
    emit(call())
}.flowOn(Dispatchers.IO)