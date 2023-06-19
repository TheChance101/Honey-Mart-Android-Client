package org.the_chance.honeymart.data.repository

import android.util.Log
import org.the_chance.honeymart.data.source.remote.mapper.toCartEntity
import org.the_chance.honeymart.data.source.remote.mapper.toCategoryEntity
import org.the_chance.honeymart.data.source.remote.mapper.toMarketEntity
import org.the_chance.honeymart.data.source.remote.mapper.toProductEntity
import org.the_chance.honeymart.data.source.remote.mapper.toWishListEntity
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.CartEntity
import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.model.MarketEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.model.WishListEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import org.the_chance.honeymart.domain.util.UnAuthorizedException
import retrofit2.Response
import javax.inject.Inject


class HoneyMartRepositoryImp @Inject constructor(
    private val honeyMartService: HoneyMartService,
) : HoneyMartRepository {

    override suspend fun getCart(): List<CartEntity> =
        wrap { honeyMartService.getCart() }.map { it.toCartEntity() }

    override suspend fun addToCart(productId: Long, count: Int): String {
        return wrap { honeyMartService.addToCart(productId, count) }
    }

    override suspend fun deleteFromCart(productId: Long): String {
        return wrap { honeyMartService.deleteFromCart(productId) }
    }

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


    private suspend fun <T : Any> wrap(function: suspend () -> Response<BaseResponse<T>>): T {
        val response = function()
        return if (response.isSuccessful) {
            when (response.body()?.status?.code) {
                else -> response.body()?.value!!
            }
        } else {
            when (response.code()) {
                401 -> throw UnAuthorizedException()
                else -> throw Throwable("Unknown error occurred")
            }
        }
    }

}