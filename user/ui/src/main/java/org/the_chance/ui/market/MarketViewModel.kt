package org.the_chance.ui.market

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.the_chance.domain.usecase.GetAllMarketUseCase
import org.the_chance.ui.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getAllMarket: GetAllMarketUseCase,
) : BaseViewModel() {
    override val TAG: String = "TAG"

    init {
        foo()
        Log.e("TAG", "MarketViewModel: ")
    }

    fun foo() {
        viewModelScope.launch {
            try {
                val r = getAllMarket()
                Log.e(TAG, "foo:$r ")
            } catch (e: Throwable) {
                Log.e(TAG, "foo error: ${e.message}")
            }
        }
    }
}