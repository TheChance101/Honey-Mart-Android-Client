package org.the_chance.honeymart.ui.feature.product_details

interface ProductDetailsInteraction {
    fun onClickFavorite(idProduct: Long)
    fun onClickSmallImage(url: String)

    fun increaseProductCount()
    fun decreaseProductCount()

    fun addProductToCart(productId: Long, count: Int)
}