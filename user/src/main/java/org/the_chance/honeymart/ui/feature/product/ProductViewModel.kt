package org.the_chance.honeymart.ui.feature.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.AddToWishListUseCase
import org.the_chance.honeymart.domain.usecase.DeleteFromWishListUseCase
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase
import org.the_chance.honeymart.domain.usecase.GetAllWishListUseCase
import org.the_chance.honeymart.domain.util.UnAuthorizedException
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.CategoryUiState
import org.the_chance.honeymart.ui.feature.uistate.ProductUiState
import org.the_chance.honeymart.ui.feature.uistate.ProductsUiState
import org.the_chance.honeymart.ui.feature.uistate.WishListProductUiState
import org.the_chance.honeymart.ui.feature.uistate.toCategoryUiState
import org.the_chance.honeymart.ui.feature.uistate.toProductUiState
import org.the_chance.honeymart.ui.feature.uistate.toWishListProductUiState
import org.the_chance.honeymart.util.AuthData
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProducts: GetAllProductsByCategoryUseCase,
    private val addToWishListUseCase: AddToWishListUseCase,
    private val getWishListUseCase: GetAllWishListUseCase,
    private val deleteFromWishListUseCase: DeleteFromWishListUseCase,
    private val getMarketAllCategories: GetAllCategoriesInMarketUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProductsUiState, ProductUiEffect>(ProductsUiState()), ProductInteractionListener,
    CategoryProductInteractionListener {

    override val TAG: String = this::class.simpleName.toString()
    private val args = ProductsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    init {
        getCategoriesByMarketId()
        getProductsByCategoryId(args.categoryId)
    }

    private fun getWishListProducts(products: List<ProductUiState>) {
        tryToExecute(
            { getWishListUseCase().map { it.toWishListProductUiState() } },
            { onGetWishListProductSuccess(it, products) },
            { onGetWishListProductError(it, products) }
        )
    }

    private fun onGetWishListProductSuccess(
        wishListProducts: List<WishListProductUiState>,
        products: List<ProductUiState>,
    ) {
        _state.update { productsUiState ->
            productsUiState.copy(
                isLoadingProduct = false,
                isError = false,
                products = updateProducts(products, wishListProducts)
            )
        }
    }

    private fun updateProducts(
        products: List<ProductUiState>,
        wishListProducts: List<WishListProductUiState>,
    ) = products.map { product ->
        product.copy(
            isFavorite = product.productId in wishListProducts.map { it.productId }
        )
    }

    private fun onGetWishListProductError(throwable: Exception, products: List<ProductUiState>) {
        _state.update {
            it.copy(
                isLoadingProduct = false,
                products = products
            )
        }
    }

    private fun getCategoriesByMarketId() {
        _state.update { it.copy(isLoadingCategory = true) }
        tryToExecute(
            { getMarketAllCategories(args.marketId).map { it.toCategoryUiState() } },
            ::onGetCategorySuccess,
            ::onGetCategoryError
        )
    }

    private fun getProductsByCategoryId(categoryId: Long) {
        _state.update { it.copy(isLoadingProduct = true) }
        tryToExecute(
            { getAllProducts(categoryId).map { it.toProductUiState() } },
            ::onGetProductSuccess,
            ::onGetProductError
        )
    }

    private fun onGetCategorySuccess(categories: List<CategoryUiState>) {
        _state.update {
            it.copy(
                isError = false,
                isLoadingCategory = false,
                categories = updateCategorySelection(categories, args.categoryId),
                position = args.position
            )
        }
    }

    private fun onGetProductSuccess(products: List<ProductUiState>) {
        getWishListProducts(products)
    }

    private fun onGetCategoryError(throwable: Exception) {
        _state.update { it.copy(isLoadingCategory = false, isError = true) }
    }

    private fun onGetProductError(throwable: Exception) {
        _state.update { it.copy(isLoadingProduct = false, isError = true) }
    }

    override fun onClickCategoryProduct(categoryId: Long) {
        val updatedCategories = updateCategorySelection(_state.value.categories, categoryId)
        val position = updatedCategories.indexOfFirst { it.categoryId == categoryId }
        _state.update {
            it.copy(
                categories = updatedCategories,
                products = emptyList(),
                position = position.inc()
            )
        }
        getProductsByCategoryId(categoryId)
    }

    private fun updateCategorySelection(
        categories: List<CategoryUiState>,
        selectedCategoryId: Long,
    ): List<CategoryUiState> {
        return categories.map { category ->
            category.copy(isCategorySelected = category.categoryId == selectedCategoryId)
        }
    }

    override fun onClickProduct(productId: Long) {
        viewModelScope.launch {
            _effect.emit(
                EventHandler(
                    ProductUiEffect.ClickProductEffect(
                        productId,
                        args.categoryId
                    )
                )
            )
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
            { deleteFromWishListUseCase(productId) },
            ::onDeleteWishListSuccess,
            ::onDeleteWishListError
        )
    }


    private fun onDeleteWishListSuccess(successMessage: String) {
        viewModelScope.launch {
            _effect.emit(EventHandler(ProductUiEffect.RemovedFromWishListEffect))
        }
        log("Deleted Successfully : $successMessage")
    }

    private fun onDeleteWishListError(error: Exception) {
        log("Delete From WishList Error : ${error.message}")
    }

    private fun addProductToWishList(productId: Long) {
        tryToExecute(
            { addToWishListUseCase(productId) },
            ::onAddToWishListSuccess,
            { onAddToWishListError(it, productId) }
        )
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
        viewModelScope.launch {
            _effect.emit(EventHandler(ProductUiEffect.AddedToWishListEffect))
        }
        log("Added Successfully : $successMessage")
    }

    private fun onAddToWishListError(error: Exception, productId: Long) {
        if (error is UnAuthorizedException) {
            viewModelScope.launch {
                _effect.emit(
                    EventHandler(
                        ProductUiEffect.UnAuthorizedUserEffect(
                            AuthData.Products(
                                args.marketId,
                                state.value.position,
                                args.categoryId
                            )
                        )
                    )
                )
            }
        }
        log("Add to WishList Error : ${error.message}")
        updateFavoriteState(productId, false)
    }

}