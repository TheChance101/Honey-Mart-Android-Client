package org.the_chance.honeymart.domain.model

data class Cart(
    val products: List<Product>,
    val total: Double = 0.0
) {
    data class Product(
        val id: Long = 0L,
        val name: String = "",
        val count: Int = 0,
        val price: Double = 0.0,
        val images: List<String> = emptyList(),
    )
}

