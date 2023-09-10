package org.the_chance.honeymart.ui.features.category

import androidx.paging.PagingData
import androidx.paging.map
import arrow.optics.Every
import arrow.optics.copy
import arrow.optics.dsl.every
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.usecase.AddProductImagesUseCase
import org.the_chance.honeymart.domain.usecase.AddProductUseCase
import org.the_chance.honeymart.domain.usecase.AddToCategoryUseCase
import org.the_chance.honeymart.domain.usecase.DeleteCategoryUseCase
import org.the_chance.honeymart.domain.usecase.DeleteProductByIdUseCase
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase
import org.the_chance.honeymart.domain.usecase.GetProductDetailsUseCase
import org.the_chance.honeymart.domain.usecase.UpdateCategoryUseCase
import org.the_chance.honeymart.domain.usecase.UpdateImageProductUseCase
import org.the_chance.honeymart.domain.usecase.UpdateProductDetailsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.features.category.composable.categoryIcons
import javax.inject.Inject

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
    private val updateProductDetailsUseCase: UpdateProductDetailsUseCase,
    private val updateProductImagesUseCase: UpdateImageProductUseCase,
) : BaseViewModel<CategoriesUiState, CategoriesUiEffect>(CategoriesUiState()),
    CategoriesInteractionsListener {

    override val TAG: String = this::class.java.simpleName
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

        _state.update { state ->
            state.copy {
                CategoriesUiState.categories set updatedCategories
                CategoriesUiState.position set position
                CategoriesUiState.isLoading set false
                CategoriesUiState.newCategory.categoryId set categoryId
                CategoriesUiState.newProducts.categoryId set categoryId
                inside(CategoriesUiState.showScreenState) {
                    ShowScreenState.showAddCategory set false
                    ShowScreenState.showUpdateCategory set false
                    ShowScreenState.showFab set true
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
            { deleteCategoryUseCase(id) },
            { onDeleteCategorySuccess() },
            ::onDeleteCategoryError
        )
    }

    private fun onDeleteCategorySuccess() {
        _state.update { state ->
            state.copy {
                CategoriesUiState.isLoading set false
                CategoriesUiState.position set 0
                inside(CategoriesUiState.showScreenState) {
                    ShowScreenState.showFab set true
                    ShowScreenState.showAddProduct set false
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
        tryToExecute(
            { addCategoryUseCase(name, categoryIconID) },
            ::addCategorySuccess,
            ::onError
        )
    }

    private fun addCategorySuccess(success: String) {
        _state.update { state ->
            state.copy {
                CategoriesUiState.isLoading set false
                CategoriesUiState.newCategory.newCategoryName set ""
                CategoriesUiState.categoryIcons.every(Every.list()).isSelected set false
                inside(CategoriesUiState.snackBar) {
                    SnackBarState.isShow set true
                    SnackBarState.message set success
                }
                CategoriesUiState.showScreenState.showFab set true
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
                updateCategoryUseCase(
                    imageId = category.newCategory.newIconId,
                    name = category.newCategory.newCategoryName,
                    id = category.newCategory.categoryId,
                    marketId = marketID
                )
            },
            { onUpdateCategorySuccess() },
            ::onError
        )
    }

    private fun onUpdateCategorySuccess() {
        _state.update { state ->
            state.copy {
                CategoriesUiState.isLoading set false
                CategoriesUiState.newCategory.newCategoryName set ""
                CategoriesUiState.categoryIcons.every(Every.list()).isSelected set false
                CategoriesUiState.showScreenState.showFab set true
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
            { getAllProducts(categoryId) },
            ::onGetProductsSuccess,
            ::onError
        )
    }

    private fun onGetProductsSuccess(products: PagingData<ProductEntity>) {
        val mappedProducts = products.map { it.toProductUiState() }

        _state.update {
            it.copy(isLoading = false, error = null, products = flowOf(mappedProducts))
        }
    }

    override fun onClickProduct(productId: Long) {
        _state.update { state ->
            state.copy {
                inside(CategoriesUiState.showScreenState) {
                    ShowScreenState.showAddProduct set false
                    ShowScreenState.showFab set false
                    ShowScreenState.showProductDetails set true
                }
                CategoriesUiState.newProducts.id set productId
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
                addProductUseCase(
                    product.newProducts.name,
                    product.newProducts.price.toDouble(),
                    product.newProducts.description,
                    product.newProducts.categoryId
                )
            },
            ::onAddProductSuccess,
            ::onError
        )
    }

    private fun onAddProductSuccess(product: ProductEntity) {
        _state.update { state ->
            state.copy {
                CategoriesUiState.newProducts.id set product.productId
                inside(CategoriesUiState.showScreenState) {
                    ShowScreenState.showAddProduct set false
                    ShowScreenState.showFab set true
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
            { addProductImagesUseCase(productId, images) },
            onSuccess = { onAddProductImagesSuccess() },
            onError = ::onError
        )
    }

    private fun onAddProductImagesSuccess() {
        _state.update { state ->
            state.copy {
                CategoriesUiState.isLoading set false
                inside(CategoriesUiState.newProducts) {
                    NewProductsUiState.name set ""
                    NewProductsUiState.description set ""
                    NewProductsUiState.price set ""
                    NewProductsUiState.images set emptyList()
                }
            }
        }
        val categoryId = _state.value.newCategory.categoryId
        getProductsByCategoryId(categoryId = categoryId)
    }

    override fun onProductNameChanged(name: String) {
        val productNameState = getNameState(name)
        _state.update { state ->
            state.copy {
                inside(CategoriesUiState.newProducts) {
                    NewProductsUiState.name set name
                    NewProductsUiState.productNameState set productNameState
                }
            }
        }
    }

    override fun onProductPriceChanged(price: String) {
        val priceState = getPriceState(price)
        _state.update { state ->
            state.copy {
                inside(CategoriesUiState.newProducts) {
                    NewProductsUiState.productPriceState set priceState
                    NewProductsUiState.price set price
                }
            }
        }
    }

    override fun onProductDescriptionChanged(description: String) {
        val productDescriptionState = getDescriptionState(description)
        _state.update { state ->
            state.copy {
                inside(CategoriesUiState.newProducts) {
                    NewProductsUiState.productDescriptionState set productDescriptionState
                    NewProductsUiState.description set description
                }
            }
        }
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
                inside(CategoriesUiState.showScreenState) {
                    ShowScreenState.showAddProduct set true
                    ShowScreenState.showFab set false
                }
            }
        }
    }

    // endregion

    // region Get Products
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
                updateProductDetailsUseCase(
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
        val productNameState = getNameState(productName)
        _state.update { state ->
            state.copy {
                CategoriesUiState.newProducts.productNameState set productNameState
                CategoriesUiState.productDetails.productName set productName
            }
        }
    }

    override fun onUpdateProductPrice(productPrice: String) {
        val productPriceState = getPriceState(productPrice)
        _state.update { state ->
            state.copy {
                CategoriesUiState.newProducts.productPriceState set productPriceState
                CategoriesUiState.productDetails.productPrice set productPrice
            }
        }
    }

    override fun onUpdateProductDescription(productDescription: String) {
        val descriptionState = getDescriptionState(productDescription)
        _state.update { state ->
            state.copy {
                CategoriesUiState.newProducts.productDescriptionState set descriptionState
                CategoriesUiState.productDetails.productDescription set productDescription
            }
        }
    }

    override fun onUpdateProductImage(productId: Long, images: List<ByteArray>) {
        tryToExecute(
            { updateProductImagesUseCase(productId, images) },
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
        _state.update { state ->
            state.copy {
                inside(CategoriesUiState.showScreenState) {
                    ShowScreenState.showAddProduct set false
                    ShowScreenState.showFab set false
                    ShowScreenState.showProductDetails set false
                    ShowScreenState.showProductUpdate set true
                }
                CategoriesUiState.productDetails.productPrice set state.productDetails.productPrice.removeDollarSign()
            }
        }
    }

    override fun onClickCancel() {
        _state.update { state ->
            state.copy {
                inside(CategoriesUiState.showScreenState) {
                    ShowScreenState.showAddProduct set false
                    ShowScreenState.showFab set false
                    ShowScreenState.showProductDetails set true
                    ShowScreenState.showProductUpdate set false
                }
            }
        }
    }
    //endregion

    // region Delete Product
    override fun deleteProductById(productId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { deleteProductByIdUseCase(productId) },
            { onSuccessDeleteProduct() },
            ::onError,
        )
    }

    private fun onSuccessDeleteProduct() {
        _state.update { state ->
            state.copy {
                CategoriesUiState.isLoading set false
                CategoriesUiState.isError set false
                inside(CategoriesUiState.showScreenState) {
                    ShowScreenState.showAddProduct set true
                    ShowScreenState.showProductDetails set false
                    ShowScreenState.showProductUpdate set false
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
        val categoryNameState: ValidationState = getNameState(categoryName)

        _state.update { state ->
            state.copy {
                CategoriesUiState.newCategory.newCategoryName set categoryName
                CategoriesUiState.newCategory.categoryNameState set categoryNameState
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
        val priceRegex = Regex("^[0-9]+(\\.[0-9]+)?$")
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
        _state.update { state ->
            state.copy {
                inside(CategoriesUiState.showScreenState) {
                    ShowScreenState.showProductDetails set false
                    ShowScreenState.showAddCategory set false
                    ShowScreenState.showUpdateCategory set false
                    ShowScreenState.showAddProduct set false
                    ShowScreenState.showProductUpdate set false
                    ShowScreenState.showCategoryProducts set false
                    ShowScreenState.showFab set true
                }
            }
        }
    }
}