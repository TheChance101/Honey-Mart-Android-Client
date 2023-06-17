package org.the_chance.honeymart.ui.feature.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.AddToWishListUseCase
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.CategoryUiState
import org.the_chance.honeymart.ui.feature.uistate.ProductUiState
import org.the_chance.honeymart.ui.feature.uistate.ProductsUiState
import org.the_chance.honeymart.ui.feature.uistate.toCategoryUiState
import org.the_chance.honeymart.ui.feature.uistate.toProductUiState
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProducts: GetAllProductsByCategoryUseCase,
    private val addToWishListUseCase: AddToWishListUseCase,
    private val getMarketAllCategories: GetAllCategoriesInMarketUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProductsUiState, Long>(ProductsUiState()), ProductInteractionListener,
    CategoryProductInteractionListener {

    override val TAG: String = this::class.simpleName.toString()
    private val args = ProductsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _productEffect = MutableSharedFlow<EventHandler<Long>>()
    val productEffect = _productEffect.asSharedFlow()

    init {
        getCategoriesByMarketId()
        getProductsByCategoryId(args.categoryId)
    }

    private fun getCategoriesByMarketId() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getMarketAllCategories(args.marketId).map { it.toCategoryUiState() } },
            ::onGetCategorySuccess,
            ::onGetCategoryError
        )
    }

    private fun getProductsByCategoryId(categoryId: Long) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllProducts(categoryId).map { it.toProductUiState() } },
            ::onGetProductSuccess,
            ::onGetProductError
        )
    }

    private fun onGetCategorySuccess(categories: List<CategoryUiState>) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
                categories = updateCategorySelection(categories, args.categoryId)
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
        val updatedCategories = updateCategorySelection(_state.value.categories, categoryId)
        _state.update {
            it.copy(
                isLoading = true,
                categories = updatedCategories,
                products = emptyList()
            )
        }
        getProductsByCategoryId(categoryId)
        viewModelScope.launch { _productEffect.emit(EventHandler(categoryId)) }
    }

    private fun updateCategorySelection(
        categories: List<CategoryUiState>,
        selectedCategoryId: Long,
    ): List<CategoryUiState> {
        return categories.map { category ->
            category.copy(isCategorySelected = category.categoryId == selectedCategoryId)
        }
    }

    override fun onClickProduct(productId: Long) {}

    override fun onClickFavIcon(productId: Long) {
        viewModelScope.launch {
            val result = addToWishListUseCase(productId)
            if (result == null) {
                log("Not Authenticated")
                _effect.emit(EventHandler(productId))
            }
        }
    }
}