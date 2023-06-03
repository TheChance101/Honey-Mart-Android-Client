package org.the_chance.data.repository

import org.the_chance.data.source.remote.models.MarketDto
import org.the_chance.data.source.remote.models.ProductDto

interface HoneyMartRepository {

    suspend fun getAllMarket(): List<MarketDto>?

    suspend fun getAllProducts(): List<ProductDto>?

}