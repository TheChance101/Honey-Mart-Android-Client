package org.the_chance.honeymart.ui.feature.home

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.model.CouponEntity
import org.the_chance.honeymart.domain.model.MarketEntity
import org.the_chance.honeymart.domain.model.OrderEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.model.RecentProductEntity
import org.the_chance.honeymart.domain.usecase.ClipCouponUseCase
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
import org.the_chance.honeymart.ui.feature.new_products.toRecentProductUiState
import org.the_chance.honeymart.ui.feature.markets.toMarketUiState
import org.the_chance.honeymart.ui.feature.orders.OrderStates
import org.the_chance.honeymart.ui.feature.orders.toOrderUiState
import org.the_chance.honeymart.ui.feature.product.toProductUiState
import org.the_chance.honeymart.ui.feature.wishlist.WishListProductUiState
import org.the_chance.honeymart.ui.feature.wishlist.toWishListProductUiState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllMarkets: GetAllMarketsUseCase,
    private val getAllCategoriesInMarket: GetAllCategoriesInMarketUseCase,
    private val getCoupons: GetCouponsUseCase,
    private val getRecentProducts: GetRecentProductsUseCase,
    private val getAllDiscoverProducts: GetAllProductsUseCase,
    private val getAllOrders: GetAllOrdersUseCase,
    private val wishListOperationsUseCase: WishListOperationsUseCase,
    private val getAllWishList: GetAllWishListUseCase,
    private val clipCouponsUseCase: ClipCouponUseCase,
) : BaseViewModel<HomeUiState, HomeUiEffect>(HomeUiState()), HomeInteractionListener {

    override val TAG: String = this::class.java.simpleName

    override fun getData() {
        _state.update {
            it.copy(isLoading = true, error = null, isConnectionError = false)
        }
        getAllMarkets()
        getUserCoupons()
        getRecentProducts()
        getDoneOrders()
        getDiscoverProducts()
        getWishListProducts()
    }

    /// region Markets
    private fun getAllMarkets() {
        tryToExecute(
            getAllMarkets::invoke,
            ::onGetMarketSuccess,
            ::onGetMarketError
        )
    }

    private fun onGetMarketSuccess(markets: List<MarketEntity>) {
        _state.update {
            it.copy(markets = markets.map { market -> market.toMarketUiState() })
        }

        if (markets.isNotEmpty()) {
            _state.update { it.copy(selectedMarketId = markets[0].marketId) }
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
    /// endregion

    /// region Categories
    private fun getMarketCategories(marketId: Long) {
        tryToExecute(
            { getAllCategoriesInMarket(marketId) },
            ::onGetAllCategoriesInMarketSuccess,
            ::onGetAllCategoriesInMarketError
        )
    }

    private fun onGetAllCategoriesInMarketSuccess(categories: List<CategoryEntity>) {
        _state.update {
            it.copy(
                isLoading = false,
                categories = categories.map { category -> category.toCategoryUiState() }
            )
        }
    }

    private fun onGetAllCategoriesInMarketError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isConnectionError = error is ErrorHandler.NoConnection,
            )
        }
    }
    /// endregion

    /// region Coupons
    private fun getUserCoupons() {
        tryToExecute(
            { getCoupons.getUserCoupons() },
            ::onGetCouponsSuccess,
            ::onGetUserCouponsError
        )
    }

    private fun onGetUserCouponsError(error: ErrorHandler) {
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
            { getCoupons.getAllValidCoupons() },
            ::onGetCouponsSuccess,
            ::onGetAllValidCouponsError
        )
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

    private fun onGetCouponsSuccess(coupons: List<CouponEntity>) {
        _state.update {
            it.copy(
                isLoading = false,
                coupons = coupons.map { coupon -> coupon.toCouponUiState() }
            )
        }
    }
    /// endregion

    /// region Recent Products
    private fun getRecentProducts() {
        tryToExecute(
            getRecentProducts::invoke,
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
    /// endregion

    /// region Done Products

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
    /// endregion

    /// region Discover Products

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
    }

    private fun onGetDiscoverProductsError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isConnectionError = error is ErrorHandler.NoConnection
            )
        }
    }
    /// endregion

    /// region WishList Products

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
                isConnectionError = error is ErrorHandler.NoConnection,
            )
        }
    }
    /// endregion

    /// region Interactions

    override fun onClickCategory(categoryId: Long, position: Int) {
        effectActionExecutor(
            _effect,
            HomeUiEffect.NavigateToProductScreenEffect(
                categoryId,
                state.value.selectedMarketId,
                position
            )
        )
    }

    override fun onClickPagerItem(marketId: Long) {
        effectActionExecutor(_effect, HomeUiEffect.NavigateToMarketScreenEffect(marketId))
    }

    override fun onClickSeeAllMarkets() {
        effectActionExecutor(_effect, HomeUiEffect.NavigateToSeeAllMarketEffect )
    }

    override fun onClickGetCoupon(couponId: Long) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { clipCouponsUseCase(couponId) },
            { onClipCouponSuccess() },
            ::onClipCouponError
        )
    }

    private fun onClipCouponSuccess() {
        getUserCoupons()
    }

    private fun onClipCouponError(errorHandler: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = errorHandler,
                isConnectionError = errorHandler is ErrorHandler.NoConnection,
            )
        }

        if (errorHandler is ErrorHandler.UnAuthorizedUser)
            effectActionExecutor(_effect, HomeUiEffect.UnAuthorizedUserEffect)
    }

    override fun onClickProductItem(productId: Long) {
        effectActionExecutor(_effect, HomeUiEffect.NavigateToProductsDetailsScreenEffect(productId))
    }

    override fun onClickFavoriteDiscoverProduct(productId: Long) {
        _state.update { it.copy(isLoading = true) }
        if (_state.value.discoverProducts.find { it.productId == productId }?.isFavorite == false)
            addProductToWishList(productId)
        else
            deleteProductFromWishList(productId)
    }

    override fun onClickSearchBar() {
        effectActionExecutor(_effect, HomeUiEffect.NavigateToSearchScreenEffect)
    }

    override fun onClickChipCategory(marketId: Long) {
        _state.update { it.copy(selectedMarketId = marketId, isLoading = true) }
        getMarketCategories(marketId)
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
                isConnectionError = error is ErrorHandler.NoConnection
            )
        }

        if (error is ErrorHandler.UnAuthorizedUser)
            effectActionExecutor(_effect, HomeUiEffect.UnAuthorizedUserEffect)
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
                isConnectionError = error is ErrorHandler.NoConnection
            )
        }
    }

    override fun onClickSeeAllNewProducts() {
        effectActionExecutor(_effect, HomeUiEffect.NavigateToNewProductsScreenEffect)
    }

    /// endregion
}


