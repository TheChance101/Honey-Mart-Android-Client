package org.the_chance.honeymart.ui.feature.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.feature.uistate.ProductsUiState
import org.the_chance.honymart.ui.composables.ProductCard
import org.the_chance.honymart.ui.composables.SideBarItem
import org.the_chance.user.R

@Composable
fun ProductsScreen(
    viewModel: ProductViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    ProductsContent(state = state)
}

@Composable
private fun ProductsContent(state: ProductsUiState) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(top = 24.dp, end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(10) {
                SideBarItem(icon = org.the_chance.design_system.R.drawable.ic_bed)
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(10) {
                ProductCard(
                    imageUrl = "https://housing.com/news/wp-content/uploads/2022/11/living-room-furniture-design-compressed-1.jpg",
                    furnitureName = "Sofa",
                    furniturePrice = "30,000",
                    secondaryText = "Secondary text"
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewProducts() {
    ProductsScreen()
}