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
import org.the_chance.honeymart.domain.util.AuthenticationException
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.GeneralException
import org.the_chance.honeymart.domain.util.NetworkException
import org.the_chance.honeymart.domain.util.handelAuthenticationException
import org.the_chance.honeymart.domain.util.handelGeneralException
import org.the_chance.honeymart.domain.util.handelNetworkException
import org.the_chance.honeymart.util.EventHandler
import java.io.IOException

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
        onError: (t: ErrorHandler) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                val result = function()
                log("tryToExecute:$result ")
                onSuccess(result)
            } catch (exception: GeneralException) {
                handelGeneralException(exception, onError)
                log("tryToExecute error GeneralException: ${exception}")
            } catch (exception: NetworkException) {
                handelNetworkException(exception, onError)
                log("tryToExecute error NetworkException: ${exception}")
            } catch (exception: AuthenticationException) {
                handelAuthenticationException(exception, onError)
                log("tryToExecute error AuthenticationException: ${exception}")
            } catch (exception: IOException) {
                log("tryToExecute error IOException: ${exception}")
                onError(ErrorHandler.NoConnection)
            } catch (exception: Exception) {
                log("tryToExecute error Exception: ${exception}")
                onError(ErrorHandler.UnKnownError)
            }
        }
    }

    protected fun <T : BaseUiEffect> effectActionExecutor(
        _effect: MutableSharedFlow<EventHandler<T>>,
        effect: T,
    ) {
        viewModelScope.launch {
            _effect.emit(EventHandler(effect))
        }
    }

    protected fun <T> tryToExecuteDebounced(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (t: ErrorHandler) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {
        job?.cancel()
        job = viewModelScope.launch(dispatcher) {
            try {
                delay(2000)
                val result = function()
                log("tryToExecute:$result ")
                onSuccess(result)

            } catch (exception: GeneralException) {
                handelGeneralException(exception, onError)
                log("tryToExecute error GeneralException: ${exception}")
            } catch (exception: NetworkException) {
                handelNetworkException(exception, onError)
                log("tryToExecute error NetworkException: ${exception}")
            } catch (exception: AuthenticationException) {
                handelAuthenticationException(exception, onError)
                log("tryToExecute error AuthenticationException: ${exception}")
            } catch (exception: IOException) {
                log("tryToExecute error IOException: ${exception}")
                onError(ErrorHandler.NoConnection)
            } catch (exception: Exception) {
                log("tryToExecute error Exception: ${exception}")
                onError(ErrorHandler.UnKnownError)
            }
        }
        log("job isCompleted : ${job?.isCompleted} ")
        log("job isCancelled : ${job?.isCancelled} ")
        log("tryToExecute: job: $job")
    }

}

