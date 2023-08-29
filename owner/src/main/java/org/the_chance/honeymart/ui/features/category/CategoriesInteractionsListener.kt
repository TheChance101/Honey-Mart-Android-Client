package org.the_chance.honeymart.ui.features.category

/**
 * Created by Aziza Helmy on 8/7/2023.
 */
interface CategoriesInteractionsListener {

    fun onClickCategory(categoryId: Long)
    fun onNewCategoryNameChanged(categoryName: String)
    fun onClickAddCategory(name: String, categoryIconID: Int)
    fun resetShowState(visibility: Visibility)
    fun resetSnackBarState()
    fun onClickNewCategoryIcon(categoryIconId: Int)
    fun updateCategory(category: CategoriesUiState)
    fun deleteCategory(id: Long)
    fun getAllCategory()
    fun addProduct(product: CategoriesUiState)
    fun addProductImage(productId: Long, images: List<ByteArray>)
    fun onProductNameChanged(name: String)
    fun onProductPriceChanged(price: String)
    fun onProductDescriptionChanged(description: String)
    fun onImagesSelected(uris: List<ByteArray>)
    fun onClickRemoveSelectedImage(imageUri: ByteArray)
    fun onClickAddProductButton()
    fun onUpdateProductName(productName: String)
    fun onUpdateProductPrice(productPrice: String)
    fun onUpdateProductDescription(productDescription: String)
    fun updateProductDetails(product: CategoriesUiState)
    fun onClickProduct(productId: Long)
    fun onClickUpdateProductDetails()
    fun onClickCancel()
    fun deleteProductById(productId: Long)
    fun onUpdateProductImage(productId: Long, images: List<ByteArray>)
    fun onErrorProducts()
}