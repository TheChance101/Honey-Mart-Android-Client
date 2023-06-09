package org.the_chance.honeymart.ui.feature.category

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.CategoriesUiState
import org.the_chance.honeymart.ui.feature.uistate.CategoryUiState
import org.the_chance.honeymart.ui.feature.uistate.asCategoriesUiState
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategories: GetAllCategoriesInMarketUseCase,
) : BaseViewModel<CategoriesUiState>(CategoriesUiState()), CategoryInteractionListener {
    override val TAG: String = this::class.java.simpleName


    init {
        getAllCategory(1) // should be replaced by marketId in args we get from savedStateHandle
    }

    private fun getAllCategory(marketId: Long) {
        _uiState.update { it.copy(isLoading = true) }
        tryToExecute(
            { getCategories(marketId) },
            { category -> category.asCategoriesUiState() },
            ::onSuccess,
            ::onError
        )
    }

    private fun onSuccess(categories: List<CategoryUiState>) {
        this._uiState.update {
            it.copy(
                isLoading = false,
                categories = categories
            )
        }
    }

    private fun onError() {
        this._uiState.update {
            it.copy(
                isLoading = false,
                isError = true
            )
        }
    }

    override fun onCategoryClicked(categoryId: Long) {}
}