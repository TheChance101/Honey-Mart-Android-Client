package org.the_chance.honeymart.ui.features.products

interface ProductsInteractionsListener {
    fun addProduct(product: ProductsUiState)
    fun addProductImage(productId: Long, images: List<ByteArray>)
    fun onProductNameChanged(name: String)
    fun onProductPriceChanged(price: String)
    fun onProductDescriptionChanged(description: String)
    fun onImagesSelected(uris: List<ByteArray>)
    fun onClickRemoveSelectedImage(imageUri: ByteArray)
}