package org.the_chance.honeymart.data.source.remote.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.http.Parameters
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.models.CartDto
import org.the_chance.honeymart.data.source.remote.models.CategoryDto
import org.the_chance.honeymart.data.source.remote.models.MarketDto
import org.the_chance.honeymart.data.source.remote.models.OrderDetailsDto
import org.the_chance.honeymart.data.source.remote.models.OrderDto
import org.the_chance.honeymart.data.source.remote.models.ProductDto
import org.the_chance.honeymart.data.source.remote.models.WishListDto
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 7/2/2023.
 */
class HoneyMartServiceImp @Inject constructor(private val client: HttpClient) :
    KtorHoneyMartService {
    override suspend fun getAllMarkets(): BaseResponse<List<MarketDto>> =
        client.get("/markets").body()

    override suspend fun addMarket(marketName: String): BaseResponse<MarketDto> =
        client.post("/markets") {
            //body = marketName
            parameter("marketName", marketName)
        }.body()

    override suspend fun updateMarket(marketId: Long, name: String): BaseResponse<MarketDto> =
        client.put("/markets/$marketId") {
            parameter("name", name)
        }.body()

    override suspend fun deleteMarket(marketId: Long): BaseResponse<String> =
        client.delete("/markets/$marketId").body()

    override suspend fun getCategoriesInMarket(marketId: Long): BaseResponse<List<CategoryDto>> =
        client.get("/markets/{id}/categories").body()

    override suspend fun addCategory(
        marketID: Long,
        name: String,
        imageId: Int
    ): BaseResponse<CategoryDto> = client.post("/category") {
        parameter("marketID", marketID)
        parameter("name", name)
        parameter("imageId", imageId)
    }.body()

    override suspend fun updateCategory(
        id: Long,
        marketID: Long,
        name: String,
        imageId: Int
    ): BaseResponse<CategoryDto> = client.put("/category") {
        parameter("marketID", marketID)
        parameter("id", id)
        parameter("name", name)
        parameter("imageId", imageId)
    }.body()

    override suspend fun deleteCategory(id: Long): BaseResponse<String> =
        client.delete("/category/{id}").body()

    override suspend fun getAllProductsByCategory(categoryId: Long): BaseResponse<List<ProductDto>> =
        client.get("/category/{categoryId}/allProduct").body()

    override suspend fun getCategoriesForSpecificProduct(productId: Long): BaseResponse<List<CategoryDto>> =
        client.get("/product/{productId}").body()

    override suspend fun addProduct(
        name: String,
        price: Double,
        description: String,
        categoriesId: List<Long>
    ): BaseResponse<ProductDto> = client.post("/product") {
        parameter("price", price)
        parameter("name", name)
        parameter("description", description)
        parameter("categoriesId", categoriesId)
    }.body()

    override suspend fun updateProduct(
        productId: Long,
        name: String,
        price: Double,
        description: String
    ): BaseResponse<ProductDto> = client.put("/product/$productId")
    {
        parameter("price", price)
        parameter("name", name)
        parameter("description", description)
    }.body()

    override suspend fun updateCategoriesHasProduct(
        productId: Long,
        categoriesId: List<Long>
    ): BaseResponse<CategoryDto> = client.put("/product/$productId/updateCategories")
    {
        parameter("categoriesId", categoriesId)
    }.body()

    override suspend fun deleteProduct(productId: Long): BaseResponse<String> =
        client.delete("/product/$productId").body()

    override suspend fun loginUser(email: String, password: String): BaseResponse<String> =
        client.post("/product") {
            parameter("email", email)
            parameter("password", password)
        }.body()

    override suspend fun getWishList(): BaseResponse<List<WishListDto>> =
        client.get("/wishList").body()

    override suspend fun deleteFromWishList(productId: Long): BaseResponse<String> =
        client.delete("/wishList/$productId").body()

    override suspend fun addToWishList(productId: Long): BaseResponse<String> =
        client.post("/wishList") {
            parameter("productId", productId)
        }.body()

    override suspend fun addProductToCart(productId: Long, count: Long): BaseResponse<String> =
        client.submitForm(
            url = "/cart/addProduct",
            formParameters = Parameters.build {
                append("productId", productId.toString())
                append("count", count.toString())
            }
        ).body()


    override suspend fun getOrderDetails(orderId: Long): BaseResponse<OrderDetailsDto> =
        client.get("/order/$orderId").body()

    override suspend fun addUser(
        fullName: String,
        password: String,
        email: String
    ): BaseResponse<String> =
        client.submitForm(
            url = "/user/signup",
            formParameters = Parameters.build {
                append("fullName", fullName)
                append("password", password)
                append("email", email)
            }
        ).body()


    override suspend fun getAllOrders(orderState: Int): BaseResponse<List<OrderDto>> =
        client.get("order/userOrders") {
            parameter("orderState", orderState)
        }.body()

    override suspend fun updateOrderState(id: Long?, state: Int): BaseResponse<Boolean> =
        client.submitForm(
            url = "/order/$id",
            formParameters = Parameters.build {
                append("state", state.toString())
            }
        ).body()

    override suspend fun getCart(): BaseResponse<CartDto> = client.get("/cart").body()

    override suspend fun addToCart(productId: Long, count: Int): BaseResponse<String> =
        client.submitForm(
            url = "/cart/addProduct",
            formParameters = Parameters.build {
                append("productId", productId.toString())
                append("count", count.toString())
            }
        ).body()

    override suspend fun deleteFromCart(productId: Long): BaseResponse<String> =
        client.delete("/cart/$productId").body()

    override suspend fun deleteAllFromCart(): BaseResponse<String> =
        client.delete("/cart/deleteAll").body()

    override suspend fun checkout(): BaseResponse<String> = client.post("/order/checkout").body()

    override suspend fun getProductDetails(productId: Long): BaseResponse<ProductDto> =
        client.get("/product/$productId").body()
}