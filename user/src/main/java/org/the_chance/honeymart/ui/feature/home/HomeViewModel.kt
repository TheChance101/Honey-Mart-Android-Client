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
import org.the_chance.honeymart.domain.usecase.GetRecentProductsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.market.toMarketUiState
import org.the_chance.honeymart.ui.feature.orders.OrderStates
import org.the_chance.honeymart.ui.feature.orders.toOrderUiState
import org.the_chance.honeymart.ui.feature.product.toProductUiState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllMarket: GetAllMarketsUseCase,
    private val getAllUserCoupons: GetAllUserCoupons,
    private val getAllRecentProducts: GetRecentProductsUseCase,
    private val getAllDiscoverProducts: GetAllProductsUSeCase,
    private val getAllOrders: GetAllOrdersUseCase,
    ) :
    BaseViewModel<HomeUiState, HomeUiEffect>(HomeUiState()) {
    override val TAG: String = this::class.java.simpleName


    init {
        getAllMarkets()
        getCoupons()
        getRecentProducts()
        getAllDiscoverProducts()
        getAllDoneOrders()
    }

     private fun getAllDoneOrders() {
        _state.update {
            it.copy(isLoading = true, orderStates = OrderStates.DONE, isError = false)
        }
        tryToExecute(
            { getAllOrders(OrderStates.DONE.state)},
            ::onGetDoneOrdersSuccess,
            ::onGetDoneOrdersError
        )

    }

    private fun onGetDoneOrdersSuccess(orders: List<OrderEntity>) {
        _state.update {
            it.copy(isLoading = false, lastPurchases = orders.map { it.toOrderUiState() } ) }
    }

    private fun onGetDoneOrdersError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    private fun getAllDiscoverProducts() {
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
}