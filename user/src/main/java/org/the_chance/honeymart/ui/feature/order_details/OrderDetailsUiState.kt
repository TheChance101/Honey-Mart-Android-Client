package org.the_chance.honeymart.ui.feature.order_details

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import org.the_chance.honeymart.domain.model.OrderDetails
import org.the_chance.honeymart.domain.util.ErrorHandler
import java.text.SimpleDateFormat
import java.util.Date

data class OrderDetailsUiState(
    val isProductsLoading: Boolean = false,
    val isDetailsLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val isError: Boolean = false,
    val orderDetails: OrderParentDetailsUiState = OrderParentDetailsUiState(),
    val products: List<OrderDetailsProductUiState> = emptyList(),
)

data class OrderParentDetailsUiState(
    val totalPrice: Double = 0.0,
    val date: String = "",
    val state: Int = 1,
) {
    val stateText = when (state) {
        1 -> "Pending"
        2 -> "Processing"
        3 -> "Done"
        4 -> "Cancelled"
        else -> "Declined"
    }
    val totalPriceCurrency = formatCurrencyWithNearestFraction(totalPrice)
}

data class OrderDetailsProductUiState(
    val id: Long = 0L,
    val name: String = "",
    val count: Int = 0,
    val price: Double = 0.0,
    val images: List<String> = emptyList(),
) {
    val imageUrl = images.takeIf { it.isNotEmpty() }?.firstOrNull() ?: ""
    val priceCurrency = formatCurrencyWithNearestFraction(price)
}

fun OrderDetails.ProductDetails.toOrderDetailsProductUiState(): OrderDetailsProductUiState {
    return OrderDetailsProductUiState(
        id = id,
        name = name,
        price = price,
        count = count,
        images = images
    )
}

fun OrderDetails.toOrderParentDetailsUiState(): OrderParentDetailsUiState {
    return OrderParentDetailsUiState(
        totalPrice = totalPrice,
        state = state,
        date = date.toDateFormat(),
    )
}

@SuppressLint("SimpleDateFormat")
fun Date.toDateFormat(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy")
    return dateFormat.format(this)
}

fun formatCurrencyWithNearestFraction(amount: Double): String {
    val decimalFormat = DecimalFormat("#,##0.0'$'")
    return decimalFormat.format(amount)
}