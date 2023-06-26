package org.the_chance.honeymart.util

import java.io.Serializable

sealed class AuthData : Serializable {
    data class ProductDetails(
        val productId: Long,
    ) : AuthData(), Serializable

    data class Products(
        val marketId: Long,
        val position: Int,
        val categoryId: Long,
    ) : AuthData(), Serializable
}