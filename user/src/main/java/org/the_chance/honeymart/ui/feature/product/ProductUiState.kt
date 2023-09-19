package org.the_chance.honeymart.ui.feature.product

import android.icu.text.DecimalFormat
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.marketInfo.CategoryUiState

data class ProductsUiState(
    val isLoadingCategory: Boolean = false,
    val isLoadingProduct: Boolean = false,
    val error: ErrorHandler? = null,
    val snackBar: SnackBarState = SnackBarState(),
    val isError: Boolean = false,
    val position: Int = 0,
    val products: Flow<PagingData<ProductUiState>> = flow {},
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
) {
    val imageUrl = productImages.takeIf { it.isNotEmpty() }?.firstOrNull() ?: ""
    val priceInCurrency = productPrice.formatCurrencyWithNearestFraction()
}

fun Product.toProductUiState(): ProductUiState {
    return ProductUiState(
        productId = productId,
        productName = productName,
        productDescription = productDescription,
        productPrice = productPrice,
        productImages = productImages.ifEmpty { listOf("", "") }
    )
}


fun ProductsUiState.contentScreen() = !this.isLoadingCategory && !this.isError
fun ProductsUiState.emptyPlaceHolder() =
    !this.isError && !this.isLoadingProduct && !this.isEmptyProducts && !this.isLoadingCategory

fun ProductsUiState.loading() = this.isLoadingProduct && !this.isEmptyProducts

fun Double.formatCurrencyWithNearestFraction(): String {
    val decimalFormat = DecimalFormat("'$'#,##0.0")
    return decimalFormat.format(this)
}