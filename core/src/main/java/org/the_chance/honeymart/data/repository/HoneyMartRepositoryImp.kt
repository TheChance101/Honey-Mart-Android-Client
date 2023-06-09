package org.the_chance.honeymart.data.repository

import org.the_chance.honeymart.data.source.remote.models.CategoryDto
import org.the_chance.honeymart.data.source.remote.models.MarketDto
import org.the_chance.honeymart.data.source.remote.models.ProductDto
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import javax.inject.Inject

class HoneyMartRepositoryImp @Inject constructor(
    private val honeyMartService: HoneyMartService
) : HoneyMartRepository {

    override suspend fun getAllMarkets(): List<MarketDto>? =
        honeyMartService.getAllMarkets().body()?.value

    override suspend fun getCategoriesInMarket(marketId: Long): List<CategoryDto>? =
        honeyMartService.getCategoriesInMarket(marketId).body()?.value

    override suspend fun getAllProductsByCategory(categoryId: Long): List<ProductDto>? =
        honeyMartService.getAllProductsByCategory(categoryId).body()?.value

    override suspend fun getCategoriesForSpecificProduct(productId: Long): List<CategoryDto>? =
        honeyMartService.getCategoriesForSpecificProduct(productId).body()?.value

}