package org.the_chance.honeymart.ui.features.coupons

import androidx.lifecycle.viewModelScope
import arrow.optics.Every
import arrow.optics.copy
import arrow.optics.dsl.every
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.usecase.usecaseManager.owner.OwnerCouponsManagerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import java.util.Date
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CouponsViewModel @Inject constructor(
    private val ownerCoupons: OwnerCouponsManagerUseCase,
) : BaseViewModel<CouponsUiState, CouponsUiEffect>(CouponsUiState()), CouponsInteractionListener {

    override val TAG: String = this::class.java.simpleName

    private val actionStream = MutableSharedFlow<String>()

    init {
        getMarketProducts()
        observeKeyword()
    }

    //region Search Product
    override fun getMarketProducts() {
        _state.update { state ->
            state.copy {
                CouponsUiState.isConnectionError set false
                CouponsUiState.productSearch.isLoading set true
            }
        }
        tryToExecute(
            { ownerCoupons.getNoCouponMarketProducts() },
            ::onGetMarketProductsSuccess,
            ::onGetMarketProductsError
        )
    }

    private fun onGetMarketProductsSuccess(products: List<Product>) {
        _state.update { state ->
            state.copy {
                CouponsUiState.productSearch.isLoading set false
                CouponsUiState.productSearch.products set products.toProductUiState().map {
                    it.copy { ProductUiState.isSelected set (state.addCoupon.coupon.product.id == it.id) }
                }
            }
        }
    }

    private fun onGetMarketProductsError(errorHandler: ErrorHandler) {
        _state.update { state ->
            state.copy {
                CouponsUiState.isConnectionError set (errorHandler is ErrorHandler.NoConnection)
                CouponsUiState.productSearch.isLoading set false
                CouponsUiState.error set errorHandler
            }
        }
    }

    private fun searchMarketProducts(keyword: String) {
        tryToExecute(
            { ownerCoupons.searchNoCouponMarketProducts(keyword) },
            ::onSearchMarketProductsSuccess,
            ::onSearchMarketProductsError
        )
    }

    private fun onSearchMarketProductsSuccess(products: List<Product>) {
        _state.update { state ->
            state.copy {
                CouponsUiState.productSearch.isLoading set false
                CouponsUiState.productSearch.products set products.toProductUiState().map {
                    it.copy { ProductUiState.isSelected set (state.addCoupon.coupon.product.id == it.id) }
                }
            }
        }
    }

    private fun onSearchMarketProductsError(errorHandler: ErrorHandler) {
        _state.update { state ->
            state.copy {
                CouponsUiState.isConnectionError set (errorHandler is ErrorHandler.NoConnection)
                CouponsUiState.productSearch.isLoading set false
                CouponsUiState.error set errorHandler
            }
        }
    }

    override fun onProductSearchItemClick(productId: Long) {
        _state.update { state ->
            state.copy {
                CouponsUiState.productSearch.products set state.productSearch.products.map {
                    it.copy { ProductUiState.isSelected set (it.id == productId) }
                }
                CouponsUiState.addCoupon.coupon set CouponUiState(
                    product = state.productSearch.products.first { it.id == productId }
                )
            }
        }
    }

    override fun onProductSearchTextChange(text: String) {
        _state.update { state ->
            state.copy { CouponsUiState.productSearch.searchText set text }
        }
        viewModelScope.launch { actionStream.emit(text) }
    }

    private fun observeKeyword() {
        viewModelScope.launch(Dispatchers.Unconfined) {
            actionStream.debounce(700).distinctUntilChanged().collect {
                log(it)
                if (it.isBlank()) {
                    getMarketProducts()
                } else {
                    searchMarketProducts(it)
                }
            }
        }
    }
    //endregion

    //region Add Coupon
    override fun onAddCouponClick() {
        _state.update { state ->
            state.copy {
                CouponsUiState.isConnectionError set false
                CouponsUiState.addCoupon.isLoading set true
            }
        }
        tryToExecute(
            {
                ownerCoupons.addCoupon(
                    productId = _state.value.addCoupon.coupon.product.id,
                    discountPercentage = _state.value.addCoupon.discountPercentage.toDouble(),
                    count = _state.value.addCoupon.coupon.count.toInt(),
                    expirationDate = _state.value.addCoupon.expirationDate!!
                )
            },
            { onAddCouponSuccess() },
            ::onAddCouponError
        )
    }

    private fun onAddCouponSuccess() {
        _state.update { state ->
            state.copy { CouponsUiState.addCoupon set AddCouponUiState() }
        }
        getMarketProducts()
    }

    private fun onAddCouponError(errorHandler: ErrorHandler) {
        _state.update { state ->
            state.copy {
                CouponsUiState.isConnectionError set (errorHandler is ErrorHandler.NoConnection)
                CouponsUiState.addCoupon.isLoading set false
                CouponsUiState.error set errorHandler
            }
        }
    }

    override fun onDiscountPercentageChange(discountPercentage: String) {
        val discountPercentageState = getDiscountPercentageState(discountPercentage)
        _state.update { state ->
            state.copy {
                CouponsUiState.addCoupon.discountPercentage set discountPercentage
                CouponsUiState.addCoupon.discountPercentageState set discountPercentageState
                CouponsUiState.addCoupon.coupon.offerPrice set if (discountPercentageState == ValidationState.VALID_TEXT_FIELD) {
                    state.addCoupon.coupon.product.price
                        .toOfferPrice(discountPercentage.toDouble())
                        .toCouponPriceFormat()
                } else {
                    state.addCoupon.coupon.offerPrice
                }
            }
        }
    }

    override fun onCouponCountChange(couponCount: String) {
        val couponCountState = getCouponCountState(couponCount)
        _state.update { state ->
            state.copy {
                CouponsUiState.addCoupon.couponCount set couponCount
                CouponsUiState.addCoupon.couponCountState set couponCountState
                CouponsUiState.addCoupon.coupon.count set if (couponCountState == ValidationState.VALID_TEXT_FIELD) {
                    couponCount
                } else {
                    state.addCoupon.coupon.count
                }
            }
        }
    }

    override fun onClickShowDatePicker() {
        _state.update { state ->
            state.copy { CouponsUiState.isDatePickerVisible set true }
        }
    }

    override fun onDatePickerDoneClick(date: Long) {
        _state.update { state ->
            state.copy {
                CouponsUiState.isDatePickerVisible set false
                CouponsUiState.addCoupon.expirationDate set Date(date)
                CouponsUiState.addCoupon.coupon.expirationDate set Date(date).toCouponExpirationDateFormat()
            }
        }
    }

    override fun onDatePickerDismiss() {
        _state.update { state ->
            state.copy { CouponsUiState.isDatePickerVisible set false }
        }
    }

    private fun getDiscountPercentageState(discountPercentage: String): ValidationState {
        val percentageRegex = Regex("^(100(\\.0{1,2})?|\\d{1,2}(\\.\\d{1,2})?)$")
        val state: ValidationState = when {
            discountPercentage.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            !discountPercentage.matches(percentageRegex) -> ValidationState.INVALID_COUPON_DISCOUNT_PERCENTAGE
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return state
    }

    private fun getCouponCountState(couponCount: String): ValidationState {
        val numberRegex = Regex("^[1-9]\\d*$")
        val state: ValidationState = when {
            couponCount.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            !couponCount.matches(numberRegex) -> ValidationState.INVALID_COUPON_COUNT
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return state
    }
}