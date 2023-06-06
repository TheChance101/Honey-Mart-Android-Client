package org.the_chance.honeymart.ui.feature.product

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetAllCategoryProductsUseCase
import org.the_chance.honeymart.domain.usecase.GetMarketAllCategoriesUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.product.uistste.CategoryUiState
import org.the_chance.honeymart.ui.feature.product.uistste.ProductUiState
import org.the_chance.honeymart.ui.feature.product.uistste.ProductsUiState
import org.the_chance.honeymart.ui.feature.product.uistste.asCategoryUiState
import org.the_chance.honeymart.ui.feature.product.uistste.asProductUiState
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProducts: GetAllCategoryProductsUseCase,
    private val getMarketAllCategories: GetMarketAllCategoriesUseCase,
) : BaseViewModel<ProductsUiState>(ProductsUiState()), ProductInteractionListener,
    CategoryProductInteractionListener {

    init {
        getCategoriesByMarketId()
        getProductsByCategoryId()
    }

    private fun getCategoriesByMarketId() {
        _uiState.update { it.copy(isLoading = true) }
        tryToExecute(
            { getMarketAllCategories(1) },
            { Category -> Category.asCategoryUiState() },
            ::onSuccess,
            ::onError
        )
    }

    private fun getProductsByCategoryId() {
        _uiState.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllProducts(1) },
            { Product -> Product.asProductUiState() },
            ::onSuccessGetProducts,
            ::onErrorGetProducts
        )
    }

    private fun onSuccessGetProducts(products: List<ProductUiState>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                productList = products
            )
        }
    }

    private fun onErrorGetProducts() {
        this._uiState.update {
            it.copy(
                isLoading = false,
                isError = false
            )
        }
    }

    private fun onSuccess(categories: List<CategoryUiState>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                categoryList = categories
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
    override val TAG: String = this::class.simpleName.toString()

    override fun onClickCategoryProduct(id: Int) {

    }

    override fun onClickProduct(id: Int) {

    }
}