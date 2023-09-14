package org.the_chance.honeymart.ui.feature.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.model.Category
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase
import org.the_chance.honeymart.domain.usecase.GetAllWishListUseCase
import org.the_chance.honeymart.domain.usecase.WishListOperationsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.SeeAllmarkets.MarketViewModel.Companion.MAX_PAGE_SIZE
import org.the_chance.honeymart.ui.feature.marketInfo.CategoryUiState
import org.the_chance.honeymart.ui.feature.marketInfo.toCategoryUiState
import org.the_chance.honeymart.ui.feature.wishlist.WishListProductUiState
import org.the_chance.honeymart.ui.feature.wishlist.toWishListProductUiState
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProducts: GetAllProductsByCategoryUseCase,
    private val getWishListUseCase: GetAllWishListUseCase,
    private val wishListOperationsUseCase: WishListOperationsUseCase,
    private val getMarketAllCategories: GetAllCategoriesInMarketUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProductsUiState, ProductUiEffect>(ProductsUiState()), ProductInteractionListener {

    override val TAG: String = this::class.simpleName.toString()

    private val args = ProductArgs(savedStateHandle)
    private var page = _state.value.page
    private var productListScrollPosition = 0

    init {
        _state.update {
            it.copy(
                categoryId = args.categoryId.toLong(),
                position = args.position.toInt()
            )
        }
        getData()
    }

    private fun getData() {
        _state.update { it.copy(error = null, isError = false) }
        getProductsByCategoryId()
        getCategoriesByMarketId()
    }

    private fun getCategoriesByMarketId() {
        _state.update { it.copy(isLoadingCategory = true, isError = false) }
        tryToExecute(
            { getMarketAllCategories(args.marketId.toLong()) },
            ::onGetCategorySuccess,
            ::onGetCategoryError
        )
    }

    private fun onGetCategorySuccess(categories: List<Category>) {
        _state.update {
            it.copy(
                error = null, isLoadingCategory = false,
                categories = updateCategorySelection(
                    categories.map { it.toCategoryUiState() },
                    state.value.categoryId
                ),
                position = state.value.position
            )
        }
    }

    private fun onGetCategoryError(error: ErrorHandler) {
        _state.update { it.copy(isLoadingCategory = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun onClickCategory(categoryId: Long) {
        val updatedCategories = updateCategorySelection(_state.value.categories, categoryId)
        val position = updatedCategories.indexOfFirst { it.categoryId == categoryId }
        _state.update {
            it.copy(
                categories = updatedCategories,
                products = listOf(),
                position = position.inc(),
                categoryId = categoryId,
                isEmptyProducts = false
            )
        }
        getProductsByCategoryId()
    }

    private fun updateCategorySelection(
        categories: List<CategoryUiState>,
        selectedCategoryId: Long,
    ): List<CategoryUiState> {
        return categories.map { category ->
            category.copy(isCategorySelected = category.categoryId == selectedCategoryId)
        }
    }

    private fun getProductsByCategoryId() {
        _state.update { it.copy(isError = false) }
        resetSearchState()
        _state.update { it.copy(isLoadingProduct = true) }
        viewModelScope.launch {
            val products = getAllProducts(state.value.categoryId, page)
            val mappedProducts = products!!.map { it.toProductUiState() }
            _state.update { it.copy(products = mappedProducts, isLoadingProduct = false) }
        }
    }

    private fun appendProducts(products: List<ProductUiState>) {
        val current = ArrayList(state.value.products)
        current.addAll(products)
        _state.update { it.copy(products = current) }
    }

    private fun incrementPage() {
        page += 1
    }

    fun onChangeProductScrollPosition(position: Int) {
        productListScrollPosition = position
    }

    private fun resetSearchState() {
        _state.update { it.copy(products = listOf()) }
        page = 1
        onChangeProductScrollPosition(0)
    }

    private fun updateProducts(
        products: List<ProductUiState>,
        wishListProducts: List<WishListProductUiState>,
    ) = products.map { product ->
        product.copy(isFavorite = product.productId in wishListProducts.map { it.productId })
    }


    override fun onClickProduct(productId: Long) {
        effectActionExecutor(
            _effect,
            ProductUiEffect.ClickProductEffect(productId, args.categoryId.toLong())
        )
    }


    private fun getWishListProducts(products: List<ProductUiState>) {
        _state.update { it.copy(isError = false) }
        tryToExecute(
            { getWishListUseCase().map { it.toWishListProductUiState() } },
            { onGetWishListProductSuccess(it, products) },
            { onGetWishListProductError(it, products) }
        )
    }


    private fun onGetWishListProductSuccess(
        wishListProducts: List<WishListProductUiState>, products: List<ProductUiState>,
    ) {
        _state.update { productsUiState ->
            productsUiState.copy(products = updateProducts(products, wishListProducts))
        }
    }

    private fun onGetWishListProductError(
        error: ErrorHandler,
        products: List<ProductUiState>
    ) {
        _state.update {
            it.copy(error = error, products = products)
        }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun onClickFavIcon(productId: Long) {
        var isFavorite = false
        val currentProducts = _state.value.products
        val updatedProducts = currentProducts.map { product ->
            if (product.productId == productId) {
                isFavorite = product.isFavorite
                product.copy(isFavorite = !isFavorite)
            } else {
                product
            }
        }
        _state.update { it.copy(products = updatedProducts, isLoadingProduct = true) }
        if (isFavorite) {
            deleteProductFromWishList(productId)
        } else {
            addProductToWishList(productId)
        }
    }

    private fun deleteProductFromWishList(productId: Long) {
        tryToExecute(
            { wishListOperationsUseCase.deleteFromWishList(productId) },
            { onDeleteWishListSuccess() },
            ::onDeleteWishListError
        )
    }

    private fun onDeleteWishListSuccess() {
        _state.update { it.copy(isLoadingProduct = false) }
    }

    private fun onDeleteWishListError(error: ErrorHandler) {
        _state.update { it.copy(error = error, isError = true, isLoadingProduct = false) }
    }

    private fun addProductToWishList(productId: Long) {
        tryToExecute(
            { wishListOperationsUseCase.addToWishList(productId) },
            ::onAddToWishListSuccess,
            { onAddToWishListError(it) }
        )
        _state.update { it.copy(snackBar = it.snackBar.copy(productId = productId)) }
    }

    private fun onAddToWishListSuccess(successMessage: String) {
        _state.update { it.copy(isLoadingProduct = false) }
        effectActionExecutor(_effect, ProductUiEffect.AddedToWishListEffect(successMessage))
    }

    override fun showSnackBar(message: String) {
        _state.update { it.copy(snackBar = it.snackBar.copy(isShow = true, message = "Success")) }
    }

    override fun onScrollDown() {
        viewModelScope.launch {
            if ((productListScrollPosition + 1) >= (page * MAX_PAGE_SIZE)) {
                _state.update { it.copy(isLoadingProduct = true) }
                incrementPage()
                if (page > 1) {
                    val result = getAllProducts(
                        state.value.categoryId, page
                    )!!.map { it.toProductUiState() }
                    appendProducts(result)
                }
                _state.update { it.copy(isLoadingProduct = false) }
            }
        }
    }

    override fun resetSnackBarState() {
        _state.update { it.copy(snackBar = it.snackBar.copy(isShow = false)) }
    }

    private fun onAddToWishListError(error: ErrorHandler) {
        _state.update { it.copy(isLoadingProduct = false) }
        if (error is ErrorHandler.UnAuthorized)
            effectActionExecutor(_effect, ProductUiEffect.UnAuthorizedUserEffect)
    }

    override fun onclickTryAgain() {
        getData()
    }

    override fun onclickTryAgainProducts() {
        getProductsByCategoryId()
    }
}