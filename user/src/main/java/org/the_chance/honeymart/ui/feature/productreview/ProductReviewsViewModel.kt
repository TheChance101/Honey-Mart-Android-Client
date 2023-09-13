package org.the_chance.honeymart.ui.feature.productreview

import androidx.lifecycle.SavedStateHandle
import org.the_chance.honeymart.domain.usecase.GetAllProductReviewsPagingUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.product_details.ProductDetailsArgs
import javax.inject.Inject

class ProductReviewsViewModel @Inject constructor(
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
//        getProductReviews(args.productId.toLong())
    }

//    private fun getProductReviews(productId: Long) {
//
//    }

    override fun onClickBack() {
        effectActionExecutor(_effect, ProductReviewsUiEffect.OnBackClickEffect)
    }

}