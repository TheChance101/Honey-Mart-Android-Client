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
    private val getAllProducts: GetAllProductsByCategoryUseCase,
    private val getMarketAllCategories: GetAllCategoriesInMarketUseCase,
) : BaseViewModel<ProductsUiState>(ProductsUiState()), ProductInteractionListener,
    CategoryProductInteractionListener {

    override val TAG: String = this::class.simpleName.toString()


    fun getCategoriesByMarketId(marketId: Long) {
        _uiState.update { it.copy(isLoading = true) }
        tryToExecute(
            { getMarketAllCategories(marketId) },
            { Category -> Category.asCategoriesUiState() },
            ::onSuccess,
            ::onError
        )
    }

    fun getProductsByCategoryId(categoryId: Long) {
        _uiState.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllProducts(categoryId) },
            { Product -> Product.asProductUiState() },
            ::onSuccessGetProducts,
            ::onError
        )
    }

    private fun onSuccessGetProducts(products: List<ProductUiState>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                products = products
            )
        }
    }

    private fun onSuccess(categories: List<CategoryUiState>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                categories = categories
            )
        }
    }

    private fun onError() {
        this._uiState.update {
            it.copy(
                isLoading = false,
                isError = true,
            )
        }
    }

    override fun onClickCategoryProduct(categoryId: Long) {

    }

    override fun onClickProduct(productId: Long) {

    }
}