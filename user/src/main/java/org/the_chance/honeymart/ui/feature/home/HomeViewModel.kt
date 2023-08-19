package org.the_chance.honeymart.ui.feature.home

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.CouponEntity
import org.the_chance.honeymart.domain.model.GetRecentProductsEntity
import org.the_chance.honeymart.domain.model.MarketEntity
import org.the_chance.honeymart.domain.model.OrderEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.usecase.GetAllMarketsUseCase
import org.the_chance.honeymart.domain.usecase.GetAllOrdersUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsUSeCase
import org.the_chance.honeymart.domain.usecase.GetAllUserCoupons
import org.the_chance.honeymart.domain.usecase.GetAllWishListUseCase
import org.the_chance.honeymart.domain.usecase.GetRecentProductsUseCase
import org.the_chance.honeymart.domain.usecase.WishListOperationsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.market.toMarketUiState
import org.the_chance.honeymart.ui.feature.orders.OrderStates
import org.the_chance.honeymart.ui.feature.orders.toOrderUiState
import org.the_chance.honeymart.ui.feature.product.ProductUiState
import org.the_chance.honeymart.ui.feature.product.toProductUiState
import org.the_chance.honeymart.ui.feature.wishlist.WishListProductUiState
import org.the_chance.honeymart.ui.feature.wishlist.toWishListProductUiState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllMarket: GetAllMarketsUseCase,
    private val getAllUserCoupons: GetAllUserCoupons,
    private val getAllRecentProducts: GetRecentProductsUseCase,
    private val getAllDiscoverProducts: GetAllProductsUSeCase,
    private val getAllOrders: GetAllOrdersUseCase,
    private val wishListOperationsUseCase: WishListOperationsUseCase,
    private val getAllWishList: GetAllWishListUseCase,

    ) :
    BaseViewModel<HomeUiState, HomeUiEffect>(HomeUiState()), HomeInteractionListener {
    override val TAG: String = this::class.java.simpleName



    override fun getData() {
        getAllMarkets()
        getCoupons()
        getRecentProducts()
        getAllDiscoverProducts()
        getAllDoneOrders()
        getWishListProducts(_state.value.discoverProducts)

    }

    override fun getAllDoneOrders() {
        _state.update {
            it.copy(isLoading = true, orderStates = OrderStates.PROCESSING, isError = false)
        }
        tryToExecute(
            { getAllOrders(OrderStates.PROCESSING.state) },
            ::onGetDoneOrdersSuccess,
            ::onGetDoneOrdersError
        )

    }

    private fun onGetDoneOrdersSuccess(orders: List<OrderEntity>) {
        _state.update {
            it.copy(isLoading = false, lastPurchases = orders.map { it.toOrderUiState() })
        }
    }

    private fun onGetDoneOrdersError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun getAllDiscoverProducts() {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            getAllDiscoverProducts::invoke,
            ::onGetDiscoverProductsSuccess,
            ::onGetDiscoverProductsError,
        )
    }


    private fun onGetDiscoverProductsError(errorHandler: ErrorHandler) {

    }

    private fun onGetDiscoverProductsSuccess(products: List<ProductEntity>) {

        _state.update {
            it.copy(
                isLoading = false,
                discoverProducts = products.map { it.toProductUiState() }
            )

        }
    }


    private fun getRecentProducts() {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            getAllRecentProducts::invoke,
            ::onGetRecentProductsSuccess,
            ::onGetRecentProductsError,
        )
    }


    fun onGetRecentProductsSuccess(products: List<GetRecentProductsEntity>) {
        _state.update {
            it.copy(
                isLoading = false,
                newProducts = products.map { it.toGetRecentProductUiState() }
            )
        }
    }

    fun onGetRecentProductsError(errorHandler: ErrorHandler) {

    }


    private fun getCoupons() {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            getAllUserCoupons::invoke,
            ::onGetCouponsSuccess,
            ::onGetCouponsError
        )
    }

    private fun onGetCouponsError(errorHandler: ErrorHandler) {

    }

    private fun onGetCouponsSuccess(coupon: List<CouponEntity>) {
        _state.update {
            it.copy(
                isLoading = false,
                coupons = coupon.map { it.toCouponUiState() }
            )
        }
    }


    private fun getAllMarkets() {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            getAllMarket::invoke,
            ::onGetMarketSuccess,
            ::onGetMarketError
        )
    }


    private fun onGetMarketSuccess(markets: List<MarketEntity>) {
        _state.update {
            it.copy(
                isLoading = false,
                markets = markets.map { it.toMarketUiState() }
            )
        }
    }

    private fun onGetMarketError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true, markets = emptyList()) }
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
        effectActionExecutor(_effect, HomeUiEffect.NavigateToProductScreenEffect(productId))
    }

    override fun onClickFavoriteDiscoverProduct(productId: Long) {
        _state.value.discoverProducts.find { it.productId == productId }?.let {
            _state.update {
                it.copy(
                    discoverProducts = it.discoverProducts.map { product ->
                        if (product.productId == productId) {
                            product.copy(isFavorite = !product.isFavorite)
                        } else {
                            product
                        }
                    }
                )
            }
        }
        addProductToWishList(productId)
        getWishListProducts(_state.value.discoverProducts)
    }

    override fun onClickSearchBar() {
        effectActionExecutor(_effect, HomeUiEffect.NavigateToSearchScreenEffect)
    }

    override fun onClickFavoriteNewProduct(productId: Long) {

        if (_state.value.newProducts.find { it.newProductId == productId }?.isFavorite == true)
            addProductToWishList(productId)
        else
            deleteProductFromWishList(productId)

    }


    private fun addProductToWishList(productId: Long) {
        tryToExecute(
            { wishListOperationsUseCase.addToWishList(productId) },
            { onAddToWishListSuccess(it, productId) },
            { onAddToWishListError(it) }
        )
    }

    private fun onDeleteWishListSuccess(successMessage: String, productId: Long) {
        _state.value.newProducts.find { it.newProductId == productId }?.let {
            _state.update {
                it.copy(
                    newProducts = it.newProducts.map { product ->
                        if (product.newProductId == productId) {
                            product.copy(isFavorite = !product.isFavorite)
                        } else {
                            product
                        }
                    }
                )
            }
        }
    }

    private fun onDeleteWishListError(error: ErrorHandler) {
        _state.update { it.copy(error = error, isError = true) }
    }

    private fun onAddToWishListSuccess(successMessage: String, productId: Long) {
        _state.value.newProducts.find { it.newProductId == productId }?.let {
            _state.update {
                it.copy(
                    newProducts = it.newProducts.map { product ->
                        if (product.newProductId == productId) {
                            product.copy(isFavorite = !product.isFavorite)
                        } else {
                            product
                        }
                    }
                )
            }
        }
    }

    private fun onAddToWishListError(error: ErrorHandler) {
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


    private fun getWishListProducts(products: List<ProductUiState>) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { getAllWishList().map { it.toWishListProductUiState() } },
            { onGetWishListProductSuccess(it, products) },
            { onGetWishListProductError(it, products) }
        )

    }

    private fun onGetWishListProductSuccess(
        wishListProducts: List<WishListProductUiState>, products: List<ProductUiState>,
    ) {
        _state.update { productsUiState ->
            productsUiState.copy(
                isLoading = false,
                discoverProducts = updateProducts(products, wishListProducts)
            )
        }
    }

    private fun updateProducts(
        products: List<ProductUiState>,
        wishListProducts: List<WishListProductUiState>,
    ) = products.map { product ->
        product.copy(isFavorite = product.productId in wishListProducts.map { it.productId })
    }

    private fun onGetWishListProductError(error: ErrorHandler, products: List<ProductUiState>) {
        _state.update { it.copy(isLoading = false, error = error, discoverProducts = products) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }
}