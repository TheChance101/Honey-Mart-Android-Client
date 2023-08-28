package org.the_chance.honeymart.data.source.remote.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.util.InternalAPI
import org.the_chance.honeymart.data.source.remote.models.AdminLoginDto
import org.the_chance.honeymart.data.source.remote.models.BaseResponse
import org.the_chance.honeymart.data.source.remote.models.CartDto
import org.the_chance.honeymart.data.source.remote.models.CategoryDto
import org.the_chance.honeymart.data.source.remote.models.CouponDto
import org.the_chance.honeymart.data.source.remote.models.MarketDetailsDto
import org.the_chance.honeymart.data.source.remote.models.MarketDto
import org.the_chance.honeymart.data.source.remote.models.OrderDetailsDto
import org.the_chance.honeymart.data.source.remote.models.OrderDto
import org.the_chance.honeymart.data.source.remote.models.OwnerLoginDto
import org.the_chance.honeymart.data.source.remote.models.OwnerProfileDto
import org.the_chance.honeymart.data.source.remote.models.ProductDto
import org.the_chance.honeymart.data.source.remote.models.ProfileUserDto
import org.the_chance.honeymart.data.source.remote.models.RecentProductDto
import org.the_chance.honeymart.data.source.remote.models.RequestDto
import org.the_chance.honeymart.data.source.remote.models.UserLoginDto
import org.the_chance.honeymart.data.source.remote.models.WishListDto
import org.the_chance.honeymart.domain.util.InternalServerException
import org.the_chance.honeymart.domain.util.UnAuthorizedException
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 7/2/2023.
 */
