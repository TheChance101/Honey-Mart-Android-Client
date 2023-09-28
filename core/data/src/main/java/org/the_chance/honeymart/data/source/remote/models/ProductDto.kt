package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("images")
    val images: List<String>? = null,
    @SerializedName("reviews")
    val reviewInfo: ReviewInfoDto? = null
)

data class ReviewInfoDto(
    @SerializedName("reviewStatistic")
    val reviewStatistic: ReviewStatisticDto?,
    @SerializedName("reviews")
    val reviews: List<ReviewDto>?
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

data class ReviewStatisticDto(
    @SerializedName("averageRating")
    val averageRating: Double?,
    @SerializedName("fiveStarsCount")
    val fiveStarsCount: Int?,
    @SerializedName("fourStarsCount")
    val fourStarsCount: Int?,
    @SerializedName("oneStarCount")
    val oneStarCount: Int?,
    @SerializedName("reviewsCount")
    val reviewsCount: Int?,
    @SerializedName("threeStarsCount")
    val threeStarsCount: Int?,
    @SerializedName("twoStarsCount")
    val twoStarsCount: Int?
)
