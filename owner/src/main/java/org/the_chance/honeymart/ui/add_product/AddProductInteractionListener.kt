package org.the_chance.honeymart.ui.add_product

interface AddProductInteractionListener {

    fun onProductNameChanged(name: String)
    fun onProductPriceChanged(price: String)
    fun onProductDescriptionChanged(description: String)
    fun addProduct(name: String, price: Double, description: String, images: List<ByteArray>)
    fun onImagesSelected(uris: List<ByteArray>)
    fun onClickRemoveSelectedImage(imageUri: ByteArray)
}