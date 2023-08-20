package org.the_chance.honeymart.ui.features.product_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.features.category.CategoriesInteractionsListener
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honeymart.ui.features.category.composable.HoneyMartTitle
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
//    ProductDetailsContent(state, viewModel)
}

@Composable
fun ProductDetailsContent(
    state: CategoriesUiState,
    listener: CategoriesInteractionsListener
) {

    ContentVisibility(state = !state.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            HoneyMartTitle()

            Loading(state = state.isLoading)
            Row(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(horizontal = MaterialTheme.dimens.space12)
                ) {
//                    ProductsOnProducts(state = state, listener = listener)
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
//                    ProductDetailsForm(state = state, listener = listener)
                }
            }
        }
    }
}


//@Preview(name = "tablet", device = Devices.TABLET, showSystemUi = true)
//@Composable
//fun PreviewProducts() {
//    HoneyMartTheme {
//        ProductDetailsContent(state = ProductsUiState(), listener = ProductDetailsViewModel)
//    }
//}