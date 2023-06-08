package org.the_chance.honeymart.data.repository

import org.the_chance.honeymart.data.source.remote.models.CategoryDto
import org.the_chance.honeymart.data.source.remote.models.MarketDto
import org.the_chance.honeymart.data.source.remote.models.ProductDto

interface HoneyMartRepository {

    suspend fun getAllMarkets(): List<MarketDto>?
    suspend fun getAllProducts(): List<ProductDto>?
    suspend fun getAllCategoriesByMarketId(id: Long): List<CategoryDto>?
    suspend fun getAllProductsByCategoryId(id:Long):List<ProductDto>?
}