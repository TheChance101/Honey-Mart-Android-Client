package org.the_chance.honeymart.ui.feature.productreview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.model.Reviews
import org.the_chance.honeymart.domain.usecase.GetAllProductReviewsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.product_details.ProductDetailsArgs
import javax.inject.Inject

@HiltViewModel
class ProductReviewsViewModel @Inject constructor(
    private val productReviewsUseCase: GetAllProductReviewsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProductReviewsUiState, ProductReviewsUiEffect>(ProductReviewsUiState()),
    ProductReviewsInteractionsListener {

    override val TAG: String = this::class.simpleName.toString()

    private val args = ProductDetailsArgs(savedStateHandle)
    private var page = state.value.page
    private var reviewScrollPosition = 0

    init {
        getData()
    }

    fun getData() {
        getProductReviews(args.productId.toLong())
    }

    private fun getProductReviews(productId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { productReviewsUseCase(productId, page) },
            ::onGetProductReviewsSuccess,
            ::onGetProductReviewsError
        )
    }

    private fun onGetProductReviewsSuccess(reviews: Reviews) {
        _state.update {
            it.copy(
                isLoading = false,
                reviews = reviews.toReviews(),
            )
        }
    }

    private fun onGetProductReviewsError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }



    fun onChangeReviews(position: Int) {
        reviewScrollPosition = position
    }

    private fun insert() {
        page += 1
    }

    override fun onScrollDown() {
        viewModelScope.launch {
            if ((reviewScrollPosition + 1) >= (page * MAX_PAGE_SIZE)) {
                _state.update { it.copy(isLoading = true) }
                insert()
                if (page > 1) {
                    val result = productReviewsUseCase(
                        args.productId.toLong(), page
                    ).toReviews()
                    appendReviews(result)
                }
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun appendReviews(reviews: ReviewDetailsUiState) {
        val current = ArrayList(state.value.reviews.reviews)
        current.addAll(reviews.reviews)
        _state.update {
            it.copy(
                reviews = reviews.copy(
                    reviewStatisticUiState = reviews.reviewStatisticUiState,
                    reviews = current
                )
            )
        }
    }

    override fun onClickBack() {
        effectActionExecutor(_effect, ProductReviewsUiEffect.OnBackClickEffect)
    }

    companion object {
        const val MAX_PAGE_SIZE = 10
    }

}