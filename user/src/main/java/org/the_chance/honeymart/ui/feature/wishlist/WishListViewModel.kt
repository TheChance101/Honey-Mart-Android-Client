package org.the_chance.honeymart.ui.feature.wishlist

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.usecaseManager.user.UserWishListManagerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel

@HiltViewModel
class WishListViewModel @javax.inject.Inject constructor(
    private val wishListOperationsUseCase: UserWishListManagerUseCase,
    ) : BaseViewModel<WishListUiState, WishListUiEffect>(WishListUiState()),
    WishListInteractionListener {

    override val TAG: String = this::class.java.simpleName


    override fun onShowSnackBar(message:String){
        _state.update { it.copy(snackBar = it.snackBar.copy(message = "Item Removed", isShow = true)) }
    }
    private fun deleteProductFromWishList(productId: Long) {
        _state.update {
            it.copy(
                products = updateProductFavoriteState(false,productId),
                isLoading = true,
                isError = false,
            )
        }
        tryToExecute(
            { wishListOperationsUseCase.operationWishListUseCase.deleteFromWishList(productId) },
            ::onDeleteProductSuccess,
            { onDeleteProductError(it, productId) }
        )
        _state.update { it.copy(snackBar = it.snackBar.copy(productId =productId)) }
    }

    private fun onDeleteProductSuccess(successMessage: String) {
        effectActionExecutor(_effect, WishListUiEffect.DeleteProductFromWishListEffect(successMessage))
        getWishListProducts()
    }
    override fun resetSnackBarState(){
        _state.update { it.copy(snackBar =it.snackBar.copy(isShow = false)) }
    }

    private fun updateProductFavoriteState(
        isFavorite: Boolean,
        productId: Long
    ): List<WishListProductUiState> {
        val updatedProducts = _state.value.products.map { wishListProductUiState ->
            if (wishListProductUiState.productId == productId) {
                wishListProductUiState.copy(isFavorite = isFavorite)
            } else {
                wishListProductUiState
            }
        }
        return updatedProducts
    }
     override fun addProductToWishList(productId: Long) {
         _state.update {
             it.copy(
                 isLoading = true,
                 isError = false,
             )
         }
        tryToExecute(
            { wishListOperationsUseCase.operationWishListUseCase.addToWishList(productId) },
            {onAddToWishListSuccess()},
            { onAddToWishListError(it, productId) }
        )
    }
    private fun onAddToWishListSuccess() {
        getWishListProducts()

    }
    private fun onAddToWishListError(error: ErrorHandler, productId: Long) {
        if (error is ErrorHandler.UnAuthorized) {
            updateFavoriteState(productId)
        }
    }

    private fun updateFavoriteState(productId: Long) {
        val newProduct = _state.value.products.map {
            if (it.productId == productId) {
                it.copy(isFavorite = false)
            } else {
                it
            }
        }
        _state.update { it.copy(products = newProduct) }
    }




    private fun onDeleteProductError(error: ErrorHandler, productId: Long) {
        _state.update {
            it.copy(products = updateProductFavoriteState(true ,productId), error = error)
        }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun getWishListProducts() {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            {wishListOperationsUseCase.getAllWishListUseCase().map { it.toWishListProductUiState() }},
            ::onGetProductSuccess,
            ::onGetProductError
        )
    }

    private fun onGetProductSuccess(products: List<WishListProductUiState>) {
        _state.update { it.copy(isLoading = false, error = null, products = products) }
    }

    private fun onGetProductError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }

    }

    override fun onClickDiscoverButton() {
        effectActionExecutor(_effect, WishListUiEffect.ClickDiscoverEffect)
    }

    override fun onClickFavoriteIcon(productId: Long) {
        deleteProductFromWishList(productId)

    }

    override fun onClickProduct(productId: Long) {
        effectActionExecutor(_effect, WishListUiEffect.ClickProductEffect(productId))
    }
}