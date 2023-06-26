package org.the_chance.honeymart.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.the_chance.honeymart.util.EventHandler

abstract class BaseViewModel<T, E>(initialState: T) : ViewModel() {

    abstract val TAG: String
    protected open fun log(message: String) {
        Log.e(TAG, message)
    }

    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected val _effect = MutableSharedFlow<EventHandler<E>>()
    val effect = _effect.asSharedFlow()

    private var job: Job? = null

    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (t: Exception) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                val result = function()
                log("tryToExecute:$result ")
                onSuccess(result)
            } catch (e: Exception) {
                log("tryToExecute error: ${e.message}")
                onError(e)
            }
            log("tryToExecute: job: $job")
        }
    }


    protected fun <T> tryToExecuteDebounced(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (t: Exception) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {
        job?.cancel()
        job = viewModelScope.launch(dispatcher) {
            try {
                delay(2000)//2s
                val result = function()
                log("tryToExecute:$result ")
                onSuccess(result)

            } catch (e: Exception) {
                log("tryToExecute error: ${e.message}")
                onError(e)
            }
        }
        log("job isCompleted : ${job?.isCompleted} ")
        log("job isCancelled : ${job?.isCancelled} ")
        log("tryToExecute: job: $job")
    }

}

