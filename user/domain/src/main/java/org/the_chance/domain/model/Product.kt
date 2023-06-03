package org.the_chance.domain.model

import org.the_chance.data.source.remote.models.ProductDto

data class Product(
    val id: Long,
    val name: String,
    val quantity: String,
    val price: Double
)
fun ProductDto.asProduct() = Product(
    id = id,
    name = name,
    quantity = quantity,
    price = price,
)
