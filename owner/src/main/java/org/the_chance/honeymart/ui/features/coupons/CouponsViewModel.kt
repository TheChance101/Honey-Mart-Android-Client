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
import org.the_chance.honeymart.domain.usecase.usecaseManager.owner.OwnerCouponsManagerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.util.StringDictionary
import java.util.Date
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class CouponsViewModel @Inject constructor(
    private val ownerCoupons: OwnerCouponsManagerUseCase,
    private val stringResource: StringDictionary,
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
            { ownerCoupons.getNoCouponMarketProducts() },
            ::onGetMarketProductsSuccess,
            ::onGetMarketProductsError
        )
    }

    private fun onGetMarketProductsSuccess(products: List<Product>) {
        _state.update { state ->
            state.copy(
                productSearch = state.productSearch.copy(
                    isLoading = false,
                    products = products.toProductUiState().map {
                        it.copy(isSelected = state.addCoupon.coupon.product.id == it.id)
                    }
                ),

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
            { ownerCoupons.searchNoCouponMarketProducts(keyword) },
            ::onSearchMarketProductsSuccess,
            ::onSearchMarketProductsError
        )
    }

    private fun onSearchMarketProductsSuccess(products: List<Product>) {
        _state.update { state ->
            state.copy(
                productSearch = state.productSearch.copy(
                    isLoading = false,
                    products = products.toProductUiState().map {
                        it.copy(isSelected = state.addCoupon.coupon.product.id == it.id)
                    }
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
                productSearch = state.productSearch.copy(
                    products = state.productSearch.products.map {
                        it.copy(isSelected = it.id == productId)
                    }
                ),
                addCoupon = AddCouponUiState(
                    coupon = CouponUiState(
                        product = state.productSearch.products.first { it.id == productId })
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
                ownerCoupons.addCoupon(
                    productId = _state.value.addCoupon.coupon.product.id,
                    discountPercentage = _state.value.addCoupon.discountPercentageState.name.toDouble(),
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

    override fun onDiscountPercentageChange(discountPercentage: CharSequence) {
        val discountPercentageState =
            ownerCoupons.validationUseCase.validateCouponsDiscountPercentage(
                discountPercentage.toString().trim()
            )
        _state.update { state ->
            state.copy(
                addCoupon = state.addCoupon.copy(
                    discountPercentageState = FieldState(
                        errorState = stringResource.validationString.getOrDefault(
                            discountPercentageState,
                            ""
                        ),
                        isValid = discountPercentageState == ValidationState.VALID_COUPON_DISCOUNT_PERCENTAGE,
                        name = discountPercentage.toString()
                    ),
                    coupon = state.addCoupon.coupon.copy(
                        offerPrice = if (state.addCoupon.discountPercentageState.isValid && discountPercentage.isNotBlank()) {
                            state.addCoupon.coupon.product.price
                                .toOfferPrice(discountPercentage.toString().toDouble())
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
        val couponCountState =
            ownerCoupons.validationUseCase.validateCouponCount(couponCount)
        _state.update { state ->
            state.copy(
                addCoupon = state.addCoupon.copy(
                    couponCountState = FieldState(
                        errorState = stringResource.validationString.getOrDefault(
                            couponCountState,
                            ""
                        ),
                        isValid = couponCountState == ValidationState.VALID_COUPON_COUNT,
                        name = couponCount
                    ),
                    coupon = state.addCoupon.coupon.copy(
                        count = if (state.addCoupon.couponCountState.isValid) {
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
}