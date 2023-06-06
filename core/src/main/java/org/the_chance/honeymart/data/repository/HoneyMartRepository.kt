package org.the_chance.honeymart.data.repository

import org.the_chance.honeymart.data.source.remote.models.CategoryDto
import org.the_chance.honeymart.data.source.remote.models.MarketDto
import org.the_chance.honeymart.data.source.remote.models.MarketWithCategoriesDto
import org.the_chance.honeymart.data.source.remote.models.ProductDto

interface HoneyMartRepository {

    suspend fun getAllMarket(): List<MarketDto>?
    suspend fun getAllCategories(id: Long): List<CategoryDto>
    suspend fun getAllProducts(): List<ProductDto>?

}