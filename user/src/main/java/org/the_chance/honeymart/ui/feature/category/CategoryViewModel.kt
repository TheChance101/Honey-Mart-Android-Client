package org.the_chance.honeymart.ui.feature.category

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.GetMarketAllCategoriesUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.ui.category.uistate.CategoryUiState
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getMarketsAllCategories: GetMarketAllCategoriesUseCase,
) : BaseViewModel<CategoryUiState>(CategoryUiState()), CategoryInteractionListener {
    override val TAG: String = this::class.java.simpleName


    init {
        getAllCategories()
        Log.e("TAG", "CategoryViewModel: ")
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            try {
                val result = getMarketsAllCategories(0)
                Log.e(TAG, "getAllCategories: $result ")
            } catch (e: Throwable) {
                Log.e(TAG, "getAllCategories error: ${e.message}")
            }
        }
    }

    override fun onCategoryClicked(id: Long) {
        TODO("Not yet implemented")
    }
}