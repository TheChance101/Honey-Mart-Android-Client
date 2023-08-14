package org.the_chance.honeymart.data.repository

import android.util.Log
import org.the_chance.honeymart.data.source.remote.mapper.toCartEntity
import org.the_chance.honeymart.data.source.remote.mapper.toCategoryEntity
import org.the_chance.honeymart.data.source.remote.mapper.toMarketEntity
import org.the_chance.honeymart.data.source.remote.mapper.toOrderDetailsEntity
import org.the_chance.honeymart.data.source.remote.mapper.toOrderEntity
import org.the_chance.honeymart.data.source.remote.mapper.toProductEntity
import org.the_chance.honeymart.data.source.remote.mapper.toWishListEntity
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
import org.the_chance.honeymart.domain.model.CartEntity
import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.model.MarketEntity
import org.the_chance.honeymart.domain.model.OrderDetailsEntity
import org.the_chance.honeymart.domain.model.OrderEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.model.WishListEntity
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import org.the_chance.honeymart.domain.util.NotFoundException
import javax.inject.Inject


class HoneyMartRepositoryImp @Inject constructor(
    private val honeyMartService: HoneyMartService,
) : BaseRepository(), HoneyMartRepository {

    override suspend fun checkout(): String {
        return wrap { honeyMartService.checkout() }.value ?: throw NotFoundException()
    }

    override suspend fun getAllMarkets(): List<MarketEntity> {
        Log.e("Service", "getAllMarkets${honeyMartService.getAllMarkets()}")
        return wrap { honeyMartService.getAllMarkets() }.value?.map { it.toMarketEntity() }
            ?: throw NotFoundException()
    }


    override suspend fun getCart(): CartEntity =
        wrap { honeyMartService.getCart() }.value?.toCartEntity() ?: throw NotFoundException()

    override suspend fun addToCart(productId: Long, count: Int): String {
        return wrap { honeyMartService.addToCart(productId, count) }.value
            ?: throw NotFoundException()
    }

    override suspend fun deleteFromCart(productId: Long): String {
        return wrap { honeyMartService.deleteFromCart(productId) }.value
            ?: throw NotFoundException()
    }


    override suspend fun getCategoriesInMarket(marketId: Long): List<CategoryEntity> =
        wrap { honeyMartService.getCategoriesInMarket(marketId) }.value?.map { it.toCategoryEntity() }
            ?: throw NotFoundException()

    override suspend fun getAllProductsByCategory(categoryId: Long): List<ProductEntity> =
        wrap { honeyMartService.getAllProductsByCategory(categoryId) }.value?.map { it.toProductEntity() }
            ?: throw NotFoundException()

    override suspend fun getCategoriesForSpecificProduct(productId: Long): List<CategoryEntity> =
        wrap { honeyMartService.getCategoriesForSpecificProduct(productId) }.value?.map { it.toCategoryEntity() }
            ?: throw NotFoundException()

    override suspend fun getWishList(): List<WishListEntity> =
        wrap { honeyMartService.getWishList() }.value?.map { it.toWishListEntity() }
            ?: throw NotFoundException()

    override suspend fun addToWishList(productId: Long): String =
        wrap { honeyMartService.addToWishList(productId) }.value ?: throw NotFoundException()

    override suspend fun deleteFromWishList(productId: Long): String =
        wrap { honeyMartService.deleteFromWishList(productId) }.value ?: throw NotFoundException()

    override suspend fun getAllOrders(orderState: Int): List<OrderEntity> =
        wrap { honeyMartService.getAllOrders(orderState) }.value?.map { it.toOrderEntity() }
            ?: throw NotFoundException()

    override suspend fun getOrderDetails(orderId: Long): OrderDetailsEntity =
        wrap { honeyMartService.getOrderDetails(orderId) }.value?.toOrderDetailsEntity()
            ?: throw NotFoundException()

    override suspend fun updateOrderState(id: Long?, state: Int): Boolean =
        wrap { honeyMartService.updateOrderState(id, state) }.value ?: throw NotFoundException()


    override suspend fun getProductDetails(productId: Long): ProductEntity =
        wrap { honeyMartService.getProductDetails(productId) }.value?.toProductEntity()
            ?: throw NotFoundException()

    override suspend fun deleteAllCart(): String =
        wrap { honeyMartService.deleteAllFromCart() }.value ?: throw NotFoundException()

    override suspend fun addCategory(name: String, imageId: Int): String =
        wrap { honeyMartService.addCategory(name, imageId) }.value ?: throw NotFoundException()

    override suspend fun addProduct(
        name: String,
        price: Double,
        description: String,
        categoryId: Long
    ): ProductEntity {
        return wrap {
            honeyMartService.addProduct(
                name = name,
                price = price,
                description = description,
                categoriesId = categoryId
            )
        }.value?.toProductEntity() ?: throw NotFoundException()
    }

    override suspend fun addImageProduct(
        productId: Long,
        images: List<ByteArray>
    ): String {
        return wrap {
            honeyMartService.addImageProduct(
                productId = productId,
                images = images
            )
        }.value ?: throw NotFoundException()
    }
}