package org.the_chance.honeymart.ui.feature.product

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.CategoryUiState
import org.the_chance.honeymart.ui.feature.uistate.ProductUiState
import org.the_chance.honeymart.ui.feature.uistate.ProductsUiState
import org.the_chance.honeymart.ui.feature.uistate.asCategoriesUiState
import org.the_chance.honeymart.ui.feature.uistate.asProductUiState
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsInCategory: GetAllProductsByCategoryUseCase,
    private val getCategoriesInMarket: GetAllCategoriesInMarketUseCase,
) : BaseViewModel<ProductsUiState>(ProductsUiState()),
    ProductInteractionListener,
    CategoryProductInteractionListener {
    override val TAG: String = this::class.simpleName.toString()

    init {
        getAllCategoriesInMarket(1) // should be replaced by marketId in args we get from savedStateHandle
        getAllProductsInCategory(6) // should be replaced by categoryId un args we get from savedStateHandle
    }

    private fun getAllCategoriesInMarket(marketId: Long) {
        _uiState.update { it.copy(isLoading = true) }
        tryToExecute(
            { getCategoriesInMarket(marketId) },
            { Category -> Category.asCategoriesUiState() },
            ::onGetCategoriesSuccess,
            ::onGetCategorisError
        )
    }

    private fun getAllProductsInCategory(categoryId: Long) {
        _uiState.update { it.copy(isLoading = true) }
        tryToExecute(
            { getProductsInCategory(categoryId) },
            { product -> product.asProductUiState() },
            ::onGetProductsSuccess,
            ::onGetProductsError
        )
    }

    private fun onGetProductsSuccess(products: List<ProductUiState>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                products = products
            )
        }
    }

    private fun onGetCategoriesSuccess(categories: List<CategoryUiState>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                categories = categories
            )
        }
    }

    private fun onGetCategorisError() {
        this._uiState.update {
            it.copy(
                isLoading = false,
                isError = true,
            )
        }
    }

    private fun onGetProductsError() {
        this._uiState.update {
            it.copy(
                isLoading = false,
                isError = true,
            )
        }
    }

    override fun onClickCategoryProduct(categoryId: Long) {}
    override fun onClickProduct(productId: Long) {}
}