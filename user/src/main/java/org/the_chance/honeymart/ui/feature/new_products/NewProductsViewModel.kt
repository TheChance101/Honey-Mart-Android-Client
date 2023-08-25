package org.the_chance.honeymart.ui.feature.new_products

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.RecentProductEntity
import org.the_chance.honeymart.domain.usecase.GetAllWishListUseCase
import org.the_chance.honeymart.domain.usecase.GetRecentProductsUseCase
import org.the_chance.honeymart.domain.usecase.WishListOperationsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.wishlist.WishListProductUiState
import org.the_chance.honeymart.ui.feature.wishlist.toWishListProductUiState
import javax.inject.Inject

@HiltViewModel
class NewProductsViewModel @Inject constructor(
    private val getRecentProducts: GetRecentProductsUseCase,
    private val wishListOperationsUseCase: WishListOperationsUseCase,
    private val getAllWishList: GetAllWishListUseCase,
) : BaseViewModel<RecentProductsUiState, RecentProductUiEffect>(RecentProductsUiState()),
    RecentProductListener {

    override val TAG: String = this::class.simpleName.toString()

    init {
        getRecentProducts()
    }

    override fun getRecentProducts() {
        tryToExecute(
            getRecentProducts::invoke,
            ::onGetRecentProductsSuccess,
            ::onGetRecentProductsError,
        )
    }


    private fun onGetRecentProductsSuccess(products: List<RecentProductEntity>) {
        _state.update {
            it.copy(
                isLoading = false,
                recentProducts = products.map { product -> product.toRecentProductUiState() }
            )
        }
    }

    private fun onGetRecentProductsError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isError = error is ErrorHandler.NoConnection
            )
        }
    }

    override fun onClickProductItem(productId: Long) {
        effectActionExecutor(_effect, RecentProductUiEffect.ClickProductEffect(productId))
    }


    override fun onClickFavoriteNewProduct(productId: Long) {
        _state.update { it.copy(isLoading = true) }
        if (_state.value.recentProducts.find { it.productId == productId }?.isFavorite == false)
            addProductToWishList(productId)
        else
            deleteProductFromWishList(productId)
    }


    private fun addProductToWishList(productId: Long) {
        tryToExecute(
            { wishListOperationsUseCase.addToWishList(productId) },
            { onAddToWishListSuccess() },
            ::onAddToWishListError
        )
    }


    private fun onAddToWishListSuccess() {
        _state.update { it.copy(isLoading = false) }
        getWishListProducts()
    }

    private fun onAddToWishListError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isError = error is ErrorHandler.NoConnection
            )
        }

        if (error is ErrorHandler.UnAuthorizedUser)
            effectActionExecutor(_effect, RecentProductUiEffect.UnAuthorizedUserEffect)
    }

    private fun deleteProductFromWishList(productId: Long) {
        tryToExecute(
            { wishListOperationsUseCase.deleteFromWishList(productId) },
            { onDeleteWishListSuccess() },
            ::onDeleteWishListError
        )
    }

    private fun onDeleteWishListSuccess() {
        _state.update { it.copy(isLoading = false) }
        getWishListProducts()
    }

    private fun onDeleteWishListError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isError = error is ErrorHandler.NoConnection
            )
        }
    }

    private fun getWishListProducts() {
        tryToExecute(
            { getAllWishList().map { it.toWishListProductUiState() } },
            { onGetWishListProductSuccess(it) },
            { onGetWishListProductError(it) }
        )

    }

    private fun onGetWishListProductSuccess(wishListProducts: List<WishListProductUiState>) {
        _state.update { productsUiState ->
            productsUiState.copy(
                isLoading = false,
                discoverProducts =
                _state.value.discoverProducts.map { product ->
                    product.copy(isFavorite = product.productId in wishListProducts.map { it.productId })
                },
                recentProducts = _state.value.recentProducts.map { product ->
                    product.copy(isFavorite = product.productId in wishListProducts.map { it.productId })
                },
            )
        }
    }

    private fun onGetWishListProductError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isError = error is ErrorHandler.NoConnection,
            )
        }
    }

}