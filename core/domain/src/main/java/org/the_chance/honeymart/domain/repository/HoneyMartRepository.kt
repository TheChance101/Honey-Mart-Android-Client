package org.the_chance.honeymart.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.the_chance.honeymart.domain.model.Cart
import org.the_chance.honeymart.domain.model.Category
import org.the_chance.honeymart.domain.model.Coupon
import org.the_chance.honeymart.domain.model.Market
import org.the_chance.honeymart.domain.model.MarketApproval
import org.the_chance.honeymart.domain.model.MarketDetails
import org.the_chance.honeymart.domain.model.MarketInfo
import org.the_chance.honeymart.domain.model.MarketRequest
import org.the_chance.honeymart.domain.model.Notification
import org.the_chance.honeymart.domain.model.Order
import org.the_chance.honeymart.domain.model.OrderDetails
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.model.RecentProduct
import org.the_chance.honeymart.domain.model.Reviews
import org.the_chance.honeymart.domain.model.UserProfile
import org.the_chance.honeymart.domain.model.WishList


interface HoneyMartRepository {

    suspend fun checkAdminApprove(): MarketApproval
    suspend fun getAllMarkets(): List<Market>?
    suspend fun getAllMarketsPaging(page: Int?): Flow<PagingData<Market>>
    suspend fun clipCoupon(couponId: Long): Boolean
    suspend fun getMarketDetails(marketId: Long): MarketDetails
    suspend fun addMarket(
        marketName: String,
        marketAddress: String,
        marketDescription: String,
    ): Boolean

    suspend fun addMarketImage(marketImage: ByteArray): Boolean

    suspend fun getCategoriesInMarket(marketId: Long): List<Category>?
    suspend fun getMarketInfo(): MarketInfo
    suspend fun updateMarketStatus(status: Int): Boolean
    suspend fun getAllProductsByCategory(page: Int?, categoryId: Long): Flow<PagingData<Product>>
    suspend fun getCategoriesForSpecificProduct(productId: Long): List<Category>?
    suspend fun addToWishList(productId: Long): String
    suspend fun deleteFromWishList(productId: Long): String
    suspend fun getWishList(): List<WishList>
    suspend fun getCart(): Cart

    suspend fun addToCart(productId: Long, count: Int): String
    suspend fun deleteFromCart(productId: Long): String
    suspend fun getOrderDetails(orderId: Long): OrderDetails

    suspend fun searchForProducts(
        query: String,
        page: Int?,
        sortOrder: String
    ): Flow<PagingData<Product>>

    suspend fun getAllOrders(orderState: Int): List<Order>

    suspend fun getAllMarketOrders(orderState: Int): List<Market.Order>
    suspend fun updateOrderState(id: Long?, state: Int): Boolean
    suspend fun checkout(): String

    suspend fun getProductDetails(productId: Long): Product

    suspend fun deleteAllCart(): String

    suspend fun getUserCoupons(): List<Coupon>

    suspend fun getAllValidCoupons(): List<Coupon>
    suspend fun getClippedUserCoupons(): List<Coupon>


    suspend fun getRecentProducts(): List<RecentProduct>

    suspend fun getAllProducts(): List<Product>

    suspend fun getAllNotifications(notificationsState: Int): List<Notification>

    suspend fun getProfileUser(): UserProfile

    suspend fun addProfileImage(image: ByteArray): String

    suspend fun addProduct(
        name: String,
        price: Double,
        description: String,
        categoryId: Long,
    ): Product

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

    suspend fun getNoCouponMarketProducts(): List<Product>
    suspend fun searchNoCouponMarketProducts(query: String): List<Product>
    suspend fun addCoupon(
        productId: Long,
        count: Int,
        discountPercentage: Double,
        expirationDate: String,
    ): Boolean

    //region admin
    suspend fun getMarketsRequests(isApproved: Boolean?): List<MarketRequest>
    suspend fun updateMarketRequest(id: Long?, isApproved: Boolean): Boolean
//endregion admin

    //region rating

    suspend fun getAllProductReviews(productId: Long): Reviews

    suspend fun getReviewsForProduct(
        page: Int?,
        productId: Long
    ): Reviews

    //end region rating
}