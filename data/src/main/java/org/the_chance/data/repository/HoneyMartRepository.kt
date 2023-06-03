package org.the_chance.data.repository

import org.the_chance.data.source.remote.models.CategoryDto
import org.the_chance.data.source.remote.models.MarketDto
import org.the_chance.data.source.remote.models.MarketWithCategoriesDto

interface HoneyMartRepository {

    suspend fun getAllMarket(): List<MarketDto>?
    suspend fun getAllCategories(id: Long): MarketWithCategoriesDto

}