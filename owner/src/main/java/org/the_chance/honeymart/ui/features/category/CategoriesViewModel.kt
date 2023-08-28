package org.the_chance.honeymart.ui.features.category

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.usecase.AddToCategoryUseCase
import org.the_chance.honeymart.domain.usecase.DeleteCategoryUseCase
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase
import org.the_chance.honeymart.domain.usecase.UpdateCategoryUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.addCategory.CategoryImageUIState
import org.the_chance.honeymart.ui.addCategory.categoryIcons
import org.the_chance.honeymart.ui.addCategory.toCategoryImageUIState
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

    init {
        getCategoryImages()
    }

    // region Categories
    override fun getAllCategory() {
        _state.update {
            it.copy(isLoading = true, isError = false)
        }
        tryToExecute(
            { getAllCategories(1).map { it.toCategoryUiState() } },
            ::onGetCategorySuccess,
            ::onGetCategoryError
        )
    }

    private fun onGetCategorySuccess(categories: List<CategoryUiState>) {
        val updatedCategories =
            updateCategorySelection(categories, categories.first().categoryId)
        this._state.update {
            it.copy(
                isLoading = false,
                error = null,
                categories = updatedCategories,
                position = 0
            )
        }
    }

    private fun onGetCategoryError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

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
                isLoading = false,
                error = null,
                position = 0
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
    override fun onClickCategory(categoryId: Long) {
        val updatedCategories = updateCategorySelection(_state.value.categories, categoryId)
        val position = updatedCategories.indexOfFirst { it.categoryId == categoryId }
        _state.update {
            it.copy(
                categories = updatedCategories,
                position = position,
                categoryId = categoryId,
                isLoading = false,
                showAddCategory = false,

                )
        }
        getProductsByCategoryId(categoryId = categoryId)
    }

    private fun updateCategorySelection(
        categories: List<CategoryUiState>,
        selectedCategoryId: Long,
    ): List<CategoryUiState> {
        return categories.map { category ->
            category.copy(isCategorySelected = category.categoryId == selectedCategoryId)
        }
    }

    fun getCategoryImages() {
        _state.update {
            it.copy(isLoading = false, categoryImages = categoryIcons.toCategoryImageUIState())
        }
    }

    private fun addCategory(name: String, categoryImageID: Int) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            function = { addCategoryUseCase(name, categoryImageID) },
            onSuccess = ::addCategorySuccess,
            onError = ::addCategoryError
        )
    }

    private fun addCategorySuccess(success: String) {
        _state.update {
            it.copy(
                isLoading = false,
                nameCategory = "",
                snackBar = it.snackBar.copy(isShow = true, message = success),
            )
        }
        getAllCategory()
        resetShowState(Visibility.ADD_CATEGORY)
    }

    override fun resetSnackBarState() {
        _state.update { it.copy(snackBar = it.snackBar.copy(isShow = false)) }
    }


    private fun addCategoryError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    private fun getProductsByCategoryId(categoryId: Long) {
//        _state.update { it.copy(isLoading = true, isError = false) }
//        tryToExecute(
//            { getAllProducts(categoryId) },
//            ::onGetProductsSuccess,
//            ::onGetProductsError
//        )
    }

    private fun onGetProductsError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    private fun onGetProductsSuccess(products: List<ProductEntity>) {
        _state.update { it.copy(isLoading = false) }

    }

    override fun changeNameCategory(nameCategory: String) {
        val categoryNameState: ValidationState = getCategoryNameState(nameCategory)
        _state.update {
            it.copy(
                nameCategory = nameCategory,
                categoryNameState = categoryNameState
            )
        }
    }

    override fun onClickAddCategory() {
        addCategory(_state.value.nameCategory.trim(), _state.value.categoryImageId)
    }

    override fun onClickCategoryImage(categoryImageId: Int) {
        val updatedCategories =
            updateCategoryImageSelection(_state.value.categoryImages, categoryImageId)
        _state.update {
            it.copy(
                categoryImages = updatedCategories,
                isLoading = false,
                categoryImageId = categoryImageId
            )
        }

    }

    private fun updateCategoryImageSelection(
        categoryImages: List<CategoryImageUIState>,
        selectedCategoryImageId: Int,
    ): List<CategoryImageUIState> {
        return categoryImages.map { category ->
            category.copy(
                isSelected = category.categoryImageId == selectedCategoryImageId
            )

        }
    }

    override fun resetShowState(visibility: Visibility) {
        when (visibility) {
            Visibility.ADD_CATEGORY -> {
                _state.update {
                    it.copy(
                        showAddCategory = !_state.value.showAddCategory,
                    )
                }
            }

            Visibility.UPDATE_CATEGORY -> {
                _state.update {
                    it.copy(
                        showUpdateCategory = !_state.value.showUpdateCategory
                    )
                }
            }

            Visibility.DELETE_CATEGORY -> {
                _state.update {
                    it.copy(
                        showDialog = !_state.value.showDialog
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
            function = {
                updateCategoryUseCase(
                    imageId = category.categoryImageId,
                    name = category.nameCategory,
                    id = category.categoryId,
                    marketId = 1
                )
            },
            onSuccess = { onUpdateCategorySuccess() },
            onError = ::onUpdateCategoryError
        )
    }

    private fun onUpdateCategorySuccess() {
        _state.update { it.copy(isLoading = false, error = null) }
        getAllCategory()
        resetShowState(Visibility.UPDATE_CATEGORY)
    }

    private fun onUpdateCategoryError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    override fun onUpdatedCategoryNameChanged(name: String) {
        val categoryNameState: ValidationState = getCategoryNameState(name)
        _state.update { it.copy(categoryNameState = categoryNameState, nameCategory = name) }
    }

    private fun getCategoryNameState(name: String): ValidationState {
        val productNameState: ValidationState = when {
            name.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            name.length <= 2 -> ValidationState.SHORT_LENGTH_TEXT
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return productNameState
    }

    override fun onClickCategoryIcon(categoryIconId: Int) {
        val updatedIcons = _state.value.categoryImages.map { imageState ->
            imageState.copy(isSelected = imageState.categoryImageId == categoryIconId)
        }
        _state.update {
            it.copy(categoryImages = updatedIcons, categoryImageId = categoryIconId)
        }
    }

    // endregion

}