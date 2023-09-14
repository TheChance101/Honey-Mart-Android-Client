package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class ReviewsDto(
    @SerializedName("reviewStatistic")
    val reviewStatistic: ReviewStatisticDto?,
    @SerializedName("reviews")
    val reviews: List<ReviewDto>?
)

data class ReviewStatisticDto(
    @SerializedName("averageRating")
    val averageRating: Double?,
    @SerializedName("reviewsCount")
    val reviewsCount: Int?,
    @SerializedName("oneStarCount")
    val oneStarCount: Int?,
    @SerializedName("twoStarsCount")
    val twoStarsCount: Int?,
    @SerializedName("threeStarsCount")
    val threeStarsCount: Int?,
    @SerializedName("fourStarsCount")
    val fourStarsCount: Int?,
    @SerializedName("fiveStarsCount")
    val fiveStarsCount: Int?,
)

data class ReviewDto(
    @SerializedName("reviewId")
    val reviewId: Long?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("reviewDate")
    val reviewDate: Long?,
    @SerializedName("user")
    val user: UserReviewDto?
) {
    data class UserReviewDto(
        @SerializedName("userId")
        val userId: Long?,
        @SerializedName("fullName")
        val fullName: String?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("profileImage")
        val profileImage: String?
    )
}