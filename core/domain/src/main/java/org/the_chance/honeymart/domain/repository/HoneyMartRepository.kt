package org.the_chance.honeymart.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.the_chance.honeymart.domain.model.CartEntity
import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.model.CouponEntity
import org.the_chance.honeymart.domain.model.MarketDetailsEntity
import org.the_chance.honeymart.domain.model.MarketEntity
import org.the_chance.honeymart.domain.model.OrderDetailsEntity
import org.the_chance.honeymart.domain.model.OrderEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.model.ProfileUserEntity
import org.the_chance.honeymart.domain.model.RecentProductEntity
import org.the_chance.honeymart.domain.model.WishListEntity


interface HoneyMartRepository {

    suspend fun getAllMarkets(): List<MarketEntity>?
    suspend fun clipCoupon(couponId: Long): Boolean
    suspend fun getMarketDetails(marketId: Long): MarketDetailsEntity
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

    suspend fun searchForProducts(query: String): List<ProductEntity>
    suspend fun getAllOrders(orderState: Int): List<OrderEntity>
    suspend fun updateOrderState(id: Long?, state: Int): Boolean
    suspend fun checkout(): String

    suspend fun getProductDetails(productId: Long): ProductEntity

    suspend fun deleteAllCart(): String

    suspend fun getUserCoupons(): List<CouponEntity>

    suspend fun getAllValidCoupons(): List<CouponEntity>

    suspend fun getRecentProducts(): List<RecentProductEntity>

    suspend fun getAllProducts(): List<ProductEntity>

    suspend fun getProfileUser(): ProfileUserEntity
    suspend fun saveThemeState(isDark: Boolean)
     fun getThemeState(): Boolean

    suspend fun addProfileImage(image: ByteArray): String
}