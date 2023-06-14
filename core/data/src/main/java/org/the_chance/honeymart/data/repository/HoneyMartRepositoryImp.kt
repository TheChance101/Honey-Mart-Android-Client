package org.the_chance.honeymart.data.repository

import org.the_chance.honeymart.data.source.remote.mapper.toCategoryEntity
import org.the_chance.honeymart.data.source.remote.mapper.toMarketEntity
import org.the_chance.honeymart.data.source.remote.mapper.toProductEntity
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.model.MarketEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import retrofit2.Response
import javax.inject.Inject

class HoneyMartRepositoryImp @Inject constructor(
    private val honeyMartService: HoneyMartService,
) : HoneyMartRepository {

    override suspend fun getAllMarkets(): List<MarketEntity> =
        wrap { honeyMartService.getAllMarkets() }.map { it.toMarketEntity() }

    override suspend fun getCategoriesInMarket(marketId: Long): List<CategoryEntity> =
        wrap { honeyMartService.getCategoriesInMarket(marketId) }.map { it.toCategoryEntity() }

    override suspend fun getAllProductsByCategory(categoryId: Long): List<ProductEntity> =
        wrap { honeyMartService.getAllProductsByCategory(categoryId) }.map { it.toProductEntity() }

    override suspend fun getCategoriesForSpecificProduct(productId: Long): List<CategoryEntity> =
        wrap { honeyMartService.getCategoriesForSpecificProduct(productId) }.map { it.toCategoryEntity() }

    private suspend fun <T : Any> wrap(function: suspend () -> Response<BaseResponse<T>>): T {
        val response = function()
        return if (response.isSuccessful) {
            response.body()?.value as T
        } else {
            throw Throwable("Unknown error occurred")
        }
    }


}