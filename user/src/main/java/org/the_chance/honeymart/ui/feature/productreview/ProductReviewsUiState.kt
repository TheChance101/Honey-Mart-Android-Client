package org.the_chance.honeymart.ui.feature.productreview

import org.the_chance.honeymart.domain.model.Review
import org.the_chance.honeymart.domain.model.ReviewStatistic
import org.the_chance.honeymart.domain.model.Reviews
import org.the_chance.honeymart.domain.util.ErrorHandler

data class ProductReviewsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val page: Int = 1,
    val reviews: ReviewDetailsUiState = ReviewDetailsUiState(),

//    val reviews: ProductReviewUiState = ProductReviewUiState(),
)

data class ReviewDetailsUiState(
    val reviewStatisticUiState: ProductRatingUiState = ProductRatingUiState(),
    val reviews: List<ReviewUiState> = listOf()
)

fun Reviews.toReviews(): ReviewDetailsUiState {
    return ReviewDetailsUiState(
        reviewStatisticUiState = reviewStatistic.toReviewStatisticUiState(),
        reviews = reviews.map { it.toReviewUiState() })
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

fun ReviewStatistic.toReviewStatisticUiState(): ProductRatingUiState {
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

data class ReviewUiState(
    val reviewId: Long,
    val content: String,
    val rating: Int,
    val reviewDate: Long,
    val fullName: String?,
)

fun Review.toReviewUiState(): ReviewUiState {
    return ReviewUiState(
        reviewId = reviewId,
        content = content,
        rating = rating,
        reviewDate = reviewDate,
        fullName = user.fullName
    )
}

//
//fun ProductReviewStatistic.toProductReviewsUiState(): ProductReviewUiState {
//    return ProductReviewUiState(
//        averageRating = averageRating,
//        reviewCount = reviewsCount,
//        oneStarCount = oneStarCount,
//        twoStarsCount = twoStarsCount,
//        threeStarsCount = threeStarsCount,
//        fourStarsCount = fourStarsCount,
//        fiveStarsCount = fiveStarsCount,
//    )
//}

//
//data class ReviewStatisticUiState(
//    val averageRating: Double = 0.0,
//    val fiveStarsCount: Int = 0,
//    val fourStarsCount: Int = 0,
//    val oneStarCount: Int = 0,
//    val reviewsCount: Int = 0,
//    val threeStarsCount: Int = 0,
//    val twoStarsCount: Int = 0
//)