package org.the_chance.honeymart.ui.features.category

import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.Category
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.usecase.OwnerCategoriesManagerUseCase
import org.the_chance.honeymart.domain.usecase.OwnerMarketsManagerUseCase
import org.the_chance.honeymart.domain.usecase.OwnerProductsManagerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honymart.ui.composables.categoryIcons
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val ownerCategories: OwnerCategoriesManagerUseCase,
    private val ownerProducts: OwnerProductsManagerUseCase,
    private val ownerMarkets: OwnerMarketsManagerUseCase,
) : BaseViewModel<CategoriesUiState, CategoriesUiEffect>(CategoriesUiState()),
    CategoriesInteractionsListener {

    override val TAG: String = this::class.java.simpleName

    init {
        getCategoryImages()
    }

    // region Categories
    override fun getAllCategory() {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { ownerCategories.getAllCategories(ownerMarkets.getOwnerMarketId.getOwnerMarketId()) },
            ::onGetCategorySuccess,
            ::onGetCategoryError
        )
    }

    private fun onGetCategorySuccess(categories: List<Category>) {
        val categoriesUiState = categories.toCategoryUiState()
        val updatedCategories = if (categories.isEmpty()) {
            categoriesUiState
        } else {
            updateSelectedCategory(categoriesUiState, categoriesUiState.first().categoryId)
        }
        val newState = _state.value.copy(
            isLoading = false,
            error = null,
            categories = updatedCategories,
            position = 0
        )
        val newCategoryId = if (updatedCategories.isEmpty()) null
        else updatedCategories[_state.value.position].categoryId

        _state.update {
            newState.copy(
                newCategory = newState.newCategory.copy(categoryId = newCategoryId!!)
            )
        }

        getProductsByCategoryId(newCategoryId!!)
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
                newCategory = it.newCategory.copy(
                    categoryId = categoryId,
                    newCategoryName = ""
                ),
                categoryIcons = it.categoryIcons.map { categoryIconState ->
                    categoryIconState.copy(isSelected = false)
                },
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
            category.copy(isCategorySelected = category.categoryId == selectedCategoryId)
        }
    }
    // endregion

    // region Delete Category
    override fun deleteCategory(id: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { ownerCategories.deleteCategoryUseCase(id) },
            { onDeleteCategorySuccess() },
            ::onDeleteCategoryError
        )
    }

    private fun onDeleteCategorySuccess() {
        _state.update {
            it.copy(
                isLoading = false,
                error = null,
                position = 0,
                showScreenState = it.showScreenState.copy(
                    showFab = true,
                    showAddProduct = false
                )
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
            { ownerCategories.addCategoryUseCase(name, categoryIconID) },
            ::addCategorySuccess,
            ::onError
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
                            showDeleteDialog = !it.showScreenState.showDeleteDialog
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
                ownerCategories.updateCategoryUseCase(
                    imageId = category.newCategory.newIconId,
                    name = category.newCategory.newCategoryName,
                    id = category.newCategory.categoryId,
                    marketId = ownerMarkets.getOwnerMarketId.getOwnerMarketId()
                )
            },
            { onUpdateCategorySuccess() },
            ::onError
        )
    }

    private fun onUpdateCategorySuccess() {
        _state.update {
            it.copy(
                isLoading = false,
                error = null,
                newCategory = it.newCategory.copy(newCategoryName = ""),
                categoryIcons = it.categoryIcons.map { categoryIconState ->
                    categoryIconState.copy(isSelected = false)
                },
                showScreenState = it.showScreenState.copy(showFab = true)
            )
        }
        resetShowState(Visibility.UPDATE_CATEGORY)
        getAllCategory()
    }

    // endregion

    // region Category Products
    private fun getProductsByCategoryId(categoryId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecutePaging(
            { ownerProducts.getAllProducts(categoryId) },
            ::onGetProductsSuccess,
            ::onError
        )
    }

    private fun onGetProductsSuccess(products: PagingData<Product>) {
        val mappedProducts = products.map { it.toProductUiState() }

        _state.update {
            it.copy(isLoading = false, error = null, products = flowOf(mappedProducts))
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
                newProducts = it.newProducts.copy(
                    id = productId,
                )
            )

        }
        val productID = _state.value.newProducts.id
        getProductDetails(productID)
    }
    // endregion

    // region Add Product
    override fun addProduct(product: CategoriesUiState) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {
                ownerProducts.addProductUseCase(
                    product.newProducts.name,
                    product.newProducts.price.toDouble(),
                    product.newProducts.description,
                    product.newCategory.categoryId
                )
            },
            ::onAddProductSuccess,
            ::onError
        )
    }

    private fun onAddProductSuccess(product: Product) {
        _state.update {
            it.copy(
                error = null,
                newProducts = it.newProducts.copy(id = product.productId),
                showScreenState = it.showScreenState.copy(showAddProduct = false, showFab = true),

                )
        }
        addProductImage(
            productId = product.productId,
            images = _state.value.newProducts.images
        )
    }

    override fun addProductImage(productId: Long, images: List<ByteArray>) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { ownerProducts.addProductImagesUseCase(productId, images) },
            onSuccess = { onAddProductImagesSuccess() },
            onError = ::onError
        )
    }

    private fun onAddProductImagesSuccess() {
        _state.update {
            it.copy(
                isLoading = false,
                error = null,
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

    override fun onProductNameChanged(name: String) {
        val productNameState = getNameState(name)
        _state.update {
            it.copy(
                newProducts = it.newProducts.copy(productNameState = productNameState, name = name)
            )
        }
    }

    override fun onProductPriceChanged(price: String) {
        val priceState = getPriceState(price)
        _state.update {
            it.copy(
                newProducts = it.newProducts.copy(productPriceState = priceState, price = price)
            )
        }
    }

    override fun onProductDescriptionChanged(description: String) {
        val productDescriptionState = getDescriptionState(description)
        _state.update {
            it.copy(
                newProducts = it.newProducts.copy(
                    productDescriptionState = productDescriptionState,
                    description = description
                )
            )
        }
    }

    override fun onImagesSelected(uris: List<ByteArray>) {
        _state.update { it.copy(newProducts = it.newProducts.copy(images = uris)) }
    }

    override fun onClickRemoveSelectedImage(imageUri: ByteArray) {
        val updatedImages = _state.value.newProducts.images.toMutableList()
        updatedImages.remove(imageUri)
        _state.update { it.copy(newProducts = it.newProducts.copy(images = updatedImages)) }
    }

    override fun onClickAddProductButton() {
        _state.update {
            it.copy(
                showScreenState = it.showScreenState.copy(showFab = false,
                    showAddProduct = true),
                newProducts = it.newProducts.copy(
                    name = "",
                    description = "",
                    price = "",
                    images = emptyList(),
                ),
            )
        }
    }
    // endregion

    // region Get Products
    private fun getProductDetails(productId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { ownerProducts.getProductDetailsUseCase(productId) },
            ::onGetProductDetailsSuccess,
            ::onGetProductDetailsError
        )
    }

    private fun onGetProductDetailsSuccess(productDetails: Product) {
        _state.update {
            it.copy(isLoading = false, productDetails = productDetails.toProductDetailsUiState())
        }
    }

    private fun onGetProductDetailsError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }
    //endregion

    //region Update Product
    override fun updateProductDetails(product: CategoriesUiState) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {
                ownerProducts.updateProductDetailsUseCase(
                    id = product.productDetails.productId,
                    name = product.productDetails.productName,
                    price = product.productDetails.productPrice.toDouble(),
                    description = product.productDetails.productDescription,
                )
            },
            { onUpdateProductDetailsSuccess() },
            ::onError
        )
    }

    private fun onUpdateProductDetailsSuccess() {
        _state.update {
            it.copy(
                showScreenState = it.showScreenState.copy(
                    showProductUpdate = false,
                    showProductDetails = false,
                    showFab = true
                )
            )
        }
        onUpdateProductImage(state.value.newProducts.id, state.value.newProducts.images)
    }

    override fun onUpdateProductName(productName: String) {
        val productNameState = getNameState(productName)
        _state.update {
            it.copy(
                newProducts = it.newProducts.copy(productNameState = productNameState),
                productDetails = it.productDetails.copy(productName = productName)
            )
        }
    }

    override fun onUpdateProductPrice(productPrice: String) {
        val productPriceState = getPriceState(productPrice)
        _state.update {
            it.copy(
                newProducts = it.newProducts.copy(productPriceState = productPriceState),
                productDetails = it.productDetails.copy(productPrice = productPrice)
            )
        }
    }

    override fun onUpdateProductDescription(productDescription: String) {
        val descriptionState = getDescriptionState(productDescription)
        _state.update {
            it.copy(
                newProducts = it.newProducts.copy(productDescriptionState = descriptionState),
                productDetails = it.productDetails.copy(productDescription = productDescription)
            )
        }
    }

    override fun onUpdateProductImage(productId: Long, images: List<ByteArray>) {
        tryToExecute(
            { ownerProducts.updateProductImagesUseCase(productId, images) },
            { onUpdateProductImageSuccess() },
            ::onError
        )
    }

    private fun onUpdateProductImageSuccess() {
        _state.update {
            it.copy(
                isLoading = false, error = null,
                newProducts = it.newProducts.copy(images = emptyList()),
            )
        }
        getProductsByCategoryId(state.value.newCategory.categoryId)

    }

    override fun onClickUpdateProductDetails() {
        _state.update {
            it.copy(
                showScreenState = it.showScreenState.copy(
                    showAddProduct = false,
                    showFab = false,
                    showProductDetails = false,
                    showProductUpdate = true
                ),
                productDetails = it.productDetails.copy(
                    productPrice = it.productDetails.productPrice.removeDollarSign()
                ),
            )
        }
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
    //endregion

    // region Delete Product
    override fun deleteProductById(productId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { ownerProducts.deleteProductByIdUseCase(productId) },
            { onSuccessDeleteProduct() },
            ::onError,
        )
    }

    private fun onSuccessDeleteProduct() {
        _state.update {
            it.copy(
                isLoading = false, isError = false, showScreenState = it.showScreenState.copy(
                    showProductDetails = false, showProductUpdate = false, showAddProduct = true
                )
            )
        }
        getProductsByCategoryId(_state.value.newCategory.categoryId)
    }

    // endregion

    // region Helper
    override fun resetSnackBarState() {
        _state.update { it.copy(snackBar = it.snackBar.copy(isShow = false)) }
    }

    override fun onNewCategoryNameChanged(categoryName: String) {
        val categoryNameState: ValidationState = getNameState(categoryName)

        _state.update {
            it.copy(
                newCategory = it.newCategory.copy(
                    newCategoryName = categoryName, categoryNameState = categoryNameState
                )
            )
        }

    }

    override fun onClickNewCategoryIcon(categoryIconId: Int) {
        val updateIcons = _state.value.categoryIcons.map { iconState ->
            iconState.copy(isSelected = iconState.categoryIconId == categoryIconId)
        }
        _state.update {
            it.copy(
                categoryIcons = updateIcons,
                newCategory = it.newCategory.copy(newIconId = categoryIconId)
            )
        }
    }

    private fun getNameState(name: String): ValidationState {
        val productNameState: ValidationState = when {
            name.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            name.length <= 2 -> ValidationState.SHORT_LENGTH_TEXT
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return productNameState
    }

    private fun getDescriptionState(description: String): ValidationState {
        val descriptionState: ValidationState = when {
            description.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            description.length < 6 -> ValidationState.SHORT_LENGTH_TEXT
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return descriptionState
    }

    private fun getPriceState(priceState: String): ValidationState {
        val priceRegex = Regex("^[0-9]{1,6}(\\.[0-9]{1,2})?$")
        val state: ValidationState = when {
            priceState.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            !priceState.matches(priceRegex) -> ValidationState.INVALID_PRICE
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return state
    }
    //endregion

    private fun onError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    fun resetStateScreen() {
        _state.update {
            it.copy(
                showScreenState = it.showScreenState.copy(
                    showProductDetails = false,
                    showAddCategory = false,
                    showUpdateCategory = false,
                    showAddProduct = false,
                    showProductUpdate = false,
                    showCategoryProducts = false,
                    showFab = true,
                )
            )
        }
    }
}