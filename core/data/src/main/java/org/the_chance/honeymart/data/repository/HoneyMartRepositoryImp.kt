package org.the_chance.honeymart.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.the_chance.honeymart.data.repository.pagingSource.ProductsPagingSource
import org.the_chance.honeymart.data.source.remote.mapper.RecentProductEntity
import kotlinx.coroutines.flow.Flow
import org.the_chance.honeymart.data.source.local.AppDataStorePreferences
import org.the_chance.honeymart.data.source.local.AuthDataStorePreferences
import org.the_chance.honeymart.data.source.remote.mapper.toCartEntity
import org.the_chance.honeymart.data.source.remote.mapper.toCategoryEntity
import org.the_chance.honeymart.data.source.remote.mapper.toCouponEntity
import org.the_chance.honeymart.data.source.remote.mapper.toMarketDetailsEntity
import org.the_chance.honeymart.data.source.remote.mapper.toMarketEntity
import org.the_chance.honeymart.data.source.remote.mapper.toOrderDetailsEntity
import org.the_chance.honeymart.data.source.remote.mapper.toOrderEntity
import org.the_chance.honeymart.data.source.remote.mapper.toProductEntity
import org.the_chance.honeymart.data.source.remote.mapper.toProfileUserEntity
import org.the_chance.honeymart.data.source.remote.mapper.toWishListEntity
import org.the_chance.honeymart.data.source.remote.network.HoneyMartService
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
import org.the_chance.honeymart.domain.repository.HoneyMartRepository
import org.the_chance.honeymart.domain.util.NotFoundException
import javax.inject.Inject


class HoneyMartRepositoryImp @Inject constructor(
    private val honeyMartService: HoneyMartService,
    private val datastore: AppDataStorePreferences,
) : BaseRepository(), HoneyMartRepository {

    override suspend fun checkout(): String {
        return wrap { honeyMartService.checkout() }.value ?: throw NotFoundException()
    }

    override suspend fun getAllMarkets(): List<MarketEntity> {
        Log.e("Service", "getAllMarkets${honeyMartService.getAllMarkets()}")
        return wrap { honeyMartService.getAllMarkets() }.value?.map { it.toMarketEntity() }
            ?: throw NotFoundException()
    }

    override suspend fun clipCoupon(couponId: Long): Boolean {
        return wrap { honeyMartService.clipCoupon(couponId) }.value ?: throw NotFoundException()
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

    override suspend fun getMarketDetails(marketId: Long): MarketDetailsEntity =
        wrap { honeyMartService.getMarketDetails(marketId) }.value?.toMarketDetailsEntity()
            ?: throw NotFoundException()

    override suspend fun getAllProductsByCategory(page:Int?,categoryId: Long): Flow<PagingData<ProductEntity>> =
        getAllWithId(
            categoryId,
            ::ProductsPagingSource
        )

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

    override suspend fun searchForProducts(query: String): List<ProductEntity> =
        wrap { honeyMartService.searchForProducts(query = query) }.value?.map { it.toProductEntity() }
            ?: throw NotFoundException()

    override suspend fun updateOrderState(id: Long?, state: Int): Boolean =
        wrap { honeyMartService.updateOrderState(id, state) }.value ?: throw NotFoundException()


    override suspend fun getProductDetails(productId: Long): ProductEntity =
        wrap { honeyMartService.getProductDetails(productId) }.value?.toProductEntity()
            ?: throw NotFoundException()

    override suspend fun deleteAllCart(): String =
        wrap { honeyMartService.deleteAllFromCart() }.value ?: throw NotFoundException()

    override suspend fun getUserCoupons(): List<CouponEntity> {
        return wrap { honeyMartService.getUserCoupons() }.value?.map { it.toCouponEntity() }
            ?: throw NotFoundException()
    }

    override suspend fun getAllValidCoupons(): List<CouponEntity> {
        return wrap { honeyMartService.getAllValidCoupons() }.value?.map { it.toCouponEntity() }
            ?: throw NotFoundException()
    }

    override suspend fun getRecentProducts(): List<RecentProductEntity> {
        return wrap { honeyMartService.getRecentProducts() }.value?.map { it.RecentProductEntity() }
            ?: throw NotFoundException()
    }

    override suspend fun getAllProducts(): List<ProductEntity> {
        return wrap { honeyMartService.getAllProducts() }.value?.map { it.toProductEntity() }
            ?: throw NotFoundException()
    }

    private fun <I : Any> getAllWithId(
        id: Long,
        sourceFactory: (HoneyMartService, Long) -> PagingSource<Int, I>,
    ): Flow<PagingData<I>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = { sourceFactory(honeyMartService, id) }
        ).flow
    }

    override suspend fun getProfileUser(): ProfileUserEntity =
        wrap { honeyMartService.getProfileUser() }.value?.toProfileUserEntity()
            ?: throw NotFoundException()

    override suspend fun saveThemeState(isDark: Boolean) {
        datastore.saveThemeState(isDark)
    }

    override suspend fun getThemeState(): Boolean {
        return datastore.getThemeState()
    }

    override suspend fun addProfileImage(image: ByteArray): String {
        return wrap {
            honeyMartService.addProfileImage(
                image = image,
            )
        }.value ?: throw NotFoundException()
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }
}