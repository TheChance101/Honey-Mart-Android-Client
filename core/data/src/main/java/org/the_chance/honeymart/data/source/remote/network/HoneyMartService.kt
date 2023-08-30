package org.the_chance.honeymart.data.source.remote.network

import org.the_chance.honeymart.data.source.remote.models.AdminLoginDto
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.models.CartDto
import org.the_chance.honeymart.data.source.remote.models.CategoryDto
import org.the_chance.honeymart.data.source.remote.models.CouponDto
import org.the_chance.honeymart.data.source.remote.models.MarketDetailsDto
import org.the_chance.honeymart.data.source.remote.models.MarketDto
import org.the_chance.honeymart.data.source.remote.models.MarketIdDto
import org.the_chance.honeymart.data.source.remote.models.MarketInfoDto
import org.the_chance.honeymart.data.source.remote.models.MarketOrderDto
import org.the_chance.honeymart.data.source.remote.models.MarketRequestDto
import org.the_chance.honeymart.data.source.remote.models.NotificationDto
import org.the_chance.honeymart.data.source.remote.models.OrderDetailsDto
import org.the_chance.honeymart.data.source.remote.models.OrderDto
import org.the_chance.honeymart.data.source.remote.models.OwnerLoginDto
import org.the_chance.honeymart.data.source.remote.models.OwnerProfileDto
import org.the_chance.honeymart.data.source.remote.models.ProductDto
import org.the_chance.honeymart.data.source.remote.models.ProfileUserDto
import org.the_chance.honeymart.data.source.remote.models.RecentProductDto
import org.the_chance.honeymart.data.source.remote.models.UserLoginDto
import org.the_chance.honeymart.data.source.remote.models.WishListDto


interface HoneyMartService {


    suspend fun addOwner(
        fullName: String,
        email: String,
        password: String,
    ): BaseResponse<Boolean>

    //region Market
    suspend fun getAllMarkets(): BaseResponse<List<MarketDto>>
    suspend fun getAllMarketsPaging(page: Int?): BaseResponse<List<MarketDto>>
    suspend fun addMarket(
        marketName: String,
        marketAddress: String,
        marketDescription: String,
    ): BaseResponse<MarketIdDto>

    suspend fun addMarketImage(
        marketImage: ByteArray
    ): BaseResponse<Boolean>

    suspend fun updateMarket(marketId: Long, name: String): BaseResponse<MarketDto>

    suspend fun deleteMarket(marketId: Long): BaseResponse<String>

    suspend fun getMarketDetails(marketId: Long): BaseResponse<MarketDetailsDto>

    suspend fun getMarketInfo(): BaseResponse<MarketInfoDto>

    //endregion Market

    //region Category
    suspend fun getCategoriesInMarket(marketId: Long): BaseResponse<List<CategoryDto>>

    suspend fun addCategory(name: String, imageId: Int): BaseResponse<String>

    suspend fun updateCategory(
        id: Long,
        marketID: Long,
        name: String,
        imageId: Int,
    ): BaseResponse<String>

    suspend fun deleteCategory(id: Long): BaseResponse<String>
    //endregion Category

    //region Products
    suspend fun getAllProductsByCategory(
        page: Int?,
        categoryId: Long
    ): BaseResponse<List<ProductDto>>

    suspend fun getAllProducts(): BaseResponse<List<ProductDto>>

    suspend fun getCategoriesForSpecificProduct(
        productId: Long,
    ): BaseResponse<List<CategoryDto>>


    suspend fun addProduct(
        name: String,
        price: Double,
        description: String,
        categoriesId: Long,
    ): BaseResponse<ProductDto>

    suspend fun addImageProduct(
        productId: Long,
        images: List<ByteArray>
    ): BaseResponse<String>

    suspend fun updateProduct(
        id: Long,
        name: String,
        price: Double,
        description: String,
    ): BaseResponse<String>

    suspend fun updateImageProduct(
        productId: Long,
        images: List<ByteArray>
    ): BaseResponse<String>

    suspend fun updateCategoriesHasProduct(
        productId: Long,
        categoriesId: List<Long>,
    ): BaseResponse<CategoryDto>


    suspend fun deleteProduct(productId: Long): BaseResponse<String>

    suspend fun searchForProducts(
        query: String,
        page: Int?,
        sortOrder: String
    ): BaseResponse<List<ProductDto>>


    //endregion Product

    suspend fun loginUser(
        email: String,
        password: String,
        deviceToken: String
    ): BaseResponse<UserLoginDto>

    suspend fun refreshToken(refreshToken: String): BaseResponse<UserLoginDto>

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

    suspend fun getAllMarketOrders(
        orderState: Int,
    ): BaseResponse<List<MarketOrderDto>>

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
    suspend fun getUserCoupons(): BaseResponse<List<CouponDto>>

    suspend fun getAllValidCoupons(): BaseResponse<List<CouponDto>>

    suspend fun getClippedUserCoupons(): BaseResponse<List<CouponDto>>


    suspend fun getRecentProducts(): BaseResponse<List<RecentProductDto>>

    suspend fun clipCoupon(couponId: Long): BaseResponse<Boolean>

    suspend fun getNoCouponMarketProducts(): BaseResponse<List<ProductDto>>

    suspend fun searchNoCouponMarketProducts(
        query: String,
    ): BaseResponse<List<ProductDto>>

    suspend fun addCoupon(
        productId: Long,
        count: Int,
        discountPercentage: Double,
        expirationDate: String,
    ): BaseResponse<Boolean>


    // endregion Coupon

    //region notifications

    suspend fun getAllNotifications(
        notificationState: Int,
    ): BaseResponse<List<NotificationDto>>

    //endregion notifications

    //region profile
    suspend fun getProfileUser(): BaseResponse<ProfileUserDto>

    suspend fun addProfileImage(image: ByteArray): BaseResponse<String>
    //endregion


    // region Owner


    // region Auth
    suspend fun loginOwner(email: String, password: String): BaseResponse<OwnerLoginDto>
    //endregion

    //endregion
    suspend fun getOwnerProfile(): BaseResponse<OwnerProfileDto>
    suspend fun deleteProductById(productId: Long): BaseResponse<String>
    suspend fun deleteProductImage(productId: Long): BaseResponse<String>

    //region admin
    suspend fun getMarketsRequests(isApproved: Boolean?): BaseResponse<List<MarketRequestDto>>

    suspend fun updateMarketRequest(
        id: Long?,
        isApproved: Boolean,
    ): BaseResponse<Boolean>

    suspend fun loginAdmin(email: String, password: String): BaseResponse<AdminLoginDto>
//endregion admin
}