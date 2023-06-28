package org.the_chance.honeymart.ui.feature.category

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.ui_effect.CategoryUiEffect
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.CategoriesUiState
import org.the_chance.honeymart.ui.feature.uistate.CategoryUiState
import org.the_chance.honeymart.ui.feature.uistate.toCategoryUiState
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getAllCategories: GetAllCategoriesInMarketUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<CategoriesUiState, CategoryUiEffect>(CategoriesUiState()),
    CategoryInteractionListener {
    override val TAG: String = this::class.java.simpleName


    private val args = CategoriesFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        getAllCategory()
    }


    fun getAllCategory() {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { getAllCategories(args.marketId).map { it.toCategoryUiState() } },
            ::onGetCategorySuccess,
            ::onGetCategoryError
        )
    }

    private fun onGetCategorySuccess(categories: List<CategoryUiState>) {
        this._state.update {
            it.copy(
                isLoading = false,
                error = null,
                categories = categories,
            )
        }
    }

    private fun onGetCategoryError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun onCategoryClicked(categoryId: Long) {
        val position = state.value.categories.indexOfFirst { it.categoryId == categoryId }
        executeAction(_effect,
            CategoryUiEffect.OnCategoryClicked(categoryId, args.marketId, position))
    }
}