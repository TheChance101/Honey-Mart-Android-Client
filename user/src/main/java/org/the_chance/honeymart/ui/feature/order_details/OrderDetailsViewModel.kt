package org.the_chance.honeymart.ui.feature.order_details

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.OrderDetails
import org.the_chance.honeymart.domain.usecase.AddReviewUseCase
import org.the_chance.honeymart.domain.usecase.usecaseManager.user.UserOrderDetailsManagerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val orderDetails: UserOrderDetailsManagerUseCase,
    private val addReview: AddReviewUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<OrderDetailsUiState, OrderDetailsUiEffect>(OrderDetailsUiState()),
    OrderDetailsInteractionListener {

    override val TAG: String = this::class.java.simpleName

    private val orderArgs = OrderDetailsArgs(savedStateHandle)

    init {
        getData()
    }

    private fun getData() {
        getOrderProducts()
        getOrderDetails()
    }

    private fun getOrderProducts() {
        _state.update { it.copy(isProductsLoading = true, isError = false) }
        tryToExecute(
            { orderDetails.getOrderProductDetailsUseCase(orderArgs.orderId.toLong()) },
            ::onGetOrderProductsSuccess,
            ::onGetOrderProductsError
        )
    }

    private fun onGetOrderProductsSuccess(products: List<OrderDetails.ProductDetails>) {
        _state.update { orderDetailsUiState ->
            orderDetailsUiState.copy(
                isProductsLoading = false,
                products = products.map { it.toOrderDetailsProductUiState() })
        }
    }

    private fun onGetOrderProductsError(error: ErrorHandler) {
        _state.update { it.copy(isProductsLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    private fun getOrderDetails() {
        _state.update { it.copy(isDetailsLoading = true, isError = false) }
        tryToExecute(
            { orderDetails.getOrderDetailsUseCase(orderArgs.orderId.toLong()) },
            ::onGetOrderDetailsSuccess,
            ::onGetOrderDetailsError
        )
    }

    private fun onGetOrderDetailsSuccess(orderDetails: OrderDetails) {
        _state.update {
            it.copy(
                isDetailsLoading = false,
                orderDetails = orderDetails.toOrderParentDetailsUiState()
            )
        }
    }

    private fun onGetOrderDetailsError(error: ErrorHandler) {
        _state.update { it.copy(isDetailsLoading = false, error = error) }
    }

    override fun onClickOrder(productId: Long) {
        effectActionExecutor(_effect, OrderDetailsUiEffect.ClickProductEffect(productId))

    }

    override fun onClickSubmitReview() {
        _state.update {
            it.copy(
                addReviewBottomSheetUiState = it.addReviewBottomSheetUiState.copy(
                    isLoading = true
                )
            )
        }

        log(
            "productId: ${_state.value.addReviewBottomSheetUiState.productId}\n " +
                    "orderId: ${orderArgs.orderId.toLong()}\n " +
                    "rating: ${_state.value.addReviewBottomSheetUiState.rating}\n " +
                    "review: ${_state.value.addReviewBottomSheetUiState.review}"
        )

        tryToExecute(
            {
                addReview(
                    productId = _state.value.addReviewBottomSheetUiState.productId,
                    orderId = orderArgs.orderId.toLong(),
                    rating = _state.value.addReviewBottomSheetUiState.rating,
                    review = _state.value.addReviewBottomSheetUiState.review
                )
            },
            { onAddReviewSuccess() },
            ::onAddReviewError
        )
    }

    private fun onAddReviewSuccess() {
        _state.update {
            it.copy(
                addReviewBottomSheetUiState = AddReviewBottomSheetUiState()
            )
        }
        effectActionExecutor(
            _effect,
            OrderDetailsUiEffect.ShowToastEffect("Review added successfully")
        )
    }

    private fun onAddReviewError(errorHandler: ErrorHandler) {
        _state.update {
            it.copy(
                isError = errorHandler is ErrorHandler.NoConnection,
                addReviewBottomSheetUiState = it.addReviewBottomSheetUiState.copy(
                    isLoading = false,
                )
            )
        }
        effectActionExecutor(
            _effect,
            OrderDetailsUiEffect.ShowToastEffect("Error while adding review")
        )
        log(errorHandler.toString())
    }

    override fun onClickAddReview(productId: Long) {
        _state.update {
            it.copy(
                addReviewBottomSheetUiState = AddReviewBottomSheetUiState(
                    isVisible = true,
                    productId = productId
                )
            )
        }
    }

    override fun onDismissAddReviewSheet() {
        _state.update {
            it.copy(
                addReviewBottomSheetUiState = it.addReviewBottomSheetUiState.copy(
                    isVisible = false
                )
            )
        }
    }

    override fun onRatingChange(rating: Float) {
        _state.update {
            it.copy(
                addReviewBottomSheetUiState = it.addReviewBottomSheetUiState.copy(
                    rating = rating
                )
            )
        }
    }

    override fun onReviewChange(review: String) {
        _state.update {
            it.copy(
                addReviewBottomSheetUiState = it.addReviewBottomSheetUiState.copy(
                    review = review
                )
            )
        }
    }
}