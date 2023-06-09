package org.the_chance.honeymart.ui.feature.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.CategoriesUiState
import org.the_chance.honeymart.ui.feature.uistate.CategoryUiState
import org.the_chance.honeymart.ui.feature.uistate.asCategoriesUiState
import org.the_chance.honeymart.ui.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getAllCategories: GetAllCategoriesInMarketUseCase,
    //  val id: Long,
) : BaseViewModel<CategoriesUiState>(CategoriesUiState()), CategoryInteractionListener {
    override val TAG: String = this::class.java.simpleName

    private val _uiCategoryState = MutableLiveData<EventHandler<Long>>()
    val uiCategoryState: LiveData<EventHandler<Long>>
        get() = _uiCategoryState

    fun getAllCategory(id: Long) {
        _uiState.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllCategories(id) },
            { category -> category.asCategoriesUiState() },
            ::onSuccess,
            ::onError
        )
    }

    private fun onSuccess(categories: List<CategoryUiState>) {
        this._uiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                categories = categories
            )
        }
    }

    private fun onError() {
        this._uiState.update {
            it.copy(
                isLoading = false,
                isError = true
            )
        }
    }

    override fun onCategoryClicked(id: Long) {
        _uiCategoryState.postValue(EventHandler(id))
    }
}