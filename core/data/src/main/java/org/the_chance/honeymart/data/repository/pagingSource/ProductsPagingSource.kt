package org.the_chance.honeymart.data.repository.pagingSource

import org.the_chance.honeymart.data.source.remote.mapper.toProductEntity
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.ProductEntity
import javax.inject.Inject

class ProductsPagingSource @Inject constructor(
    honeyMartService: HoneyMartService,
    val id: Long,
) : BasePagingSource<ProductEntity>(honeyMartService) {
    override suspend fun fetchData(page: Int): List<ProductEntity> {
        return wrap { honeyMartService.getAllProductsByCategory(page = page, categoryId = id) }.value?.map { it.toProductEntity() }
            ?: throw Throwable("paging error")
    }

}
