package org.the_chance.honeymart.domain.model

import java.util.Date


data class Reviews(
    val reviewStatistic: ProductRating,
    val reviews: List<ProductReview>
)

data class ProductRating(
    val averageRating: Double,
    val fiveStarsCount: Int,
    val fourStarsCount: Int,
    val oneStarCount: Int,
    val reviewsCount: Int,
    val threeStarsCount: Int,
    val twoStarsCount: Int
)

data class ProductReview(
    val reviewId: Long,
    val content: String,
    val rating: Int,
    val reviewDate: Date,
    val user: UserReview
) {
    data class UserReview(
        val userId: Long?,
        val fullName: String?,
        val email: String?,
        val profileImage: String?
    )
}