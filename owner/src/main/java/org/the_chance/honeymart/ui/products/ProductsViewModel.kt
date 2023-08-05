package org.the_chance.honeymart.ui.products

import org.the_chance.honeymart.ui.base.BaseViewModel

class ProductsViewModel : BaseViewModel<ProductsUiState, ProductsUiEffect>(ProductsUiState()) {
    override val TAG: String
        get() = this::class.simpleName.toString()
}