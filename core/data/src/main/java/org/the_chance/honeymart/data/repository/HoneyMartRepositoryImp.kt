package org.the_chance.honeymart.data.repository

import org.the_chance.honeymart.data.source.remote.mapper.toCategoryEntity
import org.the_chance.honeymart.data.source.remote.mapper.toMarketEntity
import org.the_chance.honeymart.data.source.remote.mapper.toProductEntity
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.model.MarketEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import javax.inject.Inject

class HoneyMartRepositoryImp @Inject constructor(
    private val honeyMartService: HoneyMartService
) : org.the_chance.honeymart.domain.repository.HoneyMartRepository {

    override suspend fun getAllMarkets(): List<MarketEntity>? =
        honeyMartService.getAllMarkets().body()?.value?.map { it.toMarketEntity() }

    override suspend fun getCategoriesInMarket(marketId: Long): List<CategoryEntity>? =
        honeyMartService.getCategoriesInMarket(marketId)
            .body()?.value?.map { it.toCategoryEntity() }

    override suspend fun getAllProductsByCategory(categoryId: Long): List<ProductEntity>? =
        honeyMartService.getAllProductsByCategory(categoryId)
            .body()?.value?.map { it.toProductEntity() }

    override suspend fun getCategoriesForSpecificProduct(productId: Long): List<CategoryEntity>? =
        honeyMartService.getCategoriesForSpecificProduct(productId)
            .body()?.value?.map { it.toCategoryEntity() }

}