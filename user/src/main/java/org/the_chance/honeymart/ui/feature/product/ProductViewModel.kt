package org.the_chance.honeymart.ui.feature.product

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.Category
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase
import org.the_chance.honeymart.domain.usecase.GetAllWishListUseCase
import org.the_chance.honeymart.domain.usecase.WishListOperationsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.marketInfo.CategoryUiState
import org.the_chance.honeymart.ui.feature.marketInfo.toCategoryUiState
import org.the_chance.honeymart.ui.feature.see_all_markets.MarketsViewModel.Companion.MAX_PAGE_SIZE
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

    init {
        _state.update {
            it.copy(
                categoryId = args.categoryId.toLong(),
                position = args.position.toInt()
            )
        }
    }

    override fun getData() {
        _state.update { it.copy(error = null, isError = false, products = emptyList()) }
        getWishListProducts()
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
        _state.update { productsUiState ->
            productsUiState.copy(
                error = null,
                isLoadingCategory = false,
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
                categoryId = categoryId
            )
        }
        resetProducts()
        getWishListProducts()
    }

    private fun updateCategorySelection(
        categories: List<CategoryUiState>,
        selectedCategoryId: Long,
    ): List<CategoryUiState> {
        return categories.map { category ->
            category.copy(isCategorySelected = category.categoryId == selectedCategoryId)
        }
    }

    override fun onChangeProductScrollPosition(position: Int) {
        if ((position + 1) >= (state.value.page * MAX_PAGE_SIZE)) {
            _state.update { it.copy(page = it.page + 1) }
            getAllProducts()
        }
    }

    private fun getAllProducts() {
        _state.update {
            it.copy(
                isLoadingProduct = true,
                error = null,
                isError = false
            )
        }
        tryToExecute(
            { getAllProducts(state.value.categoryId, state.value.page) },
            ::allProductsSuccess,
            ::allProductsError
        )
    }

    private fun allProductsSuccess(products: List<Product>) {
        val updatedProducts = state.value.products.toMutableList().apply {
            this.addAll(products.map { it.toProductUiState() })
        }
        _state.update {
            it.copy(
                isLoadingProduct = false,
                error = null,
                products = updateProducts(
                    updatedProducts,
                    state.value.wishListProducts
                )
            )
        }
    }

    private fun allProductsError(error: ErrorHandler) {
        _state.update { it.copy(isLoadingProduct = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    private fun resetProducts() {
        _state.update { it.copy(products = listOf(), page = 1) }
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


    private fun getWishListProducts() {
        _state.update { it.copy(isError = false, isLoadingProduct = true) }
        tryToExecute(
            { getWishListUseCase().map { it.toWishListProductUiState() } },
            { onGetWishListProductSuccess(it) },
            { onGetWishListProductError(it) }
        )
    }


    private fun onGetWishListProductSuccess(
        wishListProducts: List<WishListProductUiState>,
    ) {
        _state.update {
            it.copy(wishListProducts = wishListProducts)
        }
        getAllProducts()
    }

    private fun onGetWishListProductError(
        error: ErrorHandler
    ) {
        _state.update {
            it.copy(error = error)
        }
        getAllProducts()
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
        getAllProducts()
    }
}