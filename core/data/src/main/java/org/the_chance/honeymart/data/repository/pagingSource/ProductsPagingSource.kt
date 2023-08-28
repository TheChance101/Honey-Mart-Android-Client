package org.the_chance.honeymart.data.repository.pagingSource

import org.the_chance.honeymart.data.source.remote.mapper.toProduct
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.Product
import javax.inject.Inject

class ProductsPagingSource @Inject constructor(
    honeyMartService: HoneyMartService,
    val id: Long,
) : BasePagingSource<Product>(honeyMartService) {
    override suspend fun fetchData(page: Int): List<Product> {
        return wrap { honeyMartService.getAllProductsByCategory(page = page, categoryId = id) }.value?.map { it.toProduct() }
            ?: throw Throwable("paging error")
    }

}
