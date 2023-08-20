package org.the_chance.honeymart.data.source.remote.network

import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.models.CartDto
import org.the_chance.honeymart.data.source.remote.models.CategoryDto
import org.the_chance.honeymart.data.source.remote.models.CouponDto
import org.the_chance.honeymart.data.source.remote.models.MarketDetailsDto
import org.the_chance.honeymart.data.source.remote.models.MarketDto
import org.the_chance.honeymart.data.source.remote.models.OrderDetailsDto
import org.the_chance.honeymart.data.source.remote.models.OrderDto
import org.the_chance.honeymart.data.source.remote.models.ProductDto
import org.the_chance.honeymart.data.source.remote.models.RecentProductDto
import org.the_chance.honeymart.data.source.remote.models.UserLoginDto
import org.the_chance.honeymart.data.source.remote.models.WishListDto

/**
 * Created by Aziza Helmy on 7/2/2023.
 */
interface HoneyMartService {

    //region Market
    suspend fun getAllMarkets(): BaseResponse<List<MarketDto>>
    suspend fun addMarket(marketName: String): BaseResponse<MarketDto>

    suspend fun updateMarket(marketId: Long, name: String): BaseResponse<MarketDto>

    suspend fun deleteMarket(marketId: Long): BaseResponse<String>

    suspend fun getMarketDetails(marketId: Long): BaseResponse<MarketDetailsDto>

    //endregion Market

    //region Category
    suspend fun getCategoriesInMarket(marketId: Long): BaseResponse<List<CategoryDto>>

    suspend fun addCategory(marketID: Long, name: String, imageId: Int): BaseResponse<CategoryDto>

    suspend fun updateCategory(
        id: Long,
        marketID: Long,
        name: String,
        imageId: Int,
    ): BaseResponse<CategoryDto>

    suspend fun deleteCategory(id: Long): BaseResponse<String>
    //endregion Category

    //region Products
    suspend fun getAllProductsByCategory(categoryId: Long): BaseResponse<List<ProductDto>>

    suspend fun getAllProducts(): BaseResponse<List<ProductDto>>

    suspend fun getCategoriesForSpecificProduct(
        productId: Long,
    ): BaseResponse<List<CategoryDto>>


    suspend fun addProduct(
        name: String,
        price: Double,
        description: String,
        categoriesId: List<Long>,
    ): BaseResponse<ProductDto>


    suspend fun updateProduct(
        productId: Long,
        name: String,
        price: Double,
        description: String,
    ): BaseResponse<ProductDto>

    suspend fun updateCategoriesHasProduct(
        productId: Long,
        categoriesId: List<Long>,
    ): BaseResponse<CategoryDto>


    suspend fun deleteProduct(productId: Long): BaseResponse<String>

    suspend fun searchForProducts(query: String): BaseResponse<List<ProductDto>>


    //endregion Product

    suspend fun loginUser(
        email: String,
        password: String,
        deviceToken: String
    ): BaseResponse<UserLoginDto>
    //region WishList

    suspend fun getWishList(): BaseResponse<List<WishListDto>>


    suspend fun deleteFromWishList(productId: Long): BaseResponse<String>


    suspend fun addToWishList(productId: Long): BaseResponse<String>

    //endregion WishList

    //region Cart

    suspend fun addProductToCart(
        productId: Long,
        count: Long,
    ): BaseResponse<String>

    //endregion Cart


    //region user

    suspend fun getOrderDetails(orderId: Long): BaseResponse<OrderDetailsDto>

    suspend fun addUser(
        fullName: String,
        password: String,
        email: String,
    ): BaseResponse<String>

    //endregion user

    suspend fun getAllOrders(
        orderState: Int,
    ): BaseResponse<List<OrderDto>>


    suspend fun updateOrderState(
        id: Long?,
        state: Int,
    ): BaseResponse<Boolean>

    // region cart

    suspend fun getCart(): BaseResponse<CartDto>


    suspend fun addToCart(
        productId: Long,
        count: Int,
    ): BaseResponse<String>


    suspend fun deleteFromCart(productId: Long): BaseResponse<String>


    suspend fun deleteAllFromCart(): BaseResponse<String>


    suspend fun checkout(): BaseResponse<String>

    //endregion cart
    // region Product Details

    suspend fun getProductDetails(
        productId: Long,
    ): BaseResponse<ProductDto>
    // endregion


    // region Coupon
    suspend fun getAllUserCoupons(): BaseResponse<List<CouponDto>>

    suspend fun getAllValidCoupons(): BaseResponse<List<CouponDto>>
    suspend fun getRecentProducts(): BaseResponse<List<RecentProductDto>>

    suspend fun clipCoupon(couponId: Long): BaseResponse<Boolean>


    // endregion Coupon
}