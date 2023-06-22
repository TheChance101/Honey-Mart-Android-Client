package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.CartDto
import org.the_chance.honeymart.data.source.remote.models.CartProductDto
import org.the_chance.honeymart.domain.model.CartEntity
import org.the_chance.honeymart.domain.model.CartProductEntity

fun CartDto.toCartEntity() = CartEntity(
    products = products.toCartProductEntity(),
    total = total
)

fun List<CartProductDto>.toCartProductEntity(): List<CartProductEntity> {
    return this.map {
        CartProductEntity(
            productId = it.productId,
            name = it.name,
            price = it.price,
            count = it.count
        )
    }
}
