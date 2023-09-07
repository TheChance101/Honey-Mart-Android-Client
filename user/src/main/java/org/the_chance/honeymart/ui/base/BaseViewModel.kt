package org.the_chance.honeymart.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.util.AdminException
import org.the_chance.honeymart.domain.util.CartException
import org.the_chance.honeymart.domain.util.CategoryException
import org.the_chance.honeymart.domain.util.CouponException
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.GeneralException
import org.the_chance.honeymart.domain.util.ImageException
import org.the_chance.honeymart.domain.util.MarketException
import org.the_chance.honeymart.domain.util.NetworkException
import org.the_chance.honeymart.domain.util.OrderException
import org.the_chance.honeymart.domain.util.OwnerException
import org.the_chance.honeymart.domain.util.ProductException
import org.the_chance.honeymart.domain.util.TokenException
import org.the_chance.honeymart.domain.util.UserException
import org.the_chance.honeymart.domain.util.handelAdminException
import org.the_chance.honeymart.domain.util.handelCartException
import org.the_chance.honeymart.domain.util.handelCategoryException
import org.the_chance.honeymart.domain.util.handelCouponException
import org.the_chance.honeymart.domain.util.handelGeneralException
import org.the_chance.honeymart.domain.util.handelImageException
import org.the_chance.honeymart.domain.util.handelMarketException
import org.the_chance.honeymart.domain.util.handelNetworkException
import org.the_chance.honeymart.domain.util.handelOrderException
import org.the_chance.honeymart.domain.util.handelOwnerException
import org.the_chance.honeymart.domain.util.handelProductException
import org.the_chance.honeymart.domain.util.handelTokenException
import org.the_chance.honeymart.domain.util.handelUserException
import java.io.IOException

abstract class BaseViewModel<T, E>(initialState: T) : ViewModel() {

    abstract val TAG: String
    protected open fun log(message: String) {
        Log.e(TAG, message)
    }

    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected val _effect = MutableSharedFlow<E>()
    val effect = _effect.asSharedFlow()

    private var job: Job? = null

    protected fun <T : BaseUiEffect> effectActionExecutor(
        _effect: MutableSharedFlow<T>,
        effect: T,
    ) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (t: ErrorHandler) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {
        viewModelScope.launch(dispatcher) {
            handleException(
                onError
            ) {
                val result = function()
                log("tryToExecute: $result ")
                onSuccess(result)
            }
        }
    }

    fun <T : Any> tryToExecutePaging(
        flowFactory: suspend () -> Flow<PagingData<T>>,
        onSuccess: suspend (PagingData<T>) -> Unit,
        onError: (t: ErrorHandler) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            handleException(
                onError
            ) {
                val request = flowFactory().cachedIn(viewModelScope)
                request.collect { result ->
                    onSuccess(result)
                }
            }
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
            handleException(
                onError
            ) {
                delay(2000)
                val result = function()
                log("tryToExecute: $result ")
                onSuccess(result)
            }
        }
        log("job isCompleted: ${job?.isCompleted} ")
        log("job isCancelled: ${job?.isCancelled} ")
        log("tryToExecute: job: $job")
    }

    private suspend fun <T> handleException(
        onError: (t: ErrorHandler) -> Unit,
        action: suspend () -> T
    ) {
        try {
            action()
        } catch (exception: Exception) {
            log("tryToExecute error: $exception")
            when (exception) {
                is UserException -> handelUserException(exception, onError)
                is OwnerException -> handelOwnerException(exception, onError)
                is AdminException -> handelAdminException(exception, onError)
                is MarketException -> handelMarketException(exception, onError)
                is CategoryException -> handelCategoryException(exception, onError)
                is ProductException -> handelProductException(exception, onError)
                is OrderException -> handelOrderException(exception, onError)
                is CartException -> handelCartException(exception, onError)
                is ImageException -> handelImageException(exception, onError)
                is CouponException -> handelCouponException(exception, onError)
                is GeneralException -> handelGeneralException(exception, onError)
                is TokenException -> handelTokenException(exception, onError)
                is NetworkException -> handelNetworkException(exception, onError)
                is IOException -> onError(ErrorHandler.NoConnection)
                else -> onError(ErrorHandler.InvalidData)
            }
        }
    }

}

