package org.the_chance.honeymart.data.repository

import org.the_chance.honeymart.data.source.remote.models.CategoryDto
import org.the_chance.honeymart.data.source.remote.models.MarketDto
import org.the_chance.honeymart.data.source.remote.models.ProductDto

interface HoneyMartRepository {
    suspend fun getAllMarkets(): List<MarketDto>?
    suspend fun getCategoriesInMarket(marketId: Long): List<CategoryDto>?
    suspend fun getAllProductsByCategory(categoryId: Long): List<ProductDto>?
    suspend fun getCategoriesForSpecificProduct(productId: Long): List<CategoryDto>?
}