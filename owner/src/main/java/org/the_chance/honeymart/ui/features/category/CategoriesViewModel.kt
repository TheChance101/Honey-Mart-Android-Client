package org.the_chance.honeymart.ui.features.category

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.usecase.AddProductImagesUseCase
import org.the_chance.honeymart.domain.usecase.AddProductUseCase
import org.the_chance.honeymart.domain.usecase.AddToCategoryUseCase
import org.the_chance.honeymart.domain.usecase.DeleteCategoryUseCase
import org.the_chance.honeymart.domain.usecase.DeleteProductByIdUseCase
import org.the_chance.honeymart.domain.usecase.DeleteProductImagesUseCase
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase
import org.the_chance.honeymart.domain.usecase.GetProductDetailsUseCase
import org.the_chance.honeymart.domain.usecase.UpdateCategoryUseCase
import org.the_chance.honeymart.domain.usecase.UpdateProductDetailsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.features.category.composable.categoryIcons
import javax.inject.Inject

/**
 * Created by Aziza Helmy on 8/7/2023.
 */
@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getAllCategories: GetAllCategoriesInMarketUseCase,
    private val addCategoryUseCase: AddToCategoryUseCase,
    private val getAllProducts: GetAllProductsByCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val addProductImagesUseCase: AddProductImagesUseCase,
    private val productDetailsUseCase: GetProductDetailsUseCase,
    private val deleteProductByIdUseCase: DeleteProductByIdUseCase,
    private val deleteProductImagesUseCase: DeleteProductImagesUseCase,
    private val updateProductDetailsUseCase: UpdateProductDetailsUseCase,


    ) : BaseViewModel<CategoriesUiState, CategoriesUiEffect>(CategoriesUiState()),
    CategoriesInteractionsListener {

    override val TAG: String
        get() = this::class.simpleName.toString()
    private val marketID: Long = 5L

    init {
        getCategoryImages()
    }

    // region Categories
    override fun getAllCategory() {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { getAllCategories(marketID) },
            ::onGetCategorySuccess,
            ::onGetCategoryError
        )
    }

    private fun onGetCategorySuccess(categories: List<CategoryEntity>) {
        val categoriesUiState = categories.toCategoryUiState()
        val updatedCategories =
            updateSelectedCategory(categoriesUiState, categoriesUiState.first().categoryId)

        _state.update {
            it.copy(isLoading = false, error = null, categories = updatedCategories, position = 0)
        }
    }

    private fun onGetCategoryError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    private fun getCategoryImages() {
        _state.update {
            it.copy(isLoading = false, categoryIcons = categoryIcons.toCategoryImageUIState())
        }
    }

    override fun onClickCategory(categoryId: Long) {
        val updatedCategories = updateSelectedCategory(_state.value.categories, categoryId)
        val position = updatedCategories.indexOfFirst { it.categoryId == categoryId }

        _state.update {
            it.copy(
                categories = updatedCategories,
                position = position,
                isLoading = false,
                newCategory = it.newCategory.copy(categoryId = categoryId),
                showScreenState = it.showScreenState.copy(
                    showAddCategory = false,
                    showUpdateCategory = false,
                    showFab = true,
                ),
                newProducts = it.newProducts.copy(categoryId = categoryId)
            )
        }

        getProductsByCategoryId(categoryId = categoryId)
    }

    private fun updateSelectedCategory(
        categories: List<CategoryUiState>,
        selectedCategoryId: Long,
    ): List<CategoryUiState> {
        return categories.map { category ->
            category.copy(
                isCategorySelected = category.categoryId == selectedCategoryId,
//                categoryId = selectedCategoryId
            )
        }
    }
    // endregion

    // region Delete Category
    override fun deleteCategory(id: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            function = { deleteCategoryUseCase(id) },
            onSuccess = { onDeleteCategorySuccess() },
            onError = ::onDeleteCategoryError
        )
    }

    private fun onDeleteCategorySuccess() {
        _state.update {
            it.copy(
                isLoading = false, error = null, position = 0,
                showScreenState = it.showScreenState
                    .copy(showFab = true, showAddProduct = false)
            )
        }
        getAllCategory()

    }

    private fun onDeleteCategoryError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = errorHandler) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }
    // endregion

    // region Add Category

    override fun onClickAddCategory(name: String, categoryIconID: Int) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { addCategoryUseCase(name, categoryIconID) },
            ::addCategorySuccess,
            ::addCategoryError
        )
    }

    private fun addCategorySuccess(success: String) {
        _state.update {
            it.copy(
                isLoading = false,
                newCategory = it.newCategory.copy(newCategoryName = ""),
                categoryIcons = it.categoryIcons.map { categoryIconState ->
                    categoryIconState.copy(isSelected = false)
                },
                snackBar = it.snackBar.copy(isShow = true, message = success),
                showScreenState = it.showScreenState.copy(showFab = true)
            )
        }
        getAllCategory()
        resetShowState(Visibility.ADD_CATEGORY)
    }

    private fun addCategoryError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun resetShowState(visibility: Visibility) {
        when (visibility) {
            Visibility.ADD_CATEGORY -> {
                _state.update {
                    it.copy(
                        showScreenState = it.showScreenState.copy(
                            showAddCategory = !it.showScreenState.showAddCategory
                        ),
                    )
                }
            }

            Visibility.UPDATE_CATEGORY -> {
                _state.update {
                    it.copy(
                        showScreenState = it.showScreenState.copy(
                            showUpdateCategory = !it.showScreenState.showUpdateCategory
                        ),
                    )
                }
            }

            Visibility.DELETE_CATEGORY -> {
                _state.update {
                    it.copy(
                        showScreenState = it.showScreenState.copy(
                            showDialog = !it.showScreenState.showDialog
                        ),
                    )
                }
            }

            Visibility.DELETE_PRODUCT -> {
                _state.update {
                    it.copy(
                        showScreenState = it.showScreenState.copy(
                            showDialog = !it.showScreenState.showDialog
                        ),
                    )
                }
            }
        }
    }
    // endregion

    // region Update Category
    override fun updateCategory(category: CategoriesUiState) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {
                updateCategoryUseCase(
                    imageId = category.newCategory.newIconId,
                    name = category.newCategory.newCategoryName,
                    id = category.newCategory.categoryId,
                    marketId = marketID
                )
            },
            { onUpdateCategorySuccess() },
            ::onUpdateCategoryError
        )
    }

    private fun onUpdateCategorySuccess() {
        _state.update {
            it.copy(
                isLoading = false, error = null,
                newCategory = it.newCategory.copy(newCategoryName = ""),
                categoryIcons = it.categoryIcons.map { categoryIconState ->
                    categoryIconState.copy(isSelected = false)
                },
                showScreenState = it.showScreenState.copy(showFab = true)

            )
        }
        getAllCategory()
        resetShowState(Visibility.UPDATE_CATEGORY)
        getAllCategory()
    }

    private fun onUpdateCategoryError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    // endregion

    // region Category Products
    private fun getProductsByCategoryId(categoryId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { getAllProducts(categoryId) },
            ::onGetProductsSuccess,
            ::onGetProductsError
        )
    }

    private fun onGetProductsSuccess(products: List<ProductEntity>) {
        _state.update {
            it.copy(isLoading = false, error = null, products = products.toProductUiState())
        }


    }

    private fun onGetProductsError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }


    // endregion

    // region Get Products

    override fun addProduct(product: CategoriesUiState) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {
                addProductUseCase(
                    product.newProducts.name,
                    product.newProducts.price.toDouble(),
                    product.newProducts.description,
                    product.newProducts.categoryId
                )
            },
            onSuccess = ::onAddProductSuccess,
            onError = ::onAddProductError
        )
    }

    private fun onAddProductSuccess(product: ProductEntity) {
        _state.update {
            it.copy(
                error = null,
                newProducts = it.newProducts.copy(id = product.productId),
                showScreenState = it.showScreenState.copy(showAddProduct = false, showFab = true)
            )
        }
        addProductImage(productId = product.productId, images = _state.value.newProducts.images)
    }

    private fun onAddProductError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    override fun addProductImage(productId: Long, images: List<ByteArray>) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { addProductImagesUseCase(productId, images) },
            onSuccess = { onAddProductImagesSuccess() },
            onError = ::onAddProductImagesError
        )
    }

    private fun onAddProductImagesSuccess() {
        _state.update {
            it.copy(
                isLoading = false, error = null,
                newProducts = it.newProducts.copy(
                    name = "",
                    description = "",
                    price = " ",
                    images = emptyList(),
                ),
            )
        }
        val categoryId = _state.value.newCategory.categoryId
        getProductsByCategoryId(categoryId = categoryId)
    }

    private fun onAddProductImagesError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    override fun onProductNameChanged(name: String) {
        val productNameState: ValidationState = when {
            name.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            name.length <= 2 -> ValidationState.SHORT_LENGTH_TEXT
            else -> ValidationState.VALID_TEXT_FIELD
        }
        _state.update {
            it.copy(
                newProducts = it.newProducts.copy(
                    productNameState = productNameState,
                    name = name
                )
            )
        }
    }

    override fun onProductPriceChanged(price: String) {
        val productPriceState = getProductPriceState(price)
        _state.update {
            it.copy(
                newProducts = it.newProducts.copy(
                    productPriceState = productPriceState,
                    price = price
                )
            )
        }
    }

    private fun getProductPriceState(price: String): ValidationState {
        val priceRegex = Regex("^[0-9]+(\\.[0-9]+)?$")
        val productPriceState: ValidationState = when {
            price.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            !price.matches(priceRegex) -> ValidationState.INVALID_PRICE
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return productPriceState
    }

    override fun onProductDescriptionChanged(description: String) {
        val productDescriptionState = getProductDescriptionState(description)
        _state.update {
            it.copy(
                newProducts = it.newProducts.copy(
                    productDescriptionState = productDescriptionState,
                    description = description
                )
            )
        }
    }

    private fun getProductDescriptionState(description: String): ValidationState {
        val productDescriptionState: ValidationState = when {
            description.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            description.length < 6 -> ValidationState.SHORT_LENGTH_TEXT
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return productDescriptionState
    }

    override fun onImagesSelected(uris: List<ByteArray>) {
        _state.update { it.copy(newProducts = it.newProducts.copy(images = uris)) }
    }

    override fun onClickRemoveSelectedImage(imageUri: ByteArray) {
        val updatedImages = _state.value.newProducts.images.toMutableList()
        updatedImages.remove(imageUri)
        _state.update { it.copy(newProducts = it.newProducts.copy(images = updatedImages)) }
    }

    // endregion

    // region Update Products


    private fun getProductDetails(productId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { productDetailsUseCase(productId) },
            ::onGetProductDetailsSuccess,
            ::onGetProductDetailsError
        )
    }

    private fun onGetProductDetailsSuccess(productDetails: ProductEntity) {
        _state.update {
            it.copy(
                isLoading = false,
                productDetails = productDetails.toProductDetailsUiState()
            )
        }
    }


    private fun onGetProductDetailsError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }

    override fun updateProductDetails(product: CategoriesUiState) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            function = {
                updateProductDetailsUseCase(
                    id = product.productDetails.productId,
                    name = product.productDetails.productName,
                    price = product.productDetails.productPrice.toDouble(),
                    description = product.productDetails.productsQuantity,
                )
            },
            onSuccess = { onUpdateProductDetailsSuccess() },
            ::onUpdateProductDetailsError
        )
    }

    private fun onUpdateProductDetailsSuccess() {
        _state.update { it.copy(isLoading = false, error = null) }
    }

    private fun onUpdateProductDetailsError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    private fun updateProductNameState(productName: String): ValidationState {
        val productNameState: ValidationState = when {
            productName.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            productName.length <= 2 -> ValidationState.SHORT_LENGTH_TEXT
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return productNameState
    }

    override fun onUpdateProductName(productName: String) {
        val productNameState = updateProductNameState(productName)
        _state.update {
            it.copy(
                newProducts = it.newProducts.copy(
                    productNameState = productNameState
                ),
                productDetails = it.productDetails.copy(productName = productName)
            )
        }
    }


    private fun updateProductPriceState(productPrice: String): ValidationState {
        val priceRegex = Regex("^[0-9]+(\\.[0-9]+)?$")
        val productPriceState: ValidationState = when {
            productPrice.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            !productPrice.matches(priceRegex) -> ValidationState.INVALID_PRICE
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return productPriceState
    }

    override fun onUpdateProductPrice(productPrice: String) {
        val productPriceState = updateProductPriceState(productPrice)
        _state.update {
            it.copy(
                newProducts = it.newProducts.copy(
                    productPriceState = productPriceState
                ),
                productDetails = it.productDetails.copy(productPrice = productPrice)
            )
        }
    }

    private fun updateProductDescriptionState(productDescription: String): ValidationState {
        val productDescriptionState: ValidationState = when {
            productDescription.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            productDescription.length < 6 -> ValidationState.SHORT_LENGTH_TEXT
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return productDescriptionState
    }

    override fun onUpdateProductDescription(productDescription: String) {
        val productDescriptionState = updateProductDescriptionState(productDescription)
        _state.update {
            it.copy(
                newProducts = it.newProducts.copy(
                    productDescriptionState = productDescriptionState,
                ),
                productDetails = it.productDetails.copy(productDescription = productDescription)
            )
        }
    }

    override fun onUpdateProductImage(uris: List<ByteArray>) {

    }

    // endregion
    // region Delete Product
    override fun deleteProductById(productId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { deleteProductByIdUseCase(productId) },
            ::onSuccessDeleteProduct,
            ::onErrorDeleteProduct,
        )
    }

    private fun onSuccessDeleteProduct(success: String) {
        _state.update { it.copy(isLoading = false, isError = false) }
        val productID = _state.value.newProducts.id
        deleteProductImages(productId = productID)
    }

    private fun onErrorDeleteProduct(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    private fun deleteProductImages(productId: Long) {
        tryToExecute(
            { deleteProductImagesUseCase(productId) },
            ::onSuccessDeleteProductImages,
            ::onErrorDeleteProductImages,
        )
    }

    private fun onSuccessDeleteProductImages(success: String) {
        _state.update { it.copy(isLoading = false, isError = false) }
        val categoryId = _state.value.newCategory.categoryId
        getProductsByCategoryId(categoryId = categoryId)
    }

    private fun onErrorDeleteProductImages(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }
    // endregion

    // region Helper
    override fun resetSnackBarState() {
        _state.update { it.copy(snackBar = it.snackBar.copy(isShow = false)) }
    }

    override fun onNewCategoryNameChanged(categoryName: String) {
        val categoryNameState: ValidationState = getCategoryNameState(categoryName)

        _state.update {
            it.copy(
                newCategory = it.newCategory.copy(
                    newCategoryName = categoryName,
                    categoryNameState = categoryNameState
                )
            )
        }
        getProductDetails(state.value.productDetails.productId)
    }

    override fun onClickNewCategoryIcon(categoryIconId: Int) {
        val updateIcons = _state.value.categoryIcons.map { iconState ->
            iconState.copy(
                isSelected = iconState.categoryIconId == categoryIconId
            )
        }
        _state.update {
            it.copy(
                categoryIcons = updateIcons,
                newCategory = it.newCategory.copy(newIconId = categoryIconId)
            )
        }

    }

    private fun getCategoryNameState(name: String): ValidationState {
        val productNameState: ValidationState = when {
            name.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            name.length <= 2 -> ValidationState.SHORT_LENGTH_TEXT
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return productNameState
    }

    override fun onClickAddProductButton() {
        _state.update {
            it.copy(
                showScreenState = it.showScreenState.copy(showFab = false, showAddProduct = true)
            )
        }

    }

    override fun onClickProduct(productId: Long) {
        _state.update {
            it.copy(
                showScreenState = it.showScreenState.copy(
                    showAddProduct = false,
                    showFab = false,
                    showProductDetails = true,
                ),
                newProducts = it.newProducts.copy(id = productId)
            )
        }
        val productID = _state.value.newProducts.id
        getProductDetails(productID)
    }

    override fun onClickUpdateProductDetails() {
        _state.update {
            it.copy(
                showScreenState = it.showScreenState.copy(
                    showAddProduct = false,
                    showFab = false,
                    showProductDetails = false,
                    showProductUpdate = true
                )
            )
        }

        Log.e(
            "mah",
            "onClickUpdateProductDetails: ${state.value.showScreenState.showProductUpdate}"
        )
    }

    override fun onClickCancel() {
        _state.update {
            it.copy(
                showScreenState = it.showScreenState.copy(
                    showAddProduct = false,
                    showFab = false,
                    showProductDetails = true,
                    showProductUpdate = false
                )
            )
        }
    }

    //endRegion
}