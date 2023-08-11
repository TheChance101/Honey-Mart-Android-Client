package org.the_chance.honeymart.ui.feature.category

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getAllCategories: GetAllCategoriesInMarketUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<CategoriesUiState, CategoryUiEffect>(CategoriesUiState()),
    CategoryInteractionListener {

    private val categoryArgs: CategoryArgs = CategoryArgs(savedStateHandle)

    override val TAG: String = this::class.java.simpleName


    init {
        onGetData()
    }

    override fun onGetData() {
        getAllCategory()
    }

    override fun onClickCategory(categoryId: Long, position: Int) {
        effectActionExecutor(
            _effect,
            CategoryUiEffect.ClickCategoryEffect(
                categoryId,
                categoryArgs.marketId.toLong(),
                position
            )
        )

    }

    private fun getAllCategory() {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { getAllCategories(categoryArgs.marketId.toLong()) },
            ::onGetCategorySuccess,
            ::onGetCategoryError
        )
    }

    private fun onGetCategorySuccess(categories: List<CategoryEntity>) {
        this._state.update {
            it.copy(
                isLoading = false,
                error = null,
                categories = categories.map { categoryEntity -> categoryEntity.toCategoryUiState()},
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