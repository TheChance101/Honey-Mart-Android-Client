package org.the_chance.honeymart.ui.feature.productreview

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.ProductReviewStatistic
import org.the_chance.honeymart.domain.usecase.GetProductReviewsStatistics
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.product_details.ProductDetailsArgs
import javax.inject.Inject

@HiltViewModel
class ProductReviewsViewModel @Inject constructor(
    private val productReviewsStatistics: GetProductReviewsStatistics,
//    private val productReviewsPagingUseCase: GetAllProductReviewsPagingUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProductReviewsUiState, ProductReviewsUiEffect>(ProductReviewsUiState()),
    ProductReviewsInteractionsListener {

    override val TAG: String = this::class.simpleName.toString()

    private val args = ProductDetailsArgs(savedStateHandle)


    init {
        getData()
    }

    fun getData() {
        getProductReviews(args.productId.toLong())
    }

    private fun getProductReviews(productId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { productReviewsStatistics(productId) },
            ::onGetProductReviewsSuccess,
            ::onGetProductReviewsError
        )
    }

    private fun onGetProductReviewsSuccess(productReviewStatistic: ProductReviewStatistic) {
        _state.update {
            it.copy(
                isLoading = false,
                reviews = productReviewStatistic.toProductReviewsUiState()
            )
        }
    }

    private fun onGetProductReviewsError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }


    override fun onClickBack() {
        effectActionExecutor(_effect, ProductReviewsUiEffect.OnBackClickEffect)
    }

}