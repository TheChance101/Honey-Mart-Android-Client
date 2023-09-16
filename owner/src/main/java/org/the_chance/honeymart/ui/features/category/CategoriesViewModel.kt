package org.the_chance.honeymart.ui.features.category

import androidx.paging.PagingData
import androidx.paging.map
import arrow.optics.Every
import arrow.optics.copy
import arrow.optics.dsl.every
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.Category
import org.the_chance.honeymart.domain.model.Product
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
        _state.update {
            it.copy { CategoriesUiState.categories set updatedCategories }
        }
        val newState = _state.value.copy {
            CategoriesUiState.isLoading set false
            CategoriesUiState.categories set updatedCategories
            CategoriesUiState.position set 0
        }
        val newCategoryId = if (updatedCategories.isEmpty()) null
        else updatedCategories[_state.value.position].categoryId

        _state.update {
            newState.copy {
                CategoriesUiState.newCategory.categoryId set newCategoryId!!
            }
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

    override fun onClickCategory(categoryId: Long) {
        val updatedCategories = updateSelectedCategory(_state.value.categories, categoryId)
        val updatedPosition = updatedCategories.indexOfFirst { it.categoryId == categoryId }

        _state.update { state ->
            state.copy {
                CategoriesUiState.apply {
                    categories set updatedCategories
                    position set updatedPosition
                    isLoading set false
                    newCategory.categoryId set categoryId
                    newCategory.categoryState set FieldState()
                    categoryIcons.every(Every.list()).isSelected set false
                    showScreenState.showAddCategory set false
                    showScreenState.showUpdateCategory set false
                    showScreenState.showFab set true
                    newProducts.categoryId set categoryId
                }
            }
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
        _state.update { state ->
            state.copy {
                CategoriesUiState.apply {
                    isLoading set false
                    position set 0
                    inside(showScreenState) {
                        ShowScreenState.showFab set true
                        ShowScreenState.showAddProduct set false
                    }
                }
            }
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
        _state.update { state ->
            state.copy {
                CategoriesUiState.apply {
                    isLoading set false
                    newCategory.categoryState set FieldState()
                    categoryIcons.every(Every.list()).isSelected set false
                    inside(snackBar) {
                        SnackBarState.isShow set true
                        SnackBarState.message set success
                    }
                    showScreenState.showFab set true
                }
            }
        }

        getAllCategory()
        resetShowState(Visibility.ADD_CATEGORY)
    }

    override fun resetShowState(visibility: Visibility) {
        when (visibility) {
            Visibility.ADD_CATEGORY -> {
                _state.update { state ->
                    state.copy { CategoriesUiState.showScreenState.showAddCategory set !state.showScreenState.showAddCategory }
                }
            }

            Visibility.UPDATE_CATEGORY -> {
                _state.update { state ->
                    state.copy { CategoriesUiState.showScreenState.showUpdateCategory set !state.showScreenState.showUpdateCategory }
                }
            }

            Visibility.DELETE_CATEGORY -> {
                _state.update { state ->
                    state.copy { CategoriesUiState.showScreenState.showDialog set !state.showScreenState.showDialog }
                }
            }

            Visibility.DELETE_PRODUCT -> {
                _state.update { state ->
                    state.copy { CategoriesUiState.showScreenState.showDeleteDialog set !state.showScreenState.showDeleteDialog }
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
        _state.update { state ->
            state.copy {
                CategoriesUiState.apply {
                    isLoading set false
                    newCategory.categoryState set FieldState()
                    categoryIcons.every(Every.list()).isSelected set false
                    showScreenState.showFab set true
                }
            }
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
        _state.update { state ->
            state.copy {
                CategoriesUiState.apply {
                    inside(showScreenState) {
                        ShowScreenState.showAddProduct set false
                        ShowScreenState.showFab set false
                        ShowScreenState.showProductDetails set true
                    }
                    newProducts.id set productId
                }
            }
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
        _state.update { state ->
            state.copy {
                CategoriesUiState.apply {
                    newProducts.id set product.productId
                    inside(showScreenState) {
                        ShowScreenState.showAddProduct set false
                        ShowScreenState.showFab set true
                    }
                }
            }
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
        _state.update { state ->
            state.copy {
                CategoriesUiState.apply {
                    isLoading set false
                    newProducts.productNameState set FieldState()
                    newProducts.productDescriptionState set FieldState()
                    newProducts.productPriceState set FieldState()
                    newProducts.images set emptyList()
                }
            }
        }
        val categoryId = _state.value.newCategory.categoryId
        getProductsByCategoryId(categoryId = categoryId)
    }

    override fun onImagesSelected(uris: List<ByteArray>) {
        _state.update { it.copy { CategoriesUiState.newProducts.images set uris } }
    }

    override fun onClickRemoveSelectedImage(imageUri: ByteArray) {
        val updatedImages = _state.value.newProducts.images.toMutableList()
        updatedImages.remove(imageUri)
        _state.update { it.copy { CategoriesUiState.newProducts.images set updatedImages } }
    }

    override fun onClickAddProductButton() {
        _state.update { state ->
            state.copy {
                CategoriesUiState.showScreenState.showFab set false
                CategoriesUiState.showScreenState.showAddProduct set true
                inside(CategoriesUiState.newProducts) {
                    NewProductsUiState.productNameState set FieldState()
                    NewProductsUiState.productDescriptionState set FieldState()
                    NewProductsUiState.productPriceState set FieldState()
                    NewProductsUiState.images set emptyList()
                }
            }
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
        _state.update { state ->
            state.copy {
                inside(CategoriesUiState.showScreenState) {
                    ShowScreenState.showProductUpdate set false
                    ShowScreenState.showProductDetails set false
                    ShowScreenState.showFab set true
                }
            }
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
        _state.update { state ->
            state.copy {
                CategoriesUiState.newProducts.productNameState set productNameState
                CategoriesUiState.productDetails.productNameState set productNameState
            }
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
        _state.update { state ->
            state.copy {
                CategoriesUiState.newProducts.productPriceState set productPriceState
                CategoriesUiState.productDetails.productPriceState set productPriceState
            }
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
        _state.update { state ->
            state.copy {
                CategoriesUiState.newProducts.productDescriptionState set productDescriptionState
                CategoriesUiState.productDetails.productDescriptionState set productDescriptionState
            }
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
        _state.update { state ->
            state.copy {
                CategoriesUiState.isLoading set false
                CategoriesUiState.newProducts.images set emptyList()
            }
        }
        getProductsByCategoryId(state.value.newCategory.categoryId)

    }

    override fun onClickUpdateProductDetails() {
        _state.update {
            it.copy {
                CategoriesUiState.showScreenState.showAddProduct set false
                CategoriesUiState.showScreenState.showFab set false
                CategoriesUiState.showScreenState.showProductDetails set false
                CategoriesUiState.showScreenState.showProductUpdate set true
                CategoriesUiState.productDetails.productPriceState.name set it.productDetails.productPriceState.name.removeDollarSign()
            }
        }
    }

    override fun onClickCancel() {
        _state.update { state ->
            state.copy {
                inside(CategoriesUiState.showScreenState) {
                    ShowScreenState.apply {
                        showAddProduct set false
                        showFab set false
                        showProductDetails set true
                        showProductUpdate set false
                    }
                }
            }
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
        _state.update { state ->
            state.copy {
                CategoriesUiState.apply {
                    isLoading set false
                    isError set false
                    inside(showScreenState) {
                        ShowScreenState.showAddProduct set true
                        ShowScreenState.showProductDetails set false
                        ShowScreenState.showProductUpdate set false
                    }
                    productDetails set ProductUiState()
                }
            }
        }
        getProductsByCategoryId(_state.value.newCategory.categoryId)
    }

    // endregion

    // region Helper
    override fun resetSnackBarState() {
        _state.update { it.copy { CategoriesUiState.snackBar.isShow set false } }
    }

    override fun onNewCategoryNameChanged(categoryName: String) {
        val categoryNameState =
            ownerCategories.validationUseCase.validateCategoryNameField(categoryName)

        _state.update {
            it.copy {
                CategoriesUiState.newCategory.categoryState set FieldState(
                    errorState = stringResource.validationString.getOrDefault(
                        categoryNameState,
                        ""
                    ),
                    name = categoryName,
                    isValid = categoryNameState == ValidationState.VALID_CATEGORY_NAME
                )
            }
        }
    }

    override fun onClickNewCategoryIcon(categoryIconId: Int) {
        val updateIcons = _state.value.categoryIcons.map { iconState ->
            iconState.copy(isSelected = iconState.categoryIconId == categoryIconId)
        }
        _state.update { state ->
            state.copy {
                CategoriesUiState.newCategory.newIconId set categoryIconId
                CategoriesUiState.categoryIcons set updateIcons
            }
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
        _state.update { state ->
            state.copy {
                inside(CategoriesUiState.showScreenState) {
                    ShowScreenState.apply {
                        showProductDetails set false
                        showAddCategory set false
                        showUpdateCategory set false
                        showAddProduct set false
                        showProductUpdate set false
                        showCategoryProducts set false
                        showFab set true
                    }
                }
            }
        }
    }
}