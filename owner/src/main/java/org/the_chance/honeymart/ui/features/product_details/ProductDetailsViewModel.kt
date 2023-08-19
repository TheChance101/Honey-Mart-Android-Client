package org.the_chance.honeymart.ui.features.product_details

import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.features.category.ProductUiState
import org.the_chance.honeymart.ui.features.products.ProductsUiEffect
import javax.inject.Inject


@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
) : BaseViewModel<ProductUiState, ProductsUiEffect>(ProductUiState()) {
    override val TAG: String
        get() = this::class.simpleName.toString()
}