package org.the_chance.honeymart.domain.model

data class Product(
    val productId: Long,
    val productName: String,
    val productDescription: String,
    val productPrice: Double,
    val productImages:List<String>,
    val reviewStatistic: ReviewStatistic,
    val reviews: List<Review>
)

data class ReviewStatistic(
    val averageRating: Double,
    val fiveStarsCount: Int,
    val fourStarsCount: Int,
    val oneStarCount: Int,
    val reviewsCount: Int,
    val threeStarsCount: Int,
    val twoStarsCount: Int
)

data class Review(
    val reviewId: Long,
    val content: String,
    val rating: Int,
    val reviewDate: Long,
    val user: UserReview
) {
    data class UserReview(
        val userId: Long?,
        val fullName: String?,
        val email: String?,
        val profileImage: String?
    )
}