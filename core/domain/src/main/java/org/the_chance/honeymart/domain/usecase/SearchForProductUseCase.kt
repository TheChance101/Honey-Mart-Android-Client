package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class SearchForProductUseCase @Inject constructor(
    private val repository: HoneyMartRepository
) {
    suspend operator fun invoke(query: String): List<ProductEntity> {
        return repository.searchForProducts(query)
    }
}