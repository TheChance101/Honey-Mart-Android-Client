package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.ProductRatingDto
import org.the_chance.honeymart.data.source.remote.models.ReviewDto
import org.the_chance.honeymart.data.source.remote.models.ReviewsDto
import org.the_chance.honeymart.domain.model.ProductRating
import org.the_chance.honeymart.domain.model.Review
import org.the_chance.honeymart.domain.model.Reviews

fun ProductRatingDto.toReviewStatistic(): ProductRating {
    return ProductRating(
        averageRating = averageRating ?: 0.0,
        fiveStarsCount = fiveStarsCount ?: 0,
        fourStarsCount = fourStarsCount ?: 0,
        threeStarsCount = threeStarsCount ?: 0,
        twoStarsCount = twoStarsCount ?: 0,
        oneStarCount = oneStarCount ?: 0,
        reviewsCount = reviewsCount ?: 0
    )
}

fun ReviewsDto.toReviews(): Reviews {
    return Reviews(
        reviewStatistic = reviewStatistic?.toReviewStatistic() ?: ProductRating(
            0.0,
            0,
            0,
            0,
            0,
            0,
            0
        ),
        reviews = reviews?.map { it.toProductReview() } ?: emptyList()
    )
}

fun ReviewDto.toProductReview(): Review {
    return Review(
        reviewId = reviewId ?: 0L,
        content = content ?: "",
        rating = rating ?: 0,
        reviewDate = reviewDate ?: 0L,
        user = user?.toUserReview() ?: Review.UserReview(0L, "", "", "")
    )
}