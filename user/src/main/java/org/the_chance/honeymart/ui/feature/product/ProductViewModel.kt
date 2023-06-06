package org.the_chance.honeymart.ui.feature.product

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetAllProductsUseCase
import org.the_chance.honeymart.domain.usecase.GetMarketAllCategoriesUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.product.uistste.CategoryUiState
import org.the_chance.honeymart.ui.feature.product.uistste.ProductsUiState
import org.the_chance.honeymart.ui.feature.product.uistste.asCategoryUiState
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProducts: GetAllProductsUseCase,
    private val getMarketAllCategories: GetMarketAllCategoriesUseCase,
) : BaseViewModel<ProductsUiState>(ProductsUiState()), ProductInteractionListener,
    CategoryProductInteractionListener {

    init {
      getCategoriesByMarketId()
        }

  private fun getCategoriesByMarketId(){
      _uiState.update { it.copy(isLoading = true)}
          tryToExecute(
              { getMarketAllCategories(1)},
              {Category -> Category.asCategoryUiState()},
                ::onSuccess,
                ::onError
          )
      }

   private fun onSuccess(categories: List<CategoryUiState>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                categoryList = categories
            )
        }
    }

    private fun onError() {
        this._uiState.update {
            it.copy(
                isLoading = false,
                isError = true,
            )
        }
    }
    override val TAG: String = this::class.simpleName.toString()

    override fun onClickCategoryProduct(id: Int) {

    }

    override fun onClickProduct(id: Int) {

    }
}