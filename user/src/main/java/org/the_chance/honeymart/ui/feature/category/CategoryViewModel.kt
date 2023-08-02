package org.the_chance.honeymart.ui.feature.category

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getAllCategories: GetAllCategoriesInMarketUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<CategoriesUiState, Unit>(CategoriesUiState()),CategoryInteractionListener {

    private val categoryArgs: CategoryArgs = CategoryArgs(savedStateHandle)

    override val TAG: String = this::class.java.simpleName


    init {
        onGetData()
    }

    override fun onGetData() {
        getAllCategory()
        _state.update { it.copy(marketId = categoryArgs.marketId.toLong()) }
    }

    private fun getAllCategory() {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { getAllCategories(categoryArgs.marketId.toLong()).map { it.toCategoryUiState() } },
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
}