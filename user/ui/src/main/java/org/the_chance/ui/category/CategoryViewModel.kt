package org.the_chance.ui.category

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.the_chance.domain.usecase.GetAllMarketUseCase
import org.the_chance.domain.usecase.GetMarketAllCategoriesUseCase
import org.the_chance.ui.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getMarketsAllCategories: GetMarketAllCategoriesUseCase,
) : BaseViewModel() {
    override val TAG: String = this::class.java.simpleName


    init {
        getAllCategories()
        Log.e("TAG", "CategoryViewModel: ")
    }

    fun getAllCategories() {
        viewModelScope.launch {
            try {
                val result = getMarketsAllCategories(0)
                Log.e(TAG, "getAllCategories: $result ")
            } catch (e: Throwable) {
                Log.e(TAG, "getAllCategories error: ${e.message}")
            }
        }
    }
}