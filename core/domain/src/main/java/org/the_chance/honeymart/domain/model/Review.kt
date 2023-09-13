package org.the_chance.honeymart.domain.model

data class Review(
    val averageRating: Double,
    val reviewsCount: Int,
    val oneStarCount: Int,
    val twoStarsCount: Int,
    val threeStarsCount: Int,
    val fourStarsCount: Int,
    val fiveStarsCount: Int,
)
