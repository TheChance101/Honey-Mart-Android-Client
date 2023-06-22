package org.the_chance.honeymart.domain.model



/**
 * Created by Aziza Helmy on 6/4/2023.
 */

data class ProductEntity(
    val productId: Long?,
    val productName: String?,
    val productDescription: String?,
    val ProductPrice: Double?,
    val productImages:List<String>?
)

