package org.the_chance.honeymart.ui.feature.productreview

import org.the_chance.honeymart.domain.model.ProductReviewStatistic
import org.the_chance.honeymart.domain.util.ErrorHandler

data class ProductReviewsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val reviews: ProductReviewUiState = ProductReviewUiState(),
)

data class ProductReviewUiState(
    val averageRating: Double = 0.0,
    val reviewCount: Int = 0,
    val oneStarCount: Int = 0,
    val twoStarsCount: Int = 0,
    val threeStarsCount: Int = 0,
    val fourStarsCount: Int = 0,
    val fiveStarsCount: Int = 0,
)


fun ProductReviewStatistic.toProductReviewsUiState(): ProductReviewUiState {
    return ProductReviewUiState(
        averageRating = averageRating,
        reviewCount = reviewsCount,
        oneStarCount = oneStarCount,
        twoStarsCount = twoStarsCount,
        threeStarsCount = threeStarsCount,
        fourStarsCount = fourStarsCount,
        fiveStarsCount = fiveStarsCount,
    )
}