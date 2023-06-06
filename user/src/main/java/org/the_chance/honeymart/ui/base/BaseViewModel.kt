package org.the_chance.honeymart.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T>(initialState: T) : ViewModel() {


    abstract val TAG: String
    protected open fun log(message: String) {
        Log.v(TAG, message)
    }

    protected val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<T> = _uiState


    protected fun <T, R> tryToExecute(
        function: suspend () -> List<T>,
        transform: (T) -> R,
        onSuccess: (List<R>) -> Unit,
        onError: () -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                val result = function().map(transform)
                onSuccess(result)
            } catch (e: Throwable) {
                onError()
            }
        }
    }

}