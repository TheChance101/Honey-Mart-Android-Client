package org.the_chance.honeymart.data.repository

import android.util.Log
import org.the_chance.honeymart.data.source.remote.mapper.*
import org.the_chance.honeymart.data.source.remote.mapper.toCategoryEntity
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.*
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

    override suspend fun getWishList(): List<WishListEntity> =
        wrap { honeyMartService.getWishList() }.map { it.toWishListEntity() }

    override suspend fun addToWishList(productId: Long): String =
        wrap { honeyMartService.addToWishList(productId) }

    override suspend fun deleteFromWishList(productId: Long): String =
        wrap { honeyMartService.deleteFromWishList(productId) }


     override suspend fun getOrderDetails(orderId : Long): OrderDetailsEntity =
        wrap { honeyMartService.getOrderDetails(orderId) }.toOrderDetailsEntity()

    private suspend fun <T : Any> wrap(function: suspend () -> Response<BaseResponse<T>>): T {
        val response = function()
        return if (response.isSuccessful) {
            response.body()?.value ?: throw Throwable("Unknown error occurred")
        } else {
            Log.e("TAG", "wrap: ${response}")
            throw Throwable("Unknown error occurred")
        }
    }


}