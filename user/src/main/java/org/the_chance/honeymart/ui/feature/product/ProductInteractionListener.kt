package org.the_chance.honeymart.ui.feature.product

interface ProductInteractionListener {
    fun onClickProduct(productId: Long)
    fun onChangeProductScrollPosition(position: Int)
    fun onClickFavIcon(productId: Long)
    fun onClickCategory(categoryId: Long)
    fun onclickTryAgain()
    fun onclickTryAgainProducts()
    fun resetSnackBarState()
    fun showSnackBar(message: String)

     fun getData()
}