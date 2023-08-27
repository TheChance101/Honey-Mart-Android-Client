package org.the_chance.honeymart.domain.model

data class OrderDetails(
    val products: List<ProductDetails>,
    val totalPrice: Double,
    val date: String,
    val state: Int,
    val orderId: Long
) {
    data class ProductDetails(
        val id: Long,
        val name: String,
        val count: Int,
        val price: Double,
        val images: List<String>
    )
}


