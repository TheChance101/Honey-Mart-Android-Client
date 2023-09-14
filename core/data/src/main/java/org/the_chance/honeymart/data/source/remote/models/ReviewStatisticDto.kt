package org.the_chance.honeymart.data.source.remote.models


import com.google.gson.annotations.SerializedName

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