package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.ProductRatingDto
import org.the_chance.honeymart.data.source.remote.models.ProductReviewDto
import org.the_chance.honeymart.data.source.remote.models.ReviewsDto
import org.the_chance.honeymart.data.source.remote.util.convertTimestampToDate
import org.the_chance.honeymart.domain.model.ProductRating
import org.the_chance.honeymart.domain.model.ProductReview
import org.the_chance.honeymart.domain.model.Reviews
import java.util.Date

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

fun ProductReviewDto.toProductReview(): ProductReview {
    return ProductReview(
        reviewId = reviewId ?: 0L,
        content = content ?: "",
        rating = rating ?: 0,
        reviewDate = reviewDate?.convertTimestampToDate() ?: Date(),
        user = user?.toUserReviews() ?: ProductReview.UserReview(0L, "", "", "")
    )
}

fun ProductReviewDto.UserReviewDto.toUserReviews(): ProductReview.UserReview {
    return ProductReview.UserReview(
        userId = userId ?: 0L,
        fullName = fullName ?: "",
        email = email ?: "",
        profileImage = profileImage ?: ""
    )
}