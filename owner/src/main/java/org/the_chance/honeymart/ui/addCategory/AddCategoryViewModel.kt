package org.the_chance.honeymart.ui.addCategory

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.AddToCategoryUseCase
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val addToCategoryUseCase: AddToCategoryUseCase,
    private val getAllCategories: GetAllCategoriesInMarketUseCase
): BaseViewModel<AddCategoryUIState, AddCategoryUIEffect>(AddCategoryUIState()),
    AddCategoryListener {

    override val TAG: String = "addCategory"

    init {
        getCategoryImages()
        getAllCategory(8)
    }

    private fun getCategoryImages(){
        _state.update { it.copy(isLoading = false, categoryImages = categoryImages.map { it.toCategoryImageUIState() }) }
    }

    private fun addCategory(name: String, CategoryImageID: Int ){
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            function = { addToCategoryUseCase.invoke(name, CategoryImageID) },
            onSuccess = ::addCategorySuccess,
            onError = ::addCategoryError

        )

    }

    private fun addCategorySuccess(success: String) {
        _state.update { it.copy(isLoading = false, nameCategory = "") }
        getAllCategory(8)
        showSnackBar(success)
    }

    private fun showSnackBar(message: String) {
        _state.update {
            it.copy(
                snackBarState = it.snackBarState.copy(
                    isShow = true,
                    message = message
                )
            )
        }
    }

    override fun resetSnackBarState() {
        _state.update { it.copy(snackBarState = it.snackBarState.copy(isShow = false)) }
    }

    private fun addCategoryError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun changeNameCategory(nameCategory: String) {
        _state.update { it.copy(nameCategory = nameCategory) }
    }

    override fun onClickAddCategory(){
        addCategory(_state.value.nameCategory.trim(), _state.value.categoryImageId)
    }

    override fun onClickCategoryImage(categoryImageId: Int) {
        val updatedCategories =
            updateCategoryImageSelection(_state.value.categoryImages, categoryImageId)
        val position = updatedCategories.indexOfFirst { it.categoryImageId == categoryImageId }
        _state.update {
            it.copy(
                categoryImages = updatedCategories,
                isLoading = false,
                position = position.inc(),
                categoryImageId = categoryImageId
            )
        }
    }

    private fun updateCategoryImageSelection(
        categoryImages: List<CategoryImageUIState>,
        selectedCategoryImageId: Int,
    ): List<CategoryImageUIState> {
        return categoryImages.map { category ->
            category.copy(isSelected = category.categoryImageId == selectedCategoryImageId)
        }
    }

    private fun getAllCategory(marketId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { getAllCategories(marketId).map { it.toCategoryUIState() } },
            ::onGetCategorySuccess,
            ::onGetCategoryError
        )
    }

    private fun onGetCategorySuccess(categories: List<CategoryUIState>) {
        this._state.update {
            it.copy(
                isLoading = false,
                error = null,
                categories = categories,
            )
        }
    }

    private fun onGetCategoryError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun onClickCategory(categoryId: Long) {
        val updatedCategories = updateCategorySelection(_state.value.categories, categoryId)
        val position = updatedCategories.indexOfFirst { it.categoryId == categoryId }
        _state.update {
            it.copy(
                categories = updatedCategories,
                position = position.inc(),
                categoryId = categoryId,
                isLoading = false,
            )
        }
    }

    private fun updateCategorySelection(
        categories: List<CategoryUIState>,
        selectedCategoryId: Long,
    ): List<CategoryUIState> {
        return categories.map { category ->
            category.copy(isCategorySelected = category.categoryId == selectedCategoryId)
        }
    }

}