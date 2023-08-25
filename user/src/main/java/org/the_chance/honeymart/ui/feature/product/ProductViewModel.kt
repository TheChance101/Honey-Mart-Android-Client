package org.the_chance.honeymart.ui.feature.product

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase
import org.the_chance.honeymart.domain.usecase.GetAllWishListUseCase
import org.the_chance.honeymart.domain.usecase.WishListOperationsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.category.CategoryUiState
import org.the_chance.honeymart.ui.feature.category.toCategoryUiState
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

    private fun onGetCategorySuccess(categories: List<CategoryEntity>) {
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
                products = emptyList(),
                position = position.inc(),
                categoryId = categoryId,
                isLoadingProduct = true,
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
        _state.update { it.copy(isLoadingProduct = true, isError = false) }
        tryToExecute(
            { getAllProducts(state.value.categoryId) },
            ::onGetProductSuccess,
            ::onGetProductError
        )
    }

    private fun onGetProductSuccess(products: List<ProductEntity>) {
        val mappedProducts = products.map { it.toProductUiState() }
        if (products.isEmpty()) {
            _state.update {
                it.copy(
                    isEmptyProducts = true,
                    products = mappedProducts
                )
            }
        } else {
            _state.update {
                it.copy(
                    isEmptyProducts = false,
                    products = mappedProducts
                )
            }
        }
        getWishListProducts(mappedProducts)
    }

    private fun onGetProductError(error: ErrorHandler) {
        _state.update { it.copy(isLoadingProduct = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun onClickProduct(productId: Long) {
        effectActionExecutor(
            _effect,
            ProductUiEffect.ClickProductEffect(productId, args.categoryId.toLong())
        )
    }


    private fun updateProducts(
        products: List<ProductUiState>,
        wishListProducts: List<WishListProductUiState>,
    ) = products.map { product ->
        product.copy(isFavorite = product.productId in wishListProducts.map { it.productId })
    }

    private fun getWishListProducts(products: List<ProductUiState>) {
        _state.update { it.copy(isLoadingProduct = true, isError = false) }
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
            productsUiState.copy(
                isLoadingProduct = false, products = updateProducts(products, wishListProducts)
            )
        }
    }

    private fun onGetWishListProductError(error: ErrorHandler, products: List<ProductUiState>) {
        _state.update { it.copy(isLoadingProduct = false, error = error, products = products) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun onClickFavIcon(productId: Long) {
        val currentProduct = _state.value.products.find { it.productId == productId }
        val isFavorite = currentProduct?.isFavorite ?: false
        val newFavoriteState = !isFavorite

        updateFavoriteState(productId, newFavoriteState)

        if (isFavorite) {
            deleteProductFromWishList(productId)
        } else {
            addProductToWishList(productId)
        }
    }

    private fun deleteProductFromWishList(productId: Long) {
        tryToExecute(
            { wishListOperationsUseCase.deleteFromWishList(productId) },
            ::onDeleteWishListSuccess,
            ::onDeleteWishListError
        )
    }


    private fun onDeleteWishListSuccess(successMessage: String) {
    }

    private fun onDeleteWishListError(error: ErrorHandler) {
        _state.update { it.copy(error = error, isError = true) }
    }

    private fun addProductToWishList(productId: Long) {
        tryToExecute(
            { wishListOperationsUseCase.addToWishList(productId) },
            ::onAddToWishListSuccess,
            { onAddToWishListError(it, productId) }
        )
        _state.update { it.copy(snackBar = it.snackBar.copy(productId = productId)) }
    }

    private fun updateFavoriteState(productId: Long, isFavorite: Boolean) {
        val newProduct = _state.value.products.map {
            if (it.productId == productId) {
                it.copy(isFavorite = isFavorite)
            } else {
                it
            }
        }
        _state.update { it.copy(products = newProduct) }
    }

    private fun onAddToWishListSuccess(successMessage: String) {
        effectActionExecutor(_effect, ProductUiEffect.AddedToWishListEffect(successMessage))
    }

    override fun showSnackBar(message: String) {
        _state.update { it.copy(snackBar = it.snackBar.copy(isShow = true, message = message)) }
    }

    override fun resetSnackBarState() {
        _state.update { it.copy(snackBar = it.snackBar.copy(isShow = false)) }
    }

    private fun onAddToWishListError(error: ErrorHandler, productId: Long) {
        if (error is ErrorHandler.UnAuthorizedUser)
            effectActionExecutor(_effect, ProductUiEffect.UnAuthorizedUserEffect)
    }


    override fun onclickTryAgain() {
        getData()
    }

}