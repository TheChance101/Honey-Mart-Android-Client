package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.ReviewsDto
import org.the_chance.honeymart.domain.model.Review

fun ReviewsDto.toReview() = Review(
    averageRating = averageRating ?: 0.0,
    reviewsCount = reviewsCount ?: 0,
    oneStarCount = oneStarCount ?: 0,
    twoStarsCount = twoStarsCount ?: 0,
    threeStarsCount = threeStarsCount ?: 0,
    fourStarsCount = fourStarsCount ?: 0,
    fiveStarsCount = fiveStarsCount ?: 0,
)