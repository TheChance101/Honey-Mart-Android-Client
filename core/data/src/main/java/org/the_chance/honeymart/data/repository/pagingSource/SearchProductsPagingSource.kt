package org.the_chance.honeymart.data.repository.pagingSource

import org.the_chance.honeymart.data.source.remote.mapper.toProductEntity
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.ProductEntity
import javax.inject.Inject

class SearchProductsPagingSource @Inject constructor(
    honeyMartService: HoneyMartService,
    private val query: String,
) : BasePagingSource<ProductEntity>(honeyMartService) {
    override suspend fun fetchData(page: Int): List<ProductEntity> {
        return wrap { honeyMartService.searchForProducts(page = page, query = query) }.value?.map { it.toProductEntity() }
            ?: throw Throwable("paging error")
    }

}
