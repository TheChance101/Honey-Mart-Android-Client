package org.the_chance.honeymart.ui.feature.coupons

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.CouponEntity
import org.the_chance.honeymart.domain.usecase.GetClippedUserCouponsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.composables.coupon.toCouponUiState
import javax.inject.Inject

@HiltViewModel
class CouponsViewModel @Inject constructor(
    private val getClippedUserCouponsUseCase: GetClippedUserCouponsUseCase,

    ) : BaseViewModel<CouponsUiState, CouponsUiEffect>(CouponsUiState()),
    CouponsInteractionListener {
    override val TAG = this::class.java.simpleName

    override fun getData() {
        _state.update {
            it.copy(isLoading = true, error = null, isError = false)
        }
        tryToExecute(
            { getClippedUserCouponsUseCase() },
            ::onGetClippedUserCouponsSuccess,
            ::onGetClippedUserCouponsError
        )
    }

    private fun onGetClippedUserCouponsSuccess(coupons: List<CouponEntity>) {
        _state.update {
            it.copy(
                isLoading = false,
                coupons = coupons.map { coupon -> coupon.toCouponUiState() }
            )
        }
    }

    private fun onGetClippedUserCouponsError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                isError = error is ErrorHandler.NoConnection
            )
        }
    }


    override fun onClickGetCoupon(couponId: Long) {
    }

    override fun onClickAllCoupons() {
    }

    override fun onClickValidCoupons() {
    }

    override fun onClickExpiredCoupons() {
    }
}