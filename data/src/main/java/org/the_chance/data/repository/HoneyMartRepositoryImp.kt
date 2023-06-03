package org.the_chance.data.repository

import org.the_chance.data.source.remote.models.MarketDto
import org.the_chance.data.source.remote.models.ProductDto
import org.the_chance.data.source.remote.network.HoneyMartService
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

}