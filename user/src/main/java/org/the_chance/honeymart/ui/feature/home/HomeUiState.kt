package org.the_chance.honeymart.ui.feature.home

data class HomeUiState(
    val searchClick: Boolean = false,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: String? = null,
    val pagerItems: List<PagerItems> = emptyList(),
    val categories: List<CategoryUiState> = emptyList(),
    val markets: List<MarketUiState> = emptyList(),
    val coupons: List<CouponUiState> = emptyList(),
    val newProducts: List<NewProductUiState> = emptyList(),
    val lastPurchases: List<LastPurchaseUiState> = emptyList(),
    val discoverProducts: List<DiscoverProductUiState> = emptyList(),
)

data class PagerItems(
    val pagerId: Long = 0L,
    val pagerImage: String = "",
)

data class CategoryUiState(
    val categoryId: Long = 0L,
    val categoryName: String = "",
    val categoryImage: String = "",
)

data class MarketUiState(
    val marketId: Long = 0L,
    val marketName: String = "",
    val marketImage: String = "",
)

data class CouponUiState(
    val couponId: Long = 0L,
    val couponCode: Long = 0L,
    val couponImage: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val couponName: String = "",
    val dealNumber: Int = 0,
    val price: Double = 0.0,
    val offerPriceDouble: Double = 0.0,

    )

data class NewProductUiState(
    val newProductId: Long = 0L,
    val newProductName: String = "",
    val newProductImage: String = "",
    val price: Double = 0.0,
    val isFavorite: Boolean = false,

    )

data class LastPurchaseUiState(
    val lastPurchaseId: Long = 0L,
    val lastPurchaseName: String = "",
    val lastPurchaseImage: String = "",
    val productImages: List<String> = emptyList()
)

data class DiscoverProductUiState(
    val discoverProductId: Long = 0L,
    val discoverProductName: String = "",
    val discoverProductImage: String = "",
    val price: Double = 0.0,
    val isFavorite: Boolean = false,
)