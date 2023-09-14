package org.the_chance.honeymart.ui.feature.productreview

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.Reviews
import org.the_chance.honeymart.domain.usecase.GetAllProductReviewsUseCase
import org.the_chance.honeymart.domain.usecase.GetProductRatingUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.product_details.ProductDetailsArgs
import javax.inject.Inject

@HiltViewModel
class ProductReviewsViewModel @Inject constructor(
    private val productRatingUseCase: GetProductRatingUseCase,
    private val productReviewsUseCase: GetAllProductReviewsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProductReviewsUiState, ProductReviewsUiEffect>(ProductReviewsUiState()),
    ProductReviewsInteractionsListener {

    override val TAG: String = this::class.simpleName.toString()

    private val args = ProductDetailsArgs(savedStateHandle)
//    private val page = state.value.page


    init {
        getData()
    }

    fun getData() {
        getProductReviews(args.productId.toLong())
    }

    private fun getProductReviews(productId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { productRatingUseCase(productId) },
            ::onGetProductReviewsSuccess,
            ::onGetProductReviewsError
        )
    }

    private fun onGetProductReviewsSuccess(reviews: Reviews) {
        _state.update {
            it.copy(
                isLoading = false,
                reviews = reviews.toReviews()
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

//    private fun getAllRatingForProduct() {
//        _state.update { it.copy(isLoading = true) }
//        viewModelScope.launch {
//            val reviews = productsOperations.getAllRatingForProduct(
//                productId = state.value.product.productId,
//                page = page,
//            )
//            _state.update { it.copy(reviews = reviews.toReviews()) }
//        }
//    }
//
