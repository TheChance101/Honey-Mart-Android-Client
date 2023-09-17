package org.the_chance.honeymart.ui.feature.productreview

import org.the_chance.honeymart.domain.model.ProductRating
import org.the_chance.honeymart.domain.model.ProductReview
import org.the_chance.honeymart.domain.model.Reviews
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.notifications.toNotificationDateFormat

data class ProductReviewsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val page: Int = 1,
    val reviews: ReviewDetailsUiState = ReviewDetailsUiState(),
)

data class ReviewDetailsUiState(
    val reviewStatisticUiState: ProductRatingUiState = ProductRatingUiState(),
    val reviews: List<ProductReviewUiState> = listOf()
)

fun Reviews.toReviews(): ReviewDetailsUiState {
    return ReviewDetailsUiState(
        reviewStatisticUiState = reviewStatistic.toReviewStatisticUiState(),
        reviews = reviews.map { it.toProductReviewUiState() }
    )
}

data class ProductRatingUiState(
    val averageRating: Double = 0.0,
    val reviewCount: Int = 0,
    val oneStarCount: Int = 0,
    val twoStarsCount: Int = 0,
    val threeStarsCount: Int = 0,
    val fourStarsCount: Int = 0,
    val fiveStarsCount: Int = 0,
)

fun ProductRating.toReviewStatisticUiState(): ProductRatingUiState {
    return ProductRatingUiState(
        averageRating = averageRating,
        reviewCount = reviewsCount,
        oneStarCount = oneStarCount,
        twoStarsCount = twoStarsCount,
        threeStarsCount = threeStarsCount,
        fourStarsCount = fourStarsCount,
        fiveStarsCount = fiveStarsCount
    )
}

data class ProductReviewUiState(
    val reviewId: Long,
    val content: String,
    val rating: Int,
    val reviewDate: String,
    val fullName: String,
)

fun ProductReview.toProductReviewUiState(): ProductReviewUiState {
    return ProductReviewUiState(
        reviewId = reviewId,
        content = content,
        rating = rating,
        reviewDate = reviewDate.toNotificationDateFormat(),
        fullName = user.fullName
    )
}