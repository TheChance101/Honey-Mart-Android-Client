package org.the_chance.honeymart.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.the_chance.honeymart.data.repository.pagingSource.MarketsPagingSource
import org.the_chance.honeymart.data.repository.pagingSource.ProductsPagingSource
import org.the_chance.honeymart.data.repository.pagingSource.SearchProductsPagingSource
import org.the_chance.honeymart.data.source.remote.mapper.toCart
import org.the_chance.honeymart.data.source.remote.mapper.toCategory
import org.the_chance.honeymart.data.source.remote.mapper.toCoupon
import org.the_chance.honeymart.data.source.remote.mapper.toMarket
import org.the_chance.honeymart.data.source.remote.mapper.toMarketApproval
import org.the_chance.honeymart.data.source.remote.mapper.toMarketDetails
import org.the_chance.honeymart.data.source.remote.mapper.toMarketInfo
import org.the_chance.honeymart.data.source.remote.mapper.toMarketOrder
import org.the_chance.honeymart.data.source.remote.mapper.toMarketRequest
import org.the_chance.honeymart.data.source.remote.mapper.toNotification
import org.the_chance.honeymart.data.source.remote.mapper.toOrder
import org.the_chance.honeymart.data.source.remote.mapper.toOrderDetails
import org.the_chance.honeymart.data.source.remote.mapper.toProduct
import org.the_chance.honeymart.data.source.remote.mapper.toRecentProduct
import org.the_chance.honeymart.data.source.remote.mapper.toUserProfile
import org.the_chance.honeymart.data.source.remote.mapper.toWishList
import org.the_chance.honeymart.data.source.remote.models.MarketApprovalDto
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
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
import org.the_chance.honeymart.domain.model.UserProfile
import org.the_chance.honeymart.domain.model.WishList
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import org.the_chance.honeymart.domain.util.NotFoundException
import javax.inject.Inject


