package org.the_chance.honeymart.ui.feature.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.model.Category
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.CategoriesUiState
import org.the_chance.honeymart.ui.feature.uistate.CategoryUiState
import org.the_chance.honeymart.ui.feature.uistate.asCategoriesUiState
import org.the_chance.honeymart.ui.util.EventHandler
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

    private fun getAllCategory() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllCategories(args.marketId) },
            Category::asCategoriesUiState,
            ::onGetCategorySuccess,
            ::onGetCategoryError
        )
    }

    private fun onGetCategorySuccess(categories: List<CategoryUiState>) {
        this._state.update {
            it.copy(
                isLoading = false,
                isError = false,
                categories = categories,
            )
        }
    }

    private fun onGetCategoryError(throwable: Throwable) {
        this._state.update {
            it.copy(
                isLoading = false,
                isError = true
            )
        }
    }

    override fun onCategoryClicked(categoryId: Long) {
        viewModelScope.launch {
            _effect.emit(EventHandler(CategoryUiEffect(categoryId, args.marketId)))
        }
    }
}