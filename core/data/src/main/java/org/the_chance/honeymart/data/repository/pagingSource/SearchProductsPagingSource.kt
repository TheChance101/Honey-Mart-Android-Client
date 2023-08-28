package org.the_chance.honeymart.data.repository.pagingSource

import org.the_chance.honeymart.data.source.remote.mapper.toProduct
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.Product
import javax.inject.Inject

class SearchProductsPagingSource @Inject constructor(
    honeyMartService: HoneyMartService,
    private val query: String,
    private val sortOrder: String,
) : BasePagingSource<Product>(honeyMartService) {
    override suspend fun fetchData(page: Int): List<Product> {
        return wrap { honeyMartService.searchForProducts(page = page, query = query, sortOrder = sortOrder) }.value?.map { it.toProduct() }
            ?: throw Throwable("paging error")
    }

}
