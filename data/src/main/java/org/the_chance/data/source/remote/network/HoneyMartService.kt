package org.the_chance.data.source.remote.network

import org.the_chance.data.source.remote.models.BaseResponse
import org.the_chance.data.source.remote.models.MarketDto
import org.the_chance.data.source.remote.models.MarketWithCategoriesDto
import org.the_chance.data.source.remote.models.ProductDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HoneyMartService {

    @GET("/markets")
    suspend fun getMarkets(): Response<BaseResponse<List<MarketDto>>>

    @POST("/markets")
    suspend fun addMarket(@Body marketName: String)

    @DELETE("/markets/{id}")
    suspend fun deleteMarket(@Path("id") marketId: Long)

    @PUT("/markets/{id}")
    suspend fun updateMarket(@Path("id") marketId: Long)

    @GET("/markets/{id}/categories")
    suspend fun getMarketCategories(@Path("id") marketId: Long): Response<BaseResponse<MarketWithCategoriesDto>>

    @GET("/marketsWithCategories")
    suspend fun getAllMarketsWithTheirCategories(): Response<BaseResponse<List<MarketWithCategoriesDto>>>

    @POST("/category")
    suspend fun addCategory(@Body marketID: Long, @Body name: String)

    @DELETE("/category/{id}")
    suspend fun deleteCategory(@Path("id") id: Long)

    @PUT("/category")
    suspend fun updateCategory(@Body id: Long, @Body name: String)

    @GET("/Products")
    suspend fun getProducts(): Response<BaseResponse<List<ProductDto>>>

    @POST("/Product")
    suspend fun addProduct(
        @Body name: String,
        @Body quantity: String,
        @Body price: Double
    )

    @PUT("/Product/{id}")
    suspend fun updateProduct(
        @Path("id") productId: Long,
        @Body name: String,
        @Body quantity: String,
        @Body price: Double
    )

    @DELETE("/Product/{id}")
    suspend fun deleteProduct(@Path("id") productId: Long)


}