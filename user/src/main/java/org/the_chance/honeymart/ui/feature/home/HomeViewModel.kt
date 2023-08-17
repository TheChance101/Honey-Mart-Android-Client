package org.the_chance.honeymart.ui.feature.home

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.CouponEntity
import org.the_chance.honeymart.domain.model.MarketEntity
import org.the_chance.honeymart.domain.usecase.GetAllMarketsUseCase
import org.the_chance.honeymart.domain.usecase.GetAllUserCoupons
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.market.toMarketUiState
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllMarket: GetAllMarketsUseCase,
    private val getAllUserCoupons: GetAllUserCoupons,
) :
    BaseViewModel<HomeUiState, HomeUiEffect>(HomeUiState()) {
    override val TAG: String = this::class.java.simpleName


    init {
        getAllMarkets()
    }

    private fun getCoupons()
    {
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