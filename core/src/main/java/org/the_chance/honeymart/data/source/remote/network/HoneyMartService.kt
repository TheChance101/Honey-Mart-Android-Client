package org.the_chance.honeymart.data.source.remote.network

import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.models.CategoryDto
import org.the_chance.honeymart.data.source.remote.models.MarketDto
import org.the_chance.honeymart.data.source.remote.models.ProductDto
import org.the_chance.honeymart.domain.model.Category
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HoneyMartService {

    //region Market
    @GET("/markets")
    suspend fun getAllMarkets(): Response<BaseResponse<List<MarketDto>>>

    @POST("/markets")
    suspend fun addMarket(@Body marketName: String): Response<BaseResponse<MarketDto>>

    @PUT("/markets/{id}")
    suspend fun updateMarket(
        @Path("id") marketId: Long,
        @Body name: String
    ): Response<BaseResponse<MarketDto>>

    @DELETE("/markets/{id}")
    suspend fun deleteMarket(@Path("id") marketId: Long)

    //endregion Market

    //region Category
    @GET("/markets/{id}/categories")
    suspend fun getCategoriesInMarket(
        @Path("id") marketId: Long
    ): Response<BaseResponse<List<CategoryDto>>>

    @POST("/category")
    suspend fun addCategory(
        @Body marketID: Long,
        @Body name: String,
        @Body imageId: Int
    ): Response<BaseResponse<CategoryDto>>

    @PUT("/category")
    suspend fun updateCategory(
        @Body id: Long,
        @Body marketID: Long,
        @Body name: String,
        @Body imageId: Int
    )

    @DELETE("/category/{id}")
    suspend fun deleteCategory(@Path("id") id: Long)


    //endregion Category

    //region Products

    @GET("/category/{categoryId}") //naming fault in backend at the end point but, it returns list of products by category id
    suspend fun getAllProductsByCategory(
        @Path("categoryId") categoryId: Long
    ): Response<BaseResponse<List<ProductDto>>>

    @GET("/product/{productId}")
    suspend fun getCategoriesForSpecificProduct(
        @Path("productId") productId: Long
    ): Response<BaseResponse<List<CategoryDto>>>

    @POST("/product")
    suspend fun addProduct(
        @Body name: String,
        @Body price: Double,
        @Body quantity: String,
        @Body categoriesId: List<Long>,
    ): Response<BaseResponse<ProductDto>>

    @PUT("/product/{id}")
    suspend fun updateProduct(
        @Path("id") productId: Long,
        @Body name: String,
        @Body price: Double,
        @Body quantity: String,
    )

    @PUT("/product/{id}/updateCategories")
    suspend fun updateCategoriesHasProduct(
        @Path("id") productId: Long,
        @Body categoriesId: List<Long>
    )

    @DELETE("/product/{id}")
    suspend fun deleteProduct(@Path("id") productId: Long)

    //endregion Product

}