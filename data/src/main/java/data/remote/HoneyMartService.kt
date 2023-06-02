package data.remote

import data.remote.models.BaseResponse
import data.remote.models.MarketDto
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

}