package org.the_chance.honeymart.domain.usecase

import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class SearchForProductUseCase @Inject constructor(
    private val repository: HoneyMartRepository
) {
    suspend operator fun invoke(query: String, page: Int, sortOrder: String): List<Product>? {
        return if (query.isNotEmpty()) {
            repository.searchForProducts(query, page, sortOrder)
        } else {
            emptyList()
        }
    }
}