class HoneyMartRepositoryImp @Inject constructor(
    private val honeyMartService: HoneyMartService,
) : BaseRepository(), HoneyMartRepository {

    override suspend fun checkout(): String {
        return wrap { honeyMartService.checkout() }.value ?: throw NotFoundException()
    }

    override suspend fun checkAdminApprove(): MarketApproval {
        return wrap { honeyMartService.checkAdminApprove() }.value?.toMarketApproval() ?: throw NotFoundException()
    }

    override suspend fun getAllMarkets(): List<Market> {
        return wrap { honeyMartService.getAllMarkets() }.value?.map { it.toMarket() }
            ?: throw NotFoundException()
    }

    override suspend fun getAllMarketsPaging(page: Int?): Flow<PagingData<Market>> {
        return getAll(::MarketsPagingSource)
    }

    override suspend fun clipCoupon(couponId: Long): Boolean {
        return wrap { honeyMartService.clipCoupon(couponId) }.value ?: throw NotFoundException()
    }

    override suspend fun addMarketImage(marketImage: ByteArray): Boolean {
        return wrap { honeyMartService.addMarketImage(marketImage = marketImage) }.value
            ?: throw NotFoundException()
    }

    override suspend fun addMarket(
        marketName: String,
        marketAddress: String,
        marketDescription: String,
    ): Boolean {
        return wrap {
            honeyMartService.addMarket(
                marketName,
                marketAddress,
                marketDescription
            )
        }.isSuccess
    }


    override suspend fun getCart(): Cart =
        wrap { honeyMartService.getCart() }.value?.toCart() ?: throw NotFoundException()

    override suspend fun addToCart(productId: Long, count: Int): String {
        return wrap { honeyMartService.addToCart(productId, count) }.value
            ?: throw NotFoundException()
    }

    override suspend fun deleteFromCart(productId: Long): String {
        return wrap { honeyMartService.deleteFromCart(productId) }.value
            ?: throw NotFoundException()
    }


    override suspend fun getCategoriesInMarket(marketId: Long): List<Category> =
        wrap { honeyMartService.getCategoriesInMarket(marketId) }.value?.map { it.toCategory() }
            ?: throw NotFoundException()

    override suspend fun getMarketInfo(): MarketInfo {
        return wrap { honeyMartService.getMarketInfo() }.value?.toMarketInfo()
            ?: throw NotFoundException()
    }

    override suspend fun updateMarketStatus(status: Int): Boolean {
        return wrap { honeyMartService.updateMarketStatus(status) }.value
            ?: throw NotFoundException()
    }

    override suspend fun getMarketDetails(marketId: Long): MarketDetails =
        wrap { honeyMartService.getMarketDetails(marketId) }.value?.toMarketDetails()
            ?: throw NotFoundException()

    override suspend fun getAllProductsByCategory(
        page: Int?,
        categoryId: Long
    ): Flow<PagingData<Product>> =
        getAllWithParameter(
            categoryId,
            ::ProductsPagingSource
        )

    override suspend fun getCategoriesForSpecificProduct(productId: Long): List<Category> =
        wrap { honeyMartService.getCategoriesForSpecificProduct(productId) }.value?.map { it.toCategory() }
            ?: throw NotFoundException()

    override suspend fun getWishList(): List<WishList> =
        wrap { honeyMartService.getWishList() }.value?.map { it.toWishList() }
            ?: throw NotFoundException()

    override suspend fun addToWishList(productId: Long): String =
        wrap { honeyMartService.addToWishList(productId) }.value ?: throw NotFoundException()

    override suspend fun deleteFromWishList(productId: Long): String =
        wrap { honeyMartService.deleteFromWishList(productId) }.value ?: throw NotFoundException()

    override suspend fun getAllOrders(orderState: Int): List<Order> =
        wrap { honeyMartService.getAllOrders(orderState) }.value?.map { it.toOrder() }
            ?: throw NotFoundException()

    override suspend fun getAllMarketOrders(orderState: Int): List<Market.Order> =
        wrap { honeyMartService.getAllMarketOrders(orderState) }.value?.map { it.toMarketOrder() }
            ?: throw NotFoundException()


    override suspend fun getOrderDetails(orderId: Long): OrderDetails =
        wrap { honeyMartService.getOrderDetails(orderId) }.value?.toOrderDetails()
            ?: throw NotFoundException()

    override suspend fun searchForProducts(
        query: String,
        page: Int?,
        sortOrder: String
    ): Flow<PagingData<Product>> =
        search(
            query,
            sortOrder,
            ::SearchProductsPagingSource,
            page
        )

    override suspend fun updateOrderState(id: Long?, state: Int): Boolean =
        wrap { honeyMartService.updateOrderState(id, state) }.value ?: throw NotFoundException()


    override suspend fun getProductDetails(productId: Long): Product =
        wrap { honeyMartService.getProductDetails(productId) }.value?.toProduct()
            ?: throw NotFoundException()

    override suspend fun deleteAllCart(): String =
        wrap { honeyMartService.deleteAllFromCart() }.value ?: throw NotFoundException()

    override suspend fun getUserCoupons(): List<Coupon> {
        return wrap { honeyMartService.getUserCoupons() }.value?.map { it.toCoupon() }
            ?: throw NotFoundException()
    }

    override suspend fun getAllValidCoupons(): List<Coupon> {
        return wrap { honeyMartService.getAllValidCoupons() }.value?.map { it.toCoupon() }
            ?: throw NotFoundException()
    }

    override suspend fun getClippedUserCoupons(): List<Coupon> {
        return wrap { honeyMartService.getClippedUserCoupons() }.value?.map { it.toCoupon() }
            ?: throw NotFoundException()
    }

    override suspend fun getRecentProducts(): List<RecentProduct> {
        return wrap { honeyMartService.getRecentProducts() }.value?.map { it.toRecentProduct() }
            ?: throw NotFoundException()
    }

    override suspend fun getAllProducts(): List<Product> {
        return wrap { honeyMartService.getAllProducts() }.value?.map { it.toProduct() }
            ?: throw NotFoundException()
    }


    override suspend fun getProfileUser(): UserProfile =
        wrap { honeyMartService.getProfileUser() }.value?.toUserProfile()
            ?: throw NotFoundException()

    override suspend fun getAllNotifications(notificationsState: Int): List<Notification> =
        wrap { honeyMartService.getAllNotifications(notificationsState) }.value?.map { it.toNotification() }
            ?: throw NotFoundException()


    override suspend fun addProfileImage(image: ByteArray): String {
        return wrap {
            honeyMartService.addProfileImage(
                image = image,
            )
        }.value ?: throw NotFoundException()
    }

    private fun <I : Any, P> getAllWithParameter(
        parameter: P,
        sourceFactory: (HoneyMartService, P) -> PagingSource<Int, I>,
    ): Flow<PagingData<I>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = { sourceFactory(honeyMartService, parameter) }
        ).flow
    }

    private fun <I : Any, P, S> search(
        parameter: P,
        sortOrder: S,
        sourceFactory: (HoneyMartService, P, S) -> PagingSource<Int, I>,
        page: Int?
    ): Flow<PagingData<I>> {
        return Pager(
            config = PagingConfig(pageSize = page ?: DEFAULT_PAGE_SIZE),
            pagingSourceFactory = { sourceFactory(honeyMartService, parameter, sortOrder) }
        ).flow
    }

    private fun <I : Any> getAll(
        sourceFactory: (HoneyMartService) -> PagingSource<Int, I>,
    )
            : Flow<PagingData<I>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = { sourceFactory(honeyMartService) }
        ).flow
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }

    override suspend fun addCategory(name: String, imageId: Int): String =
        wrap { honeyMartService.addCategory(name, imageId) }.value ?: throw NotFoundException()

    override suspend fun deleteCategory(id: Long): String {
        return wrap { honeyMartService.deleteCategory(id) }.value ?: throw NotFoundException()
    }

    override suspend fun deleteProduct(productId: Long): String {
        return wrap { honeyMartService.deleteProduct(productId) }.value ?: throw NotFoundException()
    }

    override suspend fun deleteProductImage(productId: Long): String {
        TODO("Not yet implemented")
    }

    override suspend fun addProduct(
        name: String,
        price: Double,
        description: String,
        categoryId: Long
    ): Product {
        return wrap {
            honeyMartService.addProduct(
                name = name,
                price = price,
                description = description,
                categoriesId = categoryId
            )
        }.value?.toProduct() ?: throw NotFoundException()
    }

    override suspend fun updateProduct(
        id: Long,
        name: String,
        price: Double,
        description: String
    ): String {
        return wrap {
            honeyMartService.updateProduct(
                id = id,
                name = name,
                price = price,
                description = description,
            )
        }.value ?: throw NotFoundException()
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

    override suspend fun updateImageProduct(productId: Long, images: List<ByteArray>): String {
        return wrap {
            honeyMartService.updateImageProduct(productId, images)
        }.value ?: throw NotFoundException()
    }

    override suspend fun updateCategory(
        id: Long,
        name: String,
        marketId: Long,
        imageId: Int
    ): String {
        return wrap {
            honeyMartService.updateCategory(
                id = id,
                name = name,
                marketID = marketId,
                imageId = imageId
            )
        }.value ?: throw NotFoundException()
    }

    override suspend fun getNoCouponMarketProducts(): List<Product> {
        return wrap { honeyMartService.getNoCouponMarketProducts() }.value?.map { it.toProduct() }
            ?: throw NotFoundException()
    }

    override suspend fun searchNoCouponMarketProducts(query: String): List<Product> {
        return wrap { honeyMartService.searchNoCouponMarketProducts(query) }.value?.map { it.toProduct() }
            ?: throw NotFoundException()
    }

    override suspend fun addCoupon(
        productId: Long,
        count: Int,
        discountPercentage: Double,
        expirationDate: String
    ): Boolean {
        return wrap {
            honeyMartService.addCoupon(
                productId = productId,
                count = count,
                discountPercentage = discountPercentage,
                expirationDate = expirationDate
            )
        }.value ?: throw NotFoundException()
    }

    //region admin
    //region admin
    override suspend fun getMarketsRequests(isApproved: Boolean?): List<MarketRequest> {
        return wrap { honeyMartService.getMarketsRequests(isApproved) }.value?.map { it.toMarketRequest() }
            ?: throw NotFoundException()
    }

    override suspend fun updateMarketRequest(id: Long?, isApproved: Boolean): Boolean {
        return wrap { honeyMartService.updateMarketRequest(id, isApproved) }.value
            ?: throw NotFoundException()
    }
//endregion admin
//endregion admin
}
