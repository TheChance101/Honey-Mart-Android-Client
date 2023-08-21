package org.the_chance.honeymart.ui.features.product_details


interface ProductDetailsInteractionListener {
    fun updateProductDetails(product: ProductsDetailsUiState)
    fun onUpdateProductName(name: String)
    fun onUpdateProductPrice(price: String)
    fun onUpdateProductDescription(description: String)
    fun onUpdateProductImage(uris: List<ByteArray>)
}