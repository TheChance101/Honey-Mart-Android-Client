package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName


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


