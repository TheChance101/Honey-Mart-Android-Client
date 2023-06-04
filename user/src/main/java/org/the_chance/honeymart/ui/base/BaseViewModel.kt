package org.the_chance.honeymart.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    abstract val TAG: String
    protected open fun log(message: String) {
        Log.v(TAG, message)
    }

    protected fun <T, R> tryToExecute(
        function: suspend () -> List<T>,
        transform: (T) -> R,
        onSuccess: (List<R>) -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = function().map(transform)
                onSuccess(result)
                Log.e("gh", "markt_result:$result")
            } catch (e: Throwable) {
                onError()
            }
        }
    }

}