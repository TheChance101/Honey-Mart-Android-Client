package org.the_chance.honeymart.ui.features.product_details

import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.honeymart.domain.usecase.GetProductDetailsUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.features.category.ProductUiState
import org.the_chance.honeymart.ui.features.products.ProductsInteractionsListener
import org.the_chance.honeymart.ui.features.products.ProductsUiEffect
import org.the_chance.honeymart.ui.features.products.ProductsUiState
import javax.inject.Inject


@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productDetailsUseCase: GetProductDetailsUseCase

) : BaseViewModel<ProductsDetailsUiState, ProductDetailsUiEffect>(ProductsDetailsUiState()),
    ProductDetailsInteractionListener {
    override val TAG: String
        get() = this::class.simpleName.toString()

    override fun onUpdateProductName(name: String) {

    }

    override fun onUpdateProductPrice(price: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateProductDescription(description: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateProductImage(uris: List<ByteArray>) {
        TODO("Not yet implemented")
    }

}