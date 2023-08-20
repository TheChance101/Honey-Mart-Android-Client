package org.the_chance.honeymart.ui.feature.home

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.model.CouponEntity
import org.the_chance.honeymart.domain.model.MarketEntity
import org.the_chance.honeymart.domain.model.OrderEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.model.RecentProductEntity
import org.the_chance.honeymart.domain.model.ValidCouponEntity
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.usecase.GetAllMarketsUseCase
import org.the_chance.honeymart.domain.usecase.GetAllOrdersUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsUseCase
import org.the_chance.honeymart.domain.usecase.GetAllWishListUseCase
import org.the_chance.honeymart.domain.usecase.GetCouponsUseCase
import org.the_chance.honeymart.domain.usecase.GetRecentProductsUseCase
import org.the_chance.honeymart.domain.usecase.WishListOperationsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.category.toCategoryUiState
import org.the_chance.honeymart.ui.feature.market.toMarketUiState
import org.the_chance.honeymart.ui.feature.orders.OrderStates
import org.the_chance.honeymart.ui.feature.orders.toOrderUiState
import org.the_chance.honeymart.ui.feature.product.toProductUiState
import org.the_chance.honeymart.ui.feature.wishlist.WishListProductUiState
import org.the_chance.honeymart.ui.feature.wishlist.toWishListProductUiState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllMarket: GetAllMarketsUseCase,
    private val getAllRecentProducts: GetRecentProductsUseCase,
    private val getAllDiscoverProducts: GetAllProductsUseCase,
    private val getAllOrders: GetAllOrdersUseCase,
    private val wishListOperationsUseCase: WishListOperationsUseCase,
    private val getAllWishList: GetAllWishListUseCase,
    private val getAllCoupons: GetCouponsUseCase,
    private val getMarketSpecificCategory: GetAllCategoriesInMarketUseCase
) : BaseViewModel<HomeUiState, HomeUiEffect>(HomeUiState()), HomeInteractionListener {

    override val TAG: String = this::class.java.simpleName

    override fun getData() {
        _state.update {
            it.copy(isLoading = true, error = null, isConnectionError = false)
        }
        getDoneOrders()
        getDiscoverProducts()
        getRecentProducts()
        getAllUserCoupons()
        getAllMarkets()
        getWishListProducts()
    }

    private fun getDoneOrders() {
        tryToExecute(
            { getAllOrders(OrderStates.DONE.state) },
            ::onGetDoneOrdersSuccess,
            ::onGetDoneOrdersError
        )
    }

    private fun onGetDoneOrdersSuccess(orders: List<OrderEntity>) {
        _state.update {
            it.copy(
                lastPurchases = orders.map { order -> order.toOrderUiState() })
        }
    }

    private fun onGetDoneOrdersError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isConnectionError = error is ErrorHandler.NoConnection
            )
        }
    }

    private fun getDiscoverProducts() {
        tryToExecute(
            getAllDiscoverProducts::invoke,
            ::onGetDiscoverProductsSuccess,
            ::onGetDiscoverProductsError,
        )
    }

    private fun onGetDiscoverProductsSuccess(products: List<ProductEntity>) {
        _state.update {
            it.copy(
                discoverProducts = products.map { product -> product.toProductUiState() }
            )
        }
        Log.i(
            "DiscoverProducts",
            "onGetDiscoverProductsSuccess: ${products.map { product -> product.toProductUiState() }}"
        )
    }

    private fun onGetDiscoverProductsError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isConnectionError = error is ErrorHandler.NoConnection
            )
        }
        Log.i(
            "ErrorDiscoverProducts", "onGetDiscoverProductsError: $error"
        )
    }

    private fun getRecentProducts() {
        tryToExecute(
            getAllRecentProducts::invoke,
            ::onGetRecentProductsSuccess,
            ::onGetRecentProductsError,
        )
    }

    private fun onGetRecentProductsSuccess(products: List<RecentProductEntity>) {
        _state.update {
            it.copy(
                recentProducts = products.map { product -> product.toRecentProductUiState() }
            )
        }
    }

    private fun onGetRecentProductsError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isConnectionError = error is ErrorHandler.NoConnection
            )
        }
    }

    private fun getAllUserCoupons() {
        tryToExecute(
            { getAllCoupons.getAllUserCoupons() },
            ::onGetAllUserCouponsSuccess,
            ::onGetValidCouponsError
        )
    }

    private fun onGetAllUserCouponsSuccess(coupons: List<CouponEntity>) {
        _state.update {
            it.copy(
                isLoading = false,
                coupons = coupons.map { coupon -> coupon.toCouponUiState() }
            )
        }
    }

    private fun onGetValidCouponsError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isConnectionError = error is ErrorHandler.NoConnection
            )
        }

        if (error is ErrorHandler.UnAuthorizedUser)
            getAllValidCoupons()
    }

    private fun getAllValidCoupons() {
        tryToExecute(
            { getAllCoupons.getAllValidCoupons() },
            ::onGetAllValidCouponsSuccess,
            ::onGetAllValidCouponsError
        )
    }

    private fun onGetAllValidCouponsSuccess(coupons: List<ValidCouponEntity>) {
        _state.update {
            it.copy(
                validCoupons = coupons.map { coupon -> coupon.toValidCouponUiState() },
            )
        }
    }

    private fun onGetAllValidCouponsError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isConnectionError = error is ErrorHandler.NoConnection
            )
        }
    }

    override fun onClickCategory(categoryId: Long, marketId: Long, position: Int) {
        effectActionExecutor(
            _effect,
            HomeUiEffect.NavigateToProductScreenEffect(categoryId, marketId, position)
        )
    }

    private fun getMarketCategories(marketId: Long) {
        tryToExecute(
            { getMarketSpecificCategory(marketId) },
            ::onGetMarketSpecificCategorySuccess,
            ::onGetMarketSpecificCategoryError
        )
    }

    private fun onGetMarketSpecificCategoryError(errorHandler: ErrorHandler) {

    }

    private fun onGetMarketSpecificCategorySuccess(categories: List<CategoryEntity>) {
        _state.update {
            it.copy(
                isLoading = false,
                categories = categories.map { it.toCategoryUiState() }
            )
        }
    }


    private fun getAllMarkets() {
        tryToExecute(
            getAllMarket::invoke,
            ::onGetMarketSuccess,
            ::onGetMarketError
        )
    }

    private fun onGetMarketSuccess(markets: List<MarketEntity>) {
        _state.update {
            it.copy(
                markets = markets.map { market -> market.toMarketUiState() }
            )
        }

        if (markets.isNotEmpty()) {
            _state.update {
                it.copy(
                    selectedMarketId = markets[0].marketId
                )
            }
            getMarketCategories(markets[0].marketId)
        }
    }

    private fun onGetMarketError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isConnectionError = error is ErrorHandler.NoConnection,
            )
        }
    }

    override fun onClickPagerItem(marketId: Long) {
        effectActionExecutor(_effect, HomeUiEffect.NavigateToMarketScreenEffect(marketId))
    }

    override fun onClickCouponClipped(couponId: Long) {
        _state.value.coupons.find { it.couponId == couponId }?.let {
            _state.update {
                it.copy(
                    coupons = it.coupons.map { coupon ->
                        if (coupon.couponId == couponId) {
                            coupon.copy(isClipped = !coupon.isClipped)
                        } else {
                            coupon
                        }
                    }
                )
            }
        }
    }

    override fun onClickProductItem(productId: Long) {
        effectActionExecutor(_effect, HomeUiEffect.NavigateToProductsDetailsScreenEffect(productId))
    }

    override fun onClickFavoriteDiscoverProduct(productId: Long) {
        if (_state.value.discoverProducts.find { it.productId == productId }?.isFavorite == false)
            addProductToWishList(productId)
        else
            deleteProductFromWishList(productId)
    }

    override fun onClickSearchBar() {
        effectActionExecutor(_effect, HomeUiEffect.NavigateToSearchScreenEffect)
    }

    override fun onClickChipCategory(marketId: Long) {
        _state.update { it.copy(selectedMarketId = marketId) }
        getMarketCategories(marketId)
    }

    override fun onClickFavoriteNewProduct(productId: Long) {
        if (_state.value.recentProducts.find { it.productId == productId }?.isFavorite == false)
            addProductToWishList(productId)
        else
            deleteProductFromWishList(productId)
    }

    private fun addProductToWishList(productId: Long) {
        tryToExecute(
            { wishListOperationsUseCase.addToWishList(productId) },
            { onAddToWishListSuccess(it, productId) },
            ::onAddToWishListError
        )
    }

    private fun onDeleteWishListSuccess(successMessage: String, productId: Long) {
        _state.update {
            it.copy(
                isLoading = false,
            )
        }
        getWishListProducts()
    }

    private fun onDeleteWishListError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isConnectionError = error is ErrorHandler.NoConnection
            )
        }
    }

    private fun onAddToWishListSuccess(successMessage: String, productId: Long) {
        _state.update { it.copy(isLoading = false) }
        getWishListProducts()
    }

    private fun onAddToWishListError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isConnectionError = error is ErrorHandler.NoConnection
            )
        }

        if (error is ErrorHandler.UnAuthorizedUser)
            effectActionExecutor(_effect, HomeUiEffect.UnAuthorizedUserEffect)
    }

    private fun deleteProductFromWishList(productId: Long) {
        tryToExecute(
            { wishListOperationsUseCase.deleteFromWishList(productId) },
            { onDeleteWishListSuccess(it, productId) },
            ::onDeleteWishListError
        )
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

    /*    private fun updateProducts(
            products: List<ProductUiState>,
            wishListProducts: List<WishListProductUiState>,
        ) = products.map { product ->
            product.copy(isFavorite = product.productId in wishListProducts.map { it.productId })
        }*/

    private fun onGetWishListProductError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isConnectionError = error is ErrorHandler.NoConnection,
            )
        }
    }
}


