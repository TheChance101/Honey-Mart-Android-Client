package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.RatingDto
import org.the_chance.honeymart.domain.model.Rating

fun RatingDto.toRating(): Rating {
    return Rating(
        averageRating = averageRating ?:  0.0,
        fiveStarsCount = fiveStarsCount ?: 0,
        fourStarsCount = fourStarsCount ?: 0,
        threeStarsCount = threeStarsCount ?: 0,
        twoStarsCount =  twoStarsCount ?: 0,
        oneStarCount = oneStarCount ?: 0,
        reviewsCount = reviewsCount ?:0

    )
}