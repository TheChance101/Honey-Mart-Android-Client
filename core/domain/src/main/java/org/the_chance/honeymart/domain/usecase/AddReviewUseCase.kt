package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class AddReviewUseCase @Inject constructor(
    val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(
        productId: Long,
        orderId: Long,
        rating: Float,
        review: String,
    ) {
        honeyMartRepository.addReview(
            productId = productId,
            orderId = orderId,
            rating = rating.toInt(),
            review = review
        )
    }
}