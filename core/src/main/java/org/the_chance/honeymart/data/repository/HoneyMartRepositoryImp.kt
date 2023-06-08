package org.the_chance.honeymart.data.repository

import org.the_chance.honeymart.data.source.remote.models.CategoryDto
import org.the_chance.honeymart.data.source.remote.models.MarketDto
import org.the_chance.honeymart.data.source.remote.models.ProductDto
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import javax.inject.Inject

class HoneyMartRepositoryImp @Inject constructor(
    private val honeyMartService: HoneyMartService
) : HoneyMartRepository {
    override suspend fun getAllMarkets(): List<MarketDto>? {
        return honeyMartService.getAllMarkets().body()?.value
    }

    override suspend fun getAllProducts(): List<ProductDto>? {
        return honeyMartService.getAllProducts().body()?.value
    }

    override suspend fun getAllCategoriesByMarketId(id: Long): List<CategoryDto>? {
        return honeyMartService.getAllCategoriesByMarketId(id).body()?.value
    }

    override suspend fun getAllProductsByCategoryId(id: Long): List<ProductDto>?{
        return honeyMartService.getAllProductsByCategoryId(id).body()?.value
    }


}