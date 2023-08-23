package org.the_chance.honeymart.ui.feature.product

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.category.CategoryUiState

data class ProductsUiState(
    val isLoadingCategory: Boolean = false,
    val isLoadingProduct: Boolean = false,
    val error: ErrorHandler? = null,
    val snackBar: SnackBarState = SnackBarState(),
    val isError: Boolean = false,
    val position: Int = 0,
    val products: Flow<PagingData<ProductUiState>> = flow{},
    val isEmptyProducts: Boolean = false,
    val categories: List<CategoryUiState> = emptyList(),
    val categoryId: Long = 0L,
)

data class SnackBarState(
    val isShow: Boolean = false,
    val productId: Long = 0L,
    val message: String = ""
)


data class ProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productDescription: String = "",
    val productPrice: Double = 0.0,
    val isFavorite: Boolean = false,
    val productImages: List<String> = emptyList()
)

fun ProductEntity.toProductUiState(): ProductUiState {
    return ProductUiState(
        productId = productId,
        productName = productName,
        productDescription = productDescription,
        productPrice = productPrice,
        productImages = productImages.ifEmpty { listOf("","") }
    )
}



fun ProductsUiState.contentScreen() = !this.isLoadingCategory && !this.isError
fun ProductsUiState.emptyPlaceHolder() = this.isEmptyProducts &&
        !this.isError && !this.isLoadingProduct
fun ProductsUiState.loading() = this.isLoadingProduct && !this.isEmptyProducts

