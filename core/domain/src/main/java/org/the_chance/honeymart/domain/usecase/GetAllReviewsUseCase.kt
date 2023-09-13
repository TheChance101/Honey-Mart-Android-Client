package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.Review
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetAllReviewsUseCase @Inject constructor(
    private val honeyMartRepository: HoneyMartRepository,
) {
    suspend operator fun invoke(): List<Review> {
        return honeyMartRepository.getAllReviews() ?: emptyList()
    }
}