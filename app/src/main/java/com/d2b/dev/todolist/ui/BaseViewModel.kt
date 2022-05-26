package com.d2b.dev.todolist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {


    protected open val viewModelCoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            sendError(Exception(throwable.message))
        }
    }

    fun safelyLaunch(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(viewModelCoroutineExceptionHandler) { block() }

    private val _error: MutableSharedFlow<Exception?> = MutableSharedFlow()
    val error: SharedFlow<Exception?> = _error.asSharedFlow()

    open fun sendError(e: Exception) {
        safelyLaunch {
            _error.emit(e)
        }
    }

    open fun clearError() {
        safelyLaunch {
            _error.emit(null)
        }
    }
}