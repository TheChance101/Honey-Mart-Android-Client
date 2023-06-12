package org.the_chance.honeymart.ui.feature.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.CategoryUiState
import org.the_chance.honeymart.ui.feature.uistate.ProductUiState
import org.the_chance.honeymart.ui.feature.uistate.ProductsUiState
import org.the_chance.honeymart.ui.feature.uistate.asCategoriesUiState
import org.the_chance.honeymart.ui.feature.uistate.asProductUiState
import org.the_chance.honeymart.ui.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProducts: GetAllProductsByCategoryUseCase,
    private val getMarketAllCategories: GetAllCategoriesInMarketUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProductsUiState, Long>(ProductsUiState()), ProductInteractionListener,
    CategoryProductInteractionListener {

    override val TAG: String = this::class.simpleName.toString()
    private val args = ProductsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        getCategoriesByMarketId()
        getProductsByCategoryId()
    }

    private fun getCategoriesByMarketId() {
        _state.update { it.copy(isLoading = true) }
        updateSelectedCategoryState(args.categoryId)
        tryToExecute(
            { getMarketAllCategories(args.marketId) },
            CategoryEntity::asCategoriesUiState,
            ::onGetCategorySuccess,
            ::onGetCategoryError
        )

    }

    private fun getProductsByCategoryId() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllProducts(args.categoryId) },
            ProductEntity::asProductUiState,
            ::onGetProductSuccess,
            ::onGetProductError
        )

    }

    private fun onGetCategorySuccess(categories: List<CategoryUiState>) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
                categories = categories
            )
        }
    }

    private fun onGetProductSuccess(products: List<ProductUiState>) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
                products = products
            )
        }
    }

    private fun onGetCategoryError(throwable: Throwable) {
        _state.update { it.copy(isLoading = false, isError = true) }
    }

    private fun onGetProductError(throwable: Throwable) {
        _state.update { it.copy(isLoading = false, isError = true) }
    }

    override fun onClickCategoryProduct(CategoryId: Long) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllProducts(CategoryId) },
            ProductEntity::asProductUiState,
            ::onGetProductSuccess,
            ::onGetProductError
        )
        updateSelectedCategoryState(CategoryId)
        viewModelScope.launch { _effect.emit(EventHandler(CategoryId)) }
    }

    private fun updateSelectedCategoryState(categoryId: Long) {
        val updatedCategories = _state.value.categories.map { category ->
            if (category.categoryId == categoryId) {
                category.copy(selectedCategory = true)
            } else {
                category.copy(selectedCategory = false)
            }
        }
        _state.update { it.copy(categories = updatedCategories) }
    }


    override fun onClickProduct(productId: Long) {}
}