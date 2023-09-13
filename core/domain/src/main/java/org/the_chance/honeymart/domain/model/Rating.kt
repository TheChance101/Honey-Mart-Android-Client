package org.the_chance.honeymart.domain.model

data class Rating(
    val averageRating: Double,
    val fiveStarsCount: Int,
    val fourStarsCount: Int,
    val oneStarCount: Int,
    val reviewsCount: Int,
    val threeStarsCount: Int,
    val twoStarsCount: Int
)
