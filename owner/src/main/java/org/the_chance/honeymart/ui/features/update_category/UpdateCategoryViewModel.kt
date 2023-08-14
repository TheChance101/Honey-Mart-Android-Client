package org.the_chance.honeymart.ui.features.update_category

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.UpdateCategoryUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateCategoryViewModel @Inject constructor(
    private val updateCategoryUseCase: UpdateCategoryUseCase
) : BaseViewModel<UpdateCategoryUiState, Unit>(UpdateCategoryUiState()),
    UpdateCategoryInteractionListener {

    override val TAG: String = this::class.java.simpleName
    private val marketId = 7L
    private val categoryId = 43L

    init {
        getCategoryIcons()
    }

    private fun getCategoryIcons() {
        _state.update {
            it.copy(isLoading = false, categoryIcons = categoryIcons.toCategoryImageUIState())
        }
    }

    override fun updateCategory(category: UpdateCategoryUiState) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            function = {
                updateCategoryUseCase(
                    imageId = category.categoryIconId,
                    name = category.categoryName,
                    id = categoryId,
                    marketId = marketId
                )
            },
            onSuccess = { onUpdateCategorySuccess() },
            onError = ::onUpdateCategoryError
        )
    }

    private fun onUpdateCategorySuccess() {
        _state.update { it.copy(isLoading = false, error = null) }
        log("SUUUUUUUUUUUUUUUUUUUUUUI")
    }

    private fun onUpdateCategoryError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
        log("ERRRRRRRRRRRRRRRRROR")
    }

    override fun onUpdatedCategoryNameChanged(name: String) {
        val productNameState: ValidationState = when {
            name.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            name.length <= 2 -> ValidationState.SHORT_LENGTH_TEXT
            else -> ValidationState.VALID_TEXT_FIELD
        }
        _state.update { it.copy(categoryNameState = productNameState, categoryName = name) }
    }

    override fun onClickCategoryIcon(categoryIconId: Int) {
        val updatedIcons = _state.value.categoryIcons.map { iconState ->
            iconState.copy(isCategorySelected = iconState.categoryIconId == categoryIconId)
        }
        _state.update {
            it.copy(categoryIcons = updatedIcons, categoryIconId = categoryIconId)
        }
    }

    override fun onClickCancelUpdateCategory() {

    }
}