package org.the_chance.honeymart.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class SearchForProductUseCase @Inject constructor(
    private val repository: HoneyMartRepository
) {
    suspend operator fun invoke(query: String): Flow<PagingData<ProductEntity>> =
        repository.searchForProducts(query, page = null)
}