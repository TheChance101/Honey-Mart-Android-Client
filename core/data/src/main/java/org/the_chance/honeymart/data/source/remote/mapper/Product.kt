package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.ProductDto
import org.the_chance.honeymart.data.source.remote.models.ReviewDto
import org.the_chance.honeymart.data.source.remote.models.ReviewStatisticDto
import org.the_chance.honeymart.data.source.remote.util.convertTimestampToDate
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.model.Review
import org.the_chance.honeymart.domain.model.ReviewStatistic
import java.util.Date

fun ProductDto.toProduct() = Product(
    productId = id ?: 0L,
    productName =  name ?: "",
    productDescription = description ?: "",
    productPrice = price ?: 0.0,
    productImages = images ?: emptyList(),
    reviewStatistic = reviewInfo?.reviewStatistic?.toReviewStatistic() ?: ReviewStatistic(
        0.0,
        0,
        0,
        0,
        0,
        0,
        0
    ),
    reviews = reviewInfo?.reviews?.map { it.toReview() } ?: emptyList()
)

fun ReviewStatisticDto.toReviewStatistic(): ReviewStatistic {
    return ReviewStatistic(
        averageRating = averageRating ?: 0.0,
        fiveStarsCount = fiveStarsCount ?: 0,
        fourStarsCount = fourStarsCount ?: 0,
        threeStarsCount = threeStarsCount ?: 0,
        twoStarsCount = twoStarsCount ?: 0,
        oneStarCount = oneStarCount ?: 0,
        reviewsCount = reviewsCount ?: 0

    )
}

fun ReviewDto.toReview(): Review {
    return Review(
        reviewId = reviewId ?: 0L,
        content = content ?: "",
        rating = rating ?: 0,
        reviewDate = reviewDate?.convertTimestampToDate()?: Date(),
        user = user?.toUserReview() ?: Review.UserReview(0L, "", "", "")
    )
}

fun ReviewDto.UserReviewDto.toUserReview(): Review.UserReview {
    return Review.UserReview(
        userId = userId ?: 0L,
        fullName = fullName ?: "",
        email = email ?: "",
        profileImage = profileImage ?: ""
    )
}