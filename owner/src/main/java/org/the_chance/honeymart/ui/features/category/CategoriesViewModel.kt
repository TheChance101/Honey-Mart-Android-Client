package org.the_chance.honeymart.ui.features.category

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.usecase.AddToCategoryUseCase
import org.the_chance.honeymart.domain.usecase.DeleteCategoryUseCase
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase
import org.the_chance.honeymart.domain.usecase.UpdateCategoryUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.addCategory.categoryIcons
import org.the_chance.honeymart.ui.base.BaseViewModel
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
    private val deleteCategoryUseCase: DeleteCategoryUseCase
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

        _state.update {
            it.copy(
                categories = updatedCategories,
                position = position,
                isLoading = false,
                newCategory = it.newCategory.copy(categoryId = categoryId),
                showScreenState = it.showScreenState.copy(
                    showAddCategory = false,
                    showUpdateCategory = false
                )
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
        _state.update { it.copy(isLoading = false, error = null, position = 0) }
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
            it.copy(isLoading = false, products = products.toProductUiState())
        }
    }

    private fun onGetProductsError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
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
    // endregion
}