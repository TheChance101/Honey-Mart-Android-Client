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

    override fun onDiscountPercentageChange(discountPercentage: CharSequence) {
        val discountPercentageState =
            ownerCoupons.validationUseCase.validateCouponsDiscountPercentage(
                discountPercentage.toString().trim()
            )
        _state.update { state ->
            state.copy {
                CouponsUiState.addCoupon.discountPercentageState set FieldState(
                    errorState = stringResource.validationString.getOrDefault(
                        discountPercentageState,
                        ""
                    ),
                    isValid = discountPercentageState == ValidationState.VALID_COUPON_DISCOUNT_PERCENTAGE,
                    name = discountPercentage.toString()
                )
                CouponsUiState.addCoupon.coupon.offerPrice set if (state.addCoupon.discountPercentageState.isValid && discountPercentage.isNotBlank()) {
                    state.addCoupon.coupon.product.price
                        .toOfferPrice(discountPercentage.toString().toDouble())
                        .toCouponPriceFormat()
                } else {
                    state.addCoupon.coupon.offerPrice
                }
            }
        }
    }

    override fun onCouponCountChange(couponCount: String) {
        val couponCountState =
            ownerCoupons.validationUseCase.validateCouponCount(couponCount)
        _state.update { state ->
            state.copy {
                CouponsUiState.addCoupon.couponCountState set FieldState(
                    errorState = stringResource.validationString.getOrDefault(
                        couponCountState,
                        ""
                    ),
                    isValid = couponCountState == ValidationState.VALID_COUPON_COUNT,
                    name = couponCount
                )
                CouponsUiState.addCoupon.coupon.count set if (state.addCoupon.couponCountState.isValid) {
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
}