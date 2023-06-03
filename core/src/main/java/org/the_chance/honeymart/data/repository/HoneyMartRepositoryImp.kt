package org.the_chance.honeymart.data.repository

import org.the_chance.honeymart.data.source.remote.models.MarketDto
import org.the_chance.honeymart.data.source.remote.models.MarketWithCategoriesDto
import org.the_chance.honeymart.data.source.remote.models.ProductDto
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import javax.inject.Inject

 class HoneyMartRepositoryImp @Inject constructor (
    private val honeyMartService: HoneyMartService
) : HoneyMartRepository {
    override suspend fun getAllMarket(): List<MarketDto>? {
        return honeyMartService.getMarkets().body()?.value
    }

    override suspend fun getAllProducts(): List<ProductDto>? {
        return honeyMartService.getProducts().body()?.value
    }

    override suspend fun getAllCategories(id: Long): MarketWithCategoriesDto {
        return honeyMartService.getMarketCategories(id).body()?.value!!
    }

}