class HoneyMartServiceImp @Inject constructor(
    private val client: HttpClient,
) : HoneyMartService {
    override suspend fun addOwner(
        fullName: String,
        email: String,
        password: String,
    ): BaseResponse<String> =
        wrap(client.submitForm(url = "/user/signup", formParameters = Parameters.build {
            append("fullName", fullName)
            append("email", email)
            append("password", password)
        }))


    override suspend fun clipCoupon(couponId: Long): BaseResponse<Boolean> {
        return wrap(client.put("/coupon/clip/$couponId"))
    }

    override suspend fun getAllMarkets(): BaseResponse<List<MarketDto>> {
        return wrap(client.get("/markets"))
    }

    override suspend fun addMarket(marketName: String): BaseResponse<MarketDto> =
        wrap(client.submitForm(url = "/markets", formParameters = Parameters.build {
            append("marketName", marketName)
        }))

    override suspend fun updateMarket(marketId: Long, name: String): BaseResponse<MarketDto> =
        wrap(client.put("/markets/$marketId") {
            parameter("name", name)
        })

    override suspend fun deleteMarket(marketId: Long): BaseResponse<String> =
        wrap(client.delete("/markets/$marketId"))

    override suspend fun getCategoriesInMarket(marketId: Long): BaseResponse<List<CategoryDto>> =
        wrap(client.get("/markets/$marketId/categories"))

    override suspend fun getMarketDetails(marketId: Long): BaseResponse<MarketDetailsDto> =
        wrap(client.get("/markets/$marketId"))

    override suspend fun addCategory(
        name: String, imageId: Int,
    ): BaseResponse<String> =
        wrap(client.submitForm(url = "/category", formParameters = Parameters.build {
            append("imageId", imageId.toString())
            append("name", name)
        }))

    @OptIn(InternalAPI::class)
    override suspend fun updateCategory(
        id: Long,
        marketID: Long,
        name: String,
        imageId: Int,
    ): BaseResponse<String> {
        val formData = Parameters.build {
            append("marketID", marketID.toString())
            append("id", id.toString())
            append("name", name)
            append("imageId", imageId.toString())
        }
        val response = wrap<BaseResponse<String>>(client.put("/category") {
            contentType(ContentType.Application.Json)
            body = FormDataContent(formData)
        })
        return response
    }

    override suspend fun deleteCategory(id: Long): BaseResponse<String> {
        return wrap(client.delete(urlString = "/category/$id"))
    }


    override suspend fun getAllProductsByCategory(page: Int?,categoryId: Long): BaseResponse<List<ProductDto>> =
        wrap(client.get("/category/$categoryId/allProduct?page=$page"))

    override suspend fun getAllProducts(): BaseResponse<List<ProductDto>> =
        wrap(client.get("/product"))

    override suspend fun getCategoriesForSpecificProduct(productId: Long): BaseResponse<List<CategoryDto>> =
        wrap(client.get("/product/$productId"))

    override suspend fun addProduct(
        name: String,
        price: Double,
        description: String,
        categoriesId: Long,
    ): BaseResponse<ProductDto> =
        wrap(client.submitForm(url = "/product", formParameters = Parameters.build {
            append("price", price.toString())
            append("categoriesId", categoriesId.toString())
            append("description", description)
            append("name", name)
        }))

    override suspend fun addImageProduct(
        productId: Long,
        images: List<ByteArray>
    ): BaseResponse<String> {
        val response: HttpResponse = client.submitFormWithBinaryData(
            url = "/product/$productId/uploadImages",
            formData = formData {
                images.forEachIndexed { index, bytes ->
                    append("images", bytes, Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(HttpHeaders.ContentDisposition, "filename=image$index.jpeg")
                    })
                }
            }
        )
        return wrap(response)
    }


    override suspend fun updateProduct(
        productId: Long,
        name: String,
        price: Double,
        description: String,
    ): BaseResponse<ProductDto> = wrap(client.put("/product/$productId") {
        parameter("price", price)
        parameter("name", name)
        parameter("description", description)
    })

    override suspend fun updateCategoriesHasProduct(
        productId: Long,
        categoriesId: List<Long>,
    ): BaseResponse<CategoryDto> = wrap(client.put("/product/$productId/updateCategories") {
        parameter("categoriesId", categoriesId)
    })

    override suspend fun deleteProduct(productId: Long): BaseResponse<String> =
        wrap(client.delete("/product/$productId"))

    override suspend fun searchForProducts(query: String): BaseResponse<List<ProductDto>> =
        wrap(client.get("product/search") {
            parameter("query", query)
        })

    override suspend fun loginUser(email: String, password: String, deviceToken:String ): BaseResponse<UserLoginDto> =
        wrap(client.submitForm(url = "/user/login", formParameters = Parameters.build {
            append("email", email)
            append("password", password)
            append("deviceToken",deviceToken)
        }))

    override suspend fun refreshToken(refreshToken: String): BaseResponse<UserLoginDto> =
        wrap(client.submitForm(url = "/token/refresh" , formParameters = Parameters.build {
            append("refreshToken" ,refreshToken)
        }) )


    override suspend fun getWishList(): BaseResponse<List<WishListDto>> =
        wrap(client.get("/wishList"))

    override suspend fun deleteFromWishList(productId: Long): BaseResponse<String> =
        wrap(client.delete("/wishList/$productId"))

    override suspend fun addToWishList(productId: Long): BaseResponse<String> =
        wrap(client.submitForm(url = "/wishList", formParameters = Parameters.build {
            append("productId", productId.toString())
        }))

    override suspend fun addProductToCart(productId: Long, count: Long): BaseResponse<String> =
        wrap(client.submitForm(url = "/cart/addProduct", formParameters = Parameters.build {
            append("productId", productId.toString())
            append("count", count.toString())
        }))


    override suspend fun getOrderDetails(orderId: Long): BaseResponse<OrderDetailsDto> =
        wrap(client.get("/order/$orderId"))

    override suspend fun addUser(
        fullName: String, password: String, email: String,
    ): BaseResponse<String> =
        wrap(client.submitForm(url = "/user/signup", formParameters = Parameters.build {
            append("fullName", fullName)
            append("password", password)
            append("email", email)
        }))


    override suspend fun getAllOrders(orderState: Int): BaseResponse<List<OrderDto>> =
        wrap(client.get("order/userOrders") {
            parameter("orderState", orderState)
        })


    @OptIn(InternalAPI::class)
    override suspend fun updateOrderState(id: Long?, state: Int): BaseResponse<Boolean> {
        val url = "/order/$id"
        val formData = Parameters.build {
            append("state", "$state")
        }
        val response = wrap<BaseResponse<Boolean>>(client.put(url) {
            contentType(ContentType.Application.Json)
            body = FormDataContent(formData)
        })
        return response
    }


    override suspend fun getCart(): BaseResponse<CartDto> = wrap(client.get("/cart"))

    override suspend fun addToCart(productId: Long, count: Int): BaseResponse<String> =
        wrap(client.submitForm(url = "/cart/addProduct", formParameters = Parameters.build {
            append("productId", productId.toString())
            append("count", count.toString())
        }))

    override suspend fun deleteFromCart(productId: Long): BaseResponse<String> =
        wrap(client.delete("/cart/$productId"))

    override suspend fun deleteAllFromCart(): BaseResponse<String> =
        wrap(client.delete("/cart/deleteAll"))

    override suspend fun checkout(): BaseResponse<String> = wrap(client.post("/order/checkout"))

    override suspend fun getProductDetails(productId: Long): BaseResponse<ProductDto> =
        wrap(client.get("/product/$productId"))

    override suspend fun getUserCoupons(): BaseResponse<List<CouponDto>> {
        return wrap(client.get("/coupon/allUserCoupons"))
    }

    override suspend fun getAllValidCoupons(): BaseResponse<List<CouponDto>> {
        return wrap(client.get("/coupon/allValidCoupons"))
    }

    override suspend fun getRecentProducts(): BaseResponse<List<RecentProductDto>> {
        return wrap(client.get("/product/recentProducts"))
    }

    override suspend fun getProfileUser(): BaseResponse<ProfileUserDto> =
        wrap(client.get("/user/myProfile"))

    override suspend fun addProfileImage(image: ByteArray)
            : BaseResponse<String> {
        val response: HttpResponse = client.submitFormWithBinaryData(
            url = "/user/profileImage",
            formData = formData {
                append("image", image, Headers.build {
                    append(HttpHeaders.ContentType, "image/jpeg")
                    append(HttpHeaders.ContentDisposition, "filename=image${image.toString()}.jpeg")
                })
            }
        )
        return wrap(response)
    }


    private suspend inline fun <reified T> wrap(response: HttpResponse): T {
        if (response.status.isSuccess()) {
            return response.body()
        } else {
            when (response.status.value) {
                401 -> throw UnAuthorizedException()
                500 -> throw InternalServerException()
                else -> throw Exception(response.status.description)
            }
        }
    }


    // region Owner
    //region Auth
    override suspend fun loginOwner(email: String, password: String): BaseResponse<OwnerLoginDto> =
        wrap(client.submitForm(url = "/owner/login", formParameters = Parameters.build {
            append("email", email)
            append("password", password)
        }))

    override suspend fun getOwnerProfile(): BaseResponse<OwnerProfileDto> {
        return wrap(client.get("/owner/Profile"))
    }
    //endregion

    //region admin
    override suspend fun getMarketRequests(isApproved: Boolean): BaseResponse<List<RequestDto>> {
        return wrap(client.get("admin/markets") {
            parameter("isApproved", "$isApproved")
        })
    }

    @OptIn(InternalAPI::class)
    override suspend fun updateMarketRequest(
        id: Long?,
        isApproved: Boolean
    ): BaseResponse<Boolean> {
        val url = "admin/request/$id"
        val formData = Parameters.build {
            append("isApproved", "$isApproved")
        }
        val response = wrap<BaseResponse<Boolean>>(client.put(url) {
            contentType(ContentType.Application.Json)
            body = FormDataContent(formData)
        })
        return response
    }

    override suspend fun loginAdmin(email: String, password: String): BaseResponse<AdminLoginDto> =
        wrap(client.submitForm(url = "/admin/login", formParameters = Parameters.build {
            append("email", email)
            append("password", password)
        }))
//end region admin

    //endregion
}