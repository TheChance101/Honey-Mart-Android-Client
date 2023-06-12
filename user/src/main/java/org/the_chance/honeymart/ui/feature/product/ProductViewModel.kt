package org.the_chance.honeymart.ui.feature.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private val getAllProducts: org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase,
    private val getMarketAllCategories: org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase,
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
        tryToExecute(
            { getMarketAllCategories(args.marketId) },
            org.the_chance.honeymart.domain.model.CategoryEntity::asCategoriesUiState,
            ::onGetCategorySuccess,
            ::onGetCategoryError
        )
    }

    private fun getProductsByCategoryId() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllProducts(args.categoryId) },
            org.the_chance.honeymart.domain.model.ProductEntity::asProductUiState,
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

    override fun onClickCategoryProduct(categoryId: Long) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllProducts(categoryId) },
            org.the_chance.honeymart.domain.model.ProductEntity::asProductUiState,
            ::onGetProductSuccess,
            ::onGetProductError
        )
        viewModelScope.launch { _effect.emit(EventHandler(categoryId)) }
    }

    override fun onClickProduct(productId: Long) {}
}