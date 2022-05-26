package com.d2b.dev.todolist.data

sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val throwable: Exception) : DataState<Nothing>()
    object Idle : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}

inline fun <T, R> DataState<T>.map(transform: (T) -> R): DataState<R> {
    return when (this) {
        is DataState.Idle -> this
        is DataState.Loading -> this
        is DataState.Error -> this
        is DataState.Success<*> -> DataState.Success(transform(data as T))
    }
}

inline fun <T> DataState<T>.getOrNull() = if (this is DataState.Success) this.data else null
inline fun <T> DataState<T>.exceptionOrNull() = if (this is DataState.Error) this.throwable else null