package org.the_chance.data.repository

import org.the_chance.data.source.remote.models.CategoryDto
import org.the_chance.data.source.remote.models.MarketDto
import org.the_chance.data.source.remote.models.MarketWithCategoriesDto
import org.the_chance.data.source.remote.network.HoneyMartService
import javax.inject.Inject

class HoneyMartRepositoryImp @Inject constructor (
    private val honeyMartService: HoneyMartService
) : HoneyMartRepository {
    override suspend fun getAllMarket(): List<MarketDto>? {
        return honeyMartService.getMarkets().body()?.value
    }

    override suspend fun getAllCategories(id: Long): MarketWithCategoriesDto {
        return honeyMartService.getMarketCategories(id).body()?.value!!
    }

}