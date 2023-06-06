package org.the_chance.honeymart.ui.feature.product

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.market.MarketUiState
import org.the_chance.honeymart.ui.feature.market.asMarketsUiState
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProduct: GetAllProductsUseCase,
    private val getAllCategory:GetAllCategoriesUseCase
) : BaseViewModel<ProductsUiState>(ProductsUiState()),
    ProductInteractionListener,
    CategoryProductInteractionListener {

    override val TAG: String = this::class.simpleName.toString()

    init {
        getAllProducts()
        getAllCategories()
        viewModelScope.launch {
            log("data = ${getAllProduct()}")
            log("data = ${getAllCategory(1)}")
        }
    }

    private fun getAllProducts() {
        _uiState.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllProduct() },
            { product -> product.asProductUiState()},
            ::onGetProductsSuccess,
            ::onGetProductsError
        )
    }

    private fun onGetProductsError() {
        this._uiState.update {
            it.copy(
                isLoading = false,
                isError = true,
            )
        }
    }

    private fun onGetProductsSuccess(products: List<ProductUiState>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                productList = products
            )
        }
    }

    private fun getAllCategories() {
        _uiState.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllCategory(1) },
            { category -> category.asCategoryUiState()},
            ::onGetCategoriesSuccess,
            ::onGetCategoriesError
        )
    }

    private fun onGetCategoriesError() {
        this._uiState.update {
            it.copy(
                isLoading = false,
                isError = true,
            )
        }
    }

    private fun onGetCategoriesSuccess(categories: List<CategoryUiState>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                categoryList = categories
            )
        }
    }


    override fun onClickCategoryProduct(id: Int) {

    }

    override fun onClickProduct(id: Long) {

    }


}