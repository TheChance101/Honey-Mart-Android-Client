package org.the_chance.honeymart.ui.features.category

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.model.Category
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.model.Reviews
import org.the_chance.honeymart.domain.usecase.GetAllProductReviewsUseCase
import org.the_chance.honeymart.domain.usecase.usecaseManager.owner.OwnerCategoriesManagerUseCase
import org.the_chance.honeymart.domain.usecase.usecaseManager.owner.OwnerMarketsManagerUseCase
import org.the_chance.honeymart.domain.usecase.usecaseManager.owner.OwnerProductsManagerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.util.StringDictionary
import org.the_chance.honymart.ui.composables.categoryIcons
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val ownerCategories: OwnerCategoriesManagerUseCase,
    private val ownerProducts: OwnerProductsManagerUseCase,
    private val ownerMarkets: OwnerMarketsManagerUseCase,
    private val stringResource: StringDictionary,
    private val productReviewsUseCase: GetAllProductReviewsUseCase,

    ) : BaseViewModel<CategoriesUiState, CategoriesUiEffect>(CategoriesUiState()),
    CategoriesInteractionsListener {

    override val TAG: String = this::class.java.simpleName
    private var page = MutableStateFlow(_state.value.page)
    private var reviewScrollPosition = 0

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
        _state.update {
            it.copy(categories = updatedCategories)
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
        log(_state.value.categories.toString())

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

   override fun onChangeReviews(position: Int) {
        reviewScrollPosition = position
    }

    private fun insert() {
        page.value += 1
    }

    override fun onScrollDown() {
        viewModelScope.launch {
            if ((reviewScrollPosition + 1) >= (page.value * MAX_PAGE_SIZE)) {
                _state.update { it.copy(isLoadingReviewsPaging = true) }
                insert()
                if (page.value > 1) {
                    val result = productReviewsUseCase(
                        _state.value.newProducts.id, page.value
                    ).toReviews()
                    appendReviews(result)
                }
                _state.update { it.copy(isLoadingReviewsPaging = false) }
            }
        }
    }

    private fun appendReviews(reviews: ReviewDetailsUiState) {
        val current = ArrayList(state.value.reviews.reviews)
        current.addAll(reviews.reviews)
        _state.update {
            it.copy(
                reviews = reviews.copy(
                    reviewStatisticUiState = reviews.reviewStatisticUiState,
                    reviews = current
                )
            )
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
                    categoryState = FieldState()
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
        val categoryNameValidationState =
            ownerCategories.validationUseCase.validateCategoryNameField(name)
        if (categoryNameValidationState == ValidationState.VALID_CATEGORY_NAME) {
            tryToExecute(
                { ownerCategories.addCategoryUseCase(name, categoryIconID) },
                ::addCategorySuccess,
                ::onError
            )
        }
    }

    private fun addCategorySuccess(success: String) {
        _state.update {
            it.copy(
                isLoading = false,
                newCategory = it.newCategory.copy(categoryState = FieldState()),
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
                    name = category.newCategory.categoryState.name,
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
                newCategory = it.newCategory.copy(categoryState = FieldState()),
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
        _state.update { it.copy(isLoading = true) }
        resetSearchState()
        getProducts(page.value, categoryId)
    }

    override fun onChangeProductScrollPosition(position: Int) {
        _state.update { it.copy(productsScrollPosition = position) }
        if ((_state.value.productsScrollPosition + 1) >= (page.value * MAX_PAGE_SIZE)) {
            _state.update { it.copy(page = it.page + 1) }
            page.update { it + 1 }
            getProducts(page.value)
        }
    }

    private fun resetSearchState() {
        _state.update { it.copy(products = listOf()) }
        page.update { 1 }
        onChangeProductScrollPosition(0)
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
        getProductReviews(productID)
    }
    // endregion

    // region Add Product
    override fun addProduct(product: CategoriesUiState) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {
                ownerProducts.addProductUseCase(
                    product.newProducts.productNameState.name,
                    product.newProducts.productPriceState.name.toDouble(),
                    product.newProducts.productDescriptionState.name,
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
                    productNameState = FieldState(),
                    productDescriptionState = FieldState(),
                    productPriceState = FieldState(),
                    images = emptyList(),
                ),
            )
        }
        val categoryId = _state.value.newCategory.categoryId
        getProductsByCategoryId(categoryId = categoryId)
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
                showScreenState = it.showScreenState.copy(
                    showFab = false,
                    showAddProduct = true
                ),
                newProducts = it.newProducts.copy(
                    productNameState = FieldState(),
                    images = emptyList(),
                    productDescriptionState = FieldState(),
                    productPriceState = FieldState(),
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


    private fun getProductReviews(productId: Long) {
        _state.update { it.copy(isLoading = true, isError = false,
            reviews = it.reviews.copy(reviews = emptyList())) }
        onChangeReviews(0)
        page.value = 1
        tryToExecute(
            { productReviewsUseCase(productId, page.value) },
            ::onGetProductReviewsSuccess,
            ::onGetProductReviewsError
        )
    }

    private fun onGetProductReviewsSuccess(reviews: Reviews) {
        _state.update {
            it.copy(
                isLoading = false,
                reviews = reviews.toReviews(),
            )
        }
    }

    private fun onGetProductReviewsError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }


    //endregion

    //region Update Product
    override fun updateProductDetails(product: CategoriesUiState) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {
                ownerProducts.updateProductDetailsUseCase(
                    id = product.productDetails.productId,
                    name = product.productDetails.productNameState.name,
                    price = product.productDetails.productPriceState.name.toDouble(),
                    description = product.productDetails.productDescriptionState.name,
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
        val productNameStateValidation =
            ownerProducts.validationUseCase.validateProductNameField(productName)
        val productNameState = FieldState(
            errorState = stringResource.validationString.getOrDefault(
                productNameStateValidation,
                ""
            ),
            name = productName,
            isValid = productNameStateValidation == ValidationState.VALID_PRODUCT_NAME
        )
        _state.update {
            it.copy(
                newProducts = it.newProducts.copy(productNameState = productNameState),
                productDetails = it.productDetails.copy(productNameState = productNameState)
            )
        }
    }

    override fun onUpdateProductPrice(productPrice: String) {
        val productPriceStateValidation =
            ownerProducts.validationUseCase.validateProductPrice(productPrice)
        val productPriceState = FieldState(
            errorState = stringResource.validationString.getOrDefault(
                productPriceStateValidation,
                ""
            ),
            name = productPrice,
            isValid = productPriceStateValidation == ValidationState.VALID_PRODUCT_PRICE
        )
        _state.update {
            it.copy(
                newProducts = it.newProducts.copy(productPriceState = productPriceState),
                productDetails = it.productDetails.copy(productPriceState = productPriceState)
            )
        }
    }

    override fun onUpdateProductDescription(productDescription: String) {
        val productDescriptionStateValidation =
            ownerProducts.validationUseCase.validateProductDescription(productDescription)
        val productDescriptionState = FieldState(
            errorState = stringResource.validationString.getOrDefault(
                productDescriptionStateValidation,
                ""
            ),
            name = productDescription,
            isValid = productDescriptionStateValidation == ValidationState.VALID_PRODUCT_DESCRIPTION
        )
        _state.update {
            it.copy(
                newProducts = it.newProducts.copy(productDescriptionState = productDescriptionState),
                productDetails = it.productDetails.copy(productDescriptionState = productDescriptionState)
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
                    productPriceState = it.productDetails.productPriceState.copy(
                        name = it.productDetails.productPriceState.name.removeDollarSign()
                    )
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
                ),
                productDetails = ProductUiState()
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
        val categoryNameState =
            ownerCategories.validationUseCase.validateCategoryNameField(categoryName)

        _state.update {
            it.copy(
                newCategory = it.newCategory.copy(
                    categoryState = FieldState(
                        errorState = stringResource.validationString.getOrDefault(
                            categoryNameState,
                            ""
                        ),
                        name = categoryName,
                        isValid = categoryNameState == ValidationState.VALID_CATEGORY_NAME
                    ),
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

    private fun getProducts(page: Int, categoriesId: Long? = null) {
        _state.update { it.copy(isLoadingPaging = true, isErrorPaging = false, error = null) }
        tryToExecute(
            {
                ownerProducts.getAllProducts(
                    categoriesId ?: state.value.newCategory.categoryId, page
                ).map { it.toProductUiState() }
            },
            ::onGetProductsSuccess,
            ::onGetProductsError
        )
    }

    private fun onGetProductsSuccess(data: List<ProductUiState>) {
        _state.update {
            if (page.value > 1) {
                val current = state.value.products.toMutableList()
                current.addAll(data)
                it.copy(products = current, isLoadingPaging = false, isLoading = false)
            } else {
                it.copy(products = data, isLoadingPaging = false, isLoading = false)
            }
        }
    }

    private fun onGetProductsError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoadingPaging = false,
                isLoading = false,
                isErrorPaging = true,
                error = error
            )
        }
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

    companion object {
        const val MAX_PAGE_SIZE = 10
    }
}