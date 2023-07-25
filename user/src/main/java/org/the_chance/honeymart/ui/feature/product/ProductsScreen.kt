package org.the_chance.honeymart.ui.feature.product

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.uistate.CategoryUiState
import org.the_chance.honeymart.ui.feature.uistate.ProductUiState
import org.the_chance.honeymart.ui.feature.uistate.ProductsUiState
import org.the_chance.honymart.ui.composables.ProductCard
import org.the_chance.honymart.ui.composables.SideBarItem


@Composable
fun ProductsScreen(
    viewModel: ProductViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()


    val sampleState = ProductsUiState(
        isLoadingCategory = false,
        isLoadingProduct = false,
        isError = false,
        products = listOf(
            ProductUiState(
                productId = 1L,
                productName = "Sofa",
                productDescription = "Comfy sofa for your living room",
                productPrice = 30000.0,
                productImages = listOf("https://housing.com/news/wp-content/uploads/2022/11/living-room-furniture-design-compressed-1.jpg")
            ),
            ProductUiState(
                productId = 1L,
                productName = "Sofa",
                productDescription = "Comfy sofa for your living room",
                productPrice = 30000.0,
                productImages = listOf("https://housing.com/news/wp-content/uploads/2022/11/living-room-furniture-design-compressed-1.jpg")
            ),
            ProductUiState(
                productId = 1L,
                productName = "Sofa",
                productDescription = "Comfy sofa for your living room",
                productPrice = 30000.0,
                productImages = listOf("https://housing.com/news/wp-content/uploads/2022/11/living-room-furniture-design-compressed-1.jpg")
            ),
            ProductUiState(
                productId = 1L,
                productName = "Sofa",
                productDescription = "Comfy sofa for your living room",
                productPrice = 30000.0,
                productImages = listOf("https://housing.com/news/wp-content/uploads/2022/11/living-room-furniture-design-compressed-1.jpg")
            )
        ),
        categories = listOf(
            CategoryUiState(
                categoryImageId = R.drawable.ic_bed,
                categoryName = "Bedroom",
                isCategorySelected = true
            ),
            CategoryUiState(
                categoryImageId = R.drawable.ic_bed,
                categoryName = "Kitchen \n Tools",
                isCategorySelected = false
            ),
            CategoryUiState(
                categoryImageId = R.drawable.ic_bed,
                categoryName = "Bedroom",
                isCategorySelected = false
            ),
            CategoryUiState(
                categoryImageId = R.drawable.ic_bed,
                categoryName = "Bedroom",
                isCategorySelected = false
            ),
            CategoryUiState(
                categoryImageId = R.drawable.ic_bed,
                categoryName = "Bedroom",
                isCategorySelected = false
            ),
            CategoryUiState(
                categoryImageId = R.drawable.ic_bed,
                categoryName = "Bedroom",
                isCategorySelected = false
            ),
            CategoryUiState(
                categoryImageId = R.drawable.ic_bed,
                categoryName = "Bedroom",
                isCategorySelected = false
            ),
            CategoryUiState(
                categoryImageId = R.drawable.ic_bed,
                categoryName = "Bedroom",
                isCategorySelected = false
            ),
            CategoryUiState(
                categoryImageId = R.drawable.ic_bed,
                categoryName = "Bedroom",
                isCategorySelected = false
            ),
            CategoryUiState(
                categoryImageId = R.drawable.ic_bed,
                categoryName = "Bedroom",
                isCategorySelected = false
            ),
            CategoryUiState(
                categoryImageId = R.drawable.ic_bed,
                categoryName = "Bedroom",
                isCategorySelected = false
            ),
        )
    )

    ProductsContent(state = sampleState)
}

@Composable
private fun ProductsContent(state: ProductsUiState) {
    if (!state.isLoadingCategory && !state.isLoadingProduct) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.Start,
        ) {
            LazyColumn(
                contentPadding = PaddingValues(top = 24.dp, end = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.categories.size) { index ->
                    val category = state.categories[index]
                    SideBarItem(
                        icon = category.categoryImageId!!,
                        categoryName = category.categoryName!!,
                        isSelected = category.isCategorySelected
                    )  // Use the appropriate icon property from CategoryUiState
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.products.size) { index ->
                    val product = state.products[index]
                    ProductCard(
                        imageUrl = product.productImages?.firstOrNull() ?: "",
                        furnitureName = product.productName ?: "",
                        furniturePrice = product.productPrice?.toString() ?: "",
                        secondaryText = product.productDescription ?: ""
                    )
                }
            }
        }
    }
    LaunchedEffect(key1 = state.isLoadingProduct || state.isLoadingCategory) {
        Log.d("Tarek", "${state.isLoadingProduct} ${state.isLoadingCategory}")
    }
}

@Preview
@Composable
fun PreviewProducts() {
    //  ProductsContent(state = sampleState)
}
