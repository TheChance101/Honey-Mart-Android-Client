package org.the_chance.honeymart.domain.repository

import org.the_chance.honeymart.domain.model.CartEntity
import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.model.MarketEntity
import org.the_chance.honeymart.domain.model.NotificationEntity
import org.the_chance.honeymart.domain.model.MarketOrderEntity
import org.the_chance.honeymart.domain.model.OrderDetailsEntity
import org.the_chance.honeymart.domain.model.OrderEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.model.ProfileUserEntity
import org.the_chance.honeymart.domain.model.RecentProductEntity
import org.the_chance.honeymart.domain.model.RequestEntity
import org.the_chance.honeymart.domain.model.WishListEntity


interface HoneyMartRepository {

    suspend fun getAllMarkets(): List<MarketEntity>?
    suspend fun clipCoupon(couponId: Long): Boolean
    suspend fun getMarketDetails(marketId: Long): MarketDetailsEntity
    suspend fun addMarket(
        marketName: String,
        marketAddress: String,
        marketDescription: String,
    ): Boolean

    suspend fun addMarketImage(marketImage: ByteArray): Boolean

    suspend fun getCategoriesInMarket(marketId: Long): List<CategoryEntity>?
    suspend fun getAllProductsByCategory(page: Int?,categoryId: Long): Flow<PagingData<ProductEntity>>
    suspend fun getCategoriesForSpecificProduct(productId: Long): List<CategoryEntity>?
    suspend fun addToWishList(productId: Long): String
    suspend fun deleteFromWishList(productId: Long): String
    suspend fun getWishList(): List<WishListEntity>
    suspend fun getCart(): CartEntity

    suspend fun addToCart(productId: Long, count: Int): String
    suspend fun deleteFromCart(productId: Long): String
    suspend fun getOrderDetails(orderId: Long): OrderDetailsEntity

    suspend fun searchForProducts(query: String,page: Int?,sortOrder:String): Flow<PagingData<ProductEntity>>
    suspend fun getAllOrders(orderState: Int): List<OrderEntity>

    suspend fun getAllMarketOrders(orderState: Int): List<MarketOrderEntity>
    suspend fun updateOrderState(id: Long?, state: Int): Boolean
    suspend fun checkout(): String

    suspend fun getProductDetails(productId: Long): ProductEntity

    suspend fun deleteAllCart(): String

    suspend fun getUserCoupons(): List<CouponEntity>

    suspend fun getAllValidCoupons(): List<CouponEntity>

    suspend fun getRecentProducts(): List<RecentProductEntity>

    suspend fun getAllProducts(): List<ProductEntity>

    suspend fun getAllNotifications(notificationsState: Int): List<NotificationEntity>

    suspend fun getProfileUser(): ProfileUserEntity



    suspend fun addProfileImage(image: ByteArray): String

    suspend fun addProduct(
        name: String,
        price: Double,
        description: String,
        categoryId: Long,
    ): ProductEntity

    suspend fun updateProduct(
        id: Long,
        name: String,
        price: Double,
        description: String
    ): String

    suspend fun addImageProduct(productId: Long, images: List<ByteArray>): String
    suspend fun updateImageProduct(productId: Long, images: List<ByteArray>): String

    suspend fun updateCategory(
        id: Long,
        name: String,
        marketId: Long,
        imageId: Int
    ): String

    suspend fun addCategory(name: String, imageId: Int): String
    suspend fun deleteCategory(id: Long): String
    suspend fun deleteProduct(productId: Long): String
    suspend fun deleteProductImage(productId: Long): String


    //region admin
    suspend fun getMarketRequests(isApproved: Boolean): List<RequestEntity>
    suspend fun updateMarketRequest(id: Long?, isApproved: Boolean):Boolean
//endregion admin
}