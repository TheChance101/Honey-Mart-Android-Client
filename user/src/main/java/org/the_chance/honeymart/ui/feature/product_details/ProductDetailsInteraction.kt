package org.the_chance.honeymart.ui.feature.product_details

interface ProductDetailsInteraction {
    fun onClickFavorite(productId: Long)
    fun onClickSmallImage(url: String)
    fun onClickBack()
    fun increaseProductCount()
    fun decreaseProductCount()

    fun addProductToCart(productId: Long, count: Int)
    fun resetSnackBarState()
}