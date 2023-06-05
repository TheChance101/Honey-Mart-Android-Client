package org.the_chance.honeymart.ui.feature.product

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.GetAllProductsUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.product.uistste.ProductsUiState
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProducts: GetAllProductsUseCase,
) : BaseViewModel<ProductsUiState>(ProductsUiState()), ProductInteractionListener,
    CategoryProductInteractionListener {

    init {
        viewModelScope.launch {
            try {
                val result = getAllProducts
                log("products : ${result.invoke()}")
            } catch (e: Exception) {
                log("products : ${e.message}")
            }
        }
    }

    override val TAG: String = this::class.simpleName.toString()

    override fun onClickCategoryProduct(id: Int) {

    }

    override fun onClickProduct(id: Int) {

    }


}