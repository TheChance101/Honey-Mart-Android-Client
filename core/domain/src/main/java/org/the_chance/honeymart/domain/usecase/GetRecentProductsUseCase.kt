package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class GetRecentProductsUseCase @Inject constructor(
    private val repo: HoneyMartRepository
) {
    suspend operator fun invoke() = repo.getRecentProducts()
}