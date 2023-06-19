package org.the_chance.honeymart.domain.model

data class OrderProductEntity(
    val id: Long,
    val name: String,
    val count: Double,
    val price: Double
)