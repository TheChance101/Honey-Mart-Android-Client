package org.the_chance.honeymart.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.the_chance.honeymart.ui.util.EventHandler

abstract class BaseViewModel<T, E>(initialState: T) : ViewModel() {


    abstract val TAG: String
    protected open fun log(message: String) {
        Log.v(TAG, message)
    }

    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected val _effect = MutableSharedFlow<EventHandler<E>>()
    val effect = _effect.asSharedFlow()


    protected fun <T, R> tryToExecute(
        function: suspend () -> List<T>,
        transform: (T) -> R,
        onSuccess: (List<R>) -> Unit,
        onError: (t: Throwable) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                val result = function().map(transform)
                Log.e("TAG", "tryToExecute:$result ")
                onSuccess(result)
            } catch (e: Throwable) {
                Log.e("TAG", "tryToExecute error: ${e.message}")
                onError(e)
            }
        }
    }

}