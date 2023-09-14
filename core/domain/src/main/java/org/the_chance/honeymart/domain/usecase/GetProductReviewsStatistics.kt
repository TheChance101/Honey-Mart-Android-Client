package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.ProductReviewStatistic
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetProductReviewsStatistics @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository
) {
    suspend operator fun invoke(productId: Long): ProductReviewStatistic =
        honeyMartRepository.getAllProductReviews(productId)
}