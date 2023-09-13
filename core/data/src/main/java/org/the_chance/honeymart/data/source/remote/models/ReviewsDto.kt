package org.the_chance.honeymart.data.source.remote.models


import com.google.gson.annotations.SerializedName

data class ReviewsDto(
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