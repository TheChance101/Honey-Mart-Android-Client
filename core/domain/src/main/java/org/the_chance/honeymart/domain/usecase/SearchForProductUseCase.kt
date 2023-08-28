package org.the_chance.honeymart.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import javax.inject.Inject

class SearchForProductUseCase @Inject constructor(
    private val repository: HoneyMartRepository
) {
    suspend operator fun invoke(query: String,sortOrder:String): Flow<PagingData<Product>> =
        repository.searchForProducts(query, page = null,sortOrder)
}