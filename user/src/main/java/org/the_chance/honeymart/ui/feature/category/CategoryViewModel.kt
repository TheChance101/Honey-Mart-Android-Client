package org.the_chance.honeymart.ui.feature.category

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetMarketAllCategoriesUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getAllCategories: GetMarketAllCategoriesUseCase,
  //  val id: Long,
) : BaseViewModel<CategoriesUiState>(CategoriesUiState()), CategoryInteractionListener {
    override val TAG: String = this::class.java.simpleName


    init {
        getAllCategory(0)
    }

    private fun getAllCategory(id: Long) {
        _uiState.update {it.copy(isLoading = true) }
        tryToExecute(
            { getAllCategories(id)},
            { category -> category.asCategoriesUiState() },
            ::onSuccess,
            ::onError
        )
    }

    private fun onSuccess(categories: List<CategoryUiState>){
        this._uiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                categories = categories
            )
        }
    }

    private fun onError(){
        this._uiState.update {
            it.copy(
                isLoading = false,
                isError = true
            )
        }
    }

    override fun onCategoryClicked(id: Long) {
        //navigate to category details
    }
}