package org.the_chance.honeymart.ui.add_product

import android.net.Uri
import org.the_chance.honeymart.domain.util.ErrorHandler

data class AddProductUiState(
    val isLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val productName: String = "",
    val numberOfItems: Int = 0,
    val price: Double = 0.0,
    val description: String = "",
    val productImages: List<Uri> = emptyList(),
)
