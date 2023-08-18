package org.the_chance.honeymart.ui.features.products

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.addCategory.composable.HoneyMartTitle
import org.the_chance.honeymart.ui.composables.EmptyPlaceholder
import org.the_chance.honeymart.ui.features.add_product.components.AddProductForm
import org.the_chance.honeymart.ui.features.products.composables.ProductsOnProducts
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    ProductsContent(state, viewModel)
}

@Composable
fun ProductsContent(
    state: ProductsUiState,
    listener: ProductsInteractionsListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        HoneyMartTitle()

        Loading(state = state.isLoading && state.products.isEmpty())
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {

                EmptyPlaceholder(
                    state = state.isEmptyProducts &&
                            !state.isLoading && !state.isError, emptyObjectName = "Product"
                )

                AnimatedVisibility(
                    visible = true,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = MaterialTheme.dimens.space12)
                    ) {
                        ProductsOnProducts(state = state, listener = listener)
                    }
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                AddProductForm(state = state, listener = listener)
            }
        }
    }

//    ContentVisibility(state = state.contentScreen()) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(
//                    top = MaterialTheme.dimens.space24,
//                    start = MaterialTheme.dimens.space16,
//                    end = MaterialTheme.dimens.space16
//                )
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        modifier = Modifier.size(MaterialTheme.dimens.icon48),
//                        painter = painterResource(id = R.drawable.icon_category),
//                        contentDescription = "category icon",
//                        tint = black37
//                    )
//                    Text(
//                        text = state.category.categoryName,
//                        style = MaterialTheme.typography.bodySmall,
//                        color = blackOn60
//                    )
//                }
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    HoneyOutlineText(text = state.productsQuantity)
//                    IconButton(
//                        modifier = Modifier.size(MaterialTheme.dimens.icon24),
//                        onClick = { /*TODO*/ },
//                        backgroundColor = Color.Transparent
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_options),
//                            contentDescription = "options icon",
//                        )
//                    }
//                }
//            }
//            EmptyPlaceholder(state = state.isEmptyProducts, emptyObjectName = "Product")
//            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
//                contentPadding = PaddingValues(vertical = MaterialTheme.dimens.space24)
//            ) {
//                items(state.products.size) { index ->
//                    val product = state.products[index]
//                    ProductCard(
//                        imageUrl = product.productImage,
//                        productName = product.productName,
//                        productPrice = product.productPrice,
//                    )
//                }
//            }
//        }
//    }

}

@Preview(name = "tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun PreviewProducts() {
    HoneyMartTheme {
//        ProductsContent(state = ProductsUiState())
    }
}


