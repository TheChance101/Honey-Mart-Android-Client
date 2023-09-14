package org.the_chance.honeymart.ui.feature.productreview

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetAllProductReviewsPagingUseCase
import org.the_chance.honeymart.domain.usecase.GetProductReviewsStatistics
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.product_details.ProductDetailsArgs
import javax.inject.Inject

@HiltViewModel
class ProductReviewsViewModel @Inject constructor(
    private val productReviewsStatistics: GetProductReviewsStatistics,
    private val productReviewsPagingUseCase: GetAllProductReviewsPagingUseCase,
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
        tryToExecutePaging(
            { productReviewsStatistics(productId) },
            ::onGetProductReviewsSuccess,
            ::onGetProductReviewsError
        )
    }

//
//    private fun onGetProductReviewsSuccess(productId: ProductReviewsUiState) {
//        val mappedProductReviews = productId.map { it }
//        _state.update {
//            it.copy(
//                isLoading = false,
//                reviews = flowOf(mappedProductReviews)
//            )
//        }
//    }
//
//    private fun onGetProductReviewsError(error: ErrorHandler) {
//        _state.update { it.copy(isLoading = false) }
//        if (error is ErrorHandler.NoConnection) {
//            _state.update { it.copy(isLoading = false, isError = true) }
//        }
//    }

    override fun onClickBack() {
        effectActionExecutor(_effect, ProductReviewsUiEffect.OnBackClickEffect)
    }

}