package org.the_chance.honeymart.ui.features.coupons

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.usecase.AddCouponUseCase
import org.the_chance.honeymart.domain.usecase.GetNoCouponMarketProductsUseCase
import org.the_chance.honeymart.domain.usecase.SearchNoCouponMarketProductsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import java.util.Date
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CouponsViewModel @Inject constructor(
    private val getNoCouponMarketProducts: GetNoCouponMarketProductsUseCase,
    private val searchNoCouponMarketProducts: SearchNoCouponMarketProductsUseCase,
    private val addCoupon: AddCouponUseCase
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
            state.copy(
                isConnectionError = false,
                error = null,
                productSearch = state.productSearch.copy(
                    isLoading = true
                )
            )
        }
        tryToExecute(
            { getNoCouponMarketProducts() },
            ::onGetMarketProductsSuccess,
            ::onGetMarketProductsError
        )
    }

    private fun onGetMarketProductsSuccess(products: List<Product>) {
        _state.update { state ->
            state.copy(
                productSearch = state.productSearch.copy(
                    isLoading = false,
                    products = products.toProductUiState()
                )
            )
        }
    }

    private fun onGetMarketProductsError(errorHandler: ErrorHandler) {
        _state.update { state ->
            state.copy(
                isConnectionError = errorHandler is ErrorHandler.NoConnection,
                productSearch = state.productSearch.copy(
                    isLoading = false
                ),
                error = errorHandler
            )
        }
    }

    private fun searchMarketProducts(keyword: String) {
        tryToExecute(
            { searchNoCouponMarketProducts(keyword) },
            ::onSearchMarketProductsSuccess,
            ::onSearchMarketProductsError
        )
    }

    private fun onSearchMarketProductsSuccess(products: List<Product>) {
        _state.update { state ->
            state.copy(
                productSearch = state.productSearch.copy(
                    isLoading = false,
                    products = products.toProductUiState()
                )
            )
        }
    }

    private fun onSearchMarketProductsError(errorHandler: ErrorHandler) {
        _state.update { state ->
            state.copy(
                isConnectionError = errorHandler is ErrorHandler.NoConnection,
                productSearch = state.productSearch.copy(
                    isLoading = false
                ),
                error = errorHandler
            )
        }
    }

    override fun onProductSearchItemClick(productId: Long) {
        _state.update { state ->
            state.copy(
                addCoupon = AddCouponUiState(
                    coupon = CouponUiState(
                        product = state.productSearch.products.first { it.productId == productId })
                )
            )
        }
    }

    override fun onProductSearchTextChange(text: String) {
        _state.update { state ->
            state.copy(
                productSearch = state.productSearch.copy(
                    searchText = text
                )
            )
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
            state.copy(
                isConnectionError = false,
                error = null,
                addCoupon = state.addCoupon.copy(
                    isLoading = true
                )
            )
        }
        tryToExecute(
            {
                addCoupon(
                    productId = _state.value.addCoupon.coupon.product.productId,
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
            state.copy(
                addCoupon = AddCouponUiState(),
            )
        }
        getMarketProducts()
    }

    private fun onAddCouponError(errorHandler: ErrorHandler) {
        _state.update { state ->
            state.copy(
                isConnectionError = errorHandler is ErrorHandler.NoConnection,
                addCoupon = state.addCoupon.copy(
                    isLoading = false
                ),
                error = errorHandler
            )
        }
    }

    override fun onDiscountPercentageChange(discountPercentage: String) {
        val discountPercentageState = getDiscountPercentageState(discountPercentage)
        _state.update { state ->
            state.copy(
                addCoupon = state.addCoupon.copy(
                    discountPercentage = discountPercentage,
                    discountPercentageState = discountPercentageState,
                    coupon = state.addCoupon.coupon.copy(
                        offerPrice = if (discountPercentageState == ValidationState.VALID_TEXT_FIELD) {
                            state.addCoupon.coupon.product.productPrice
                                .toOfferPrice(discountPercentage.toDouble())
                                .toCouponPriceFormat()
                        } else {
                            state.addCoupon.coupon.offerPrice
                        }
                    )
                )
            )
        }
    }

    override fun onCouponCountChange(couponCount: String) {
        val couponCountState = getCouponCountState(couponCount)
        _state.update { state ->
            state.copy(
                addCoupon = state.addCoupon.copy(
                    couponCount = couponCount,
                    couponCountState = couponCountState,
                    coupon = state.addCoupon.coupon.copy(
                        count = if (couponCountState == ValidationState.VALID_TEXT_FIELD) {
                            couponCount
                        } else {
                            state.addCoupon.coupon.count
                        }
                    )
                )
            )
        }
    }

    override fun onClickShowDatePicker() {
        _state.update { state ->
            state.copy(
                isDatePickerVisible = true
            )
        }
    }

    override fun onDatePickerDoneClick(date: Long) {
        _state.update { state ->
            state.copy(
                isDatePickerVisible = false,
                addCoupon = state.addCoupon.copy(
                    expirationDate = Date(date),
                    coupon = state.addCoupon.coupon.copy(
                        expirationDate = Date(date).toCouponExpirationDateFormat()
                    )
                )
            )
        }
    }

    override fun onDatePickerDismiss() {
        _state.update { state ->
            state.copy(
                isDatePickerVisible = false
            )
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