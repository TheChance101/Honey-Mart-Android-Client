package org.the_chance.honeymart.ui.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.products.composables.ProductCard
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.HoneyOutlineText
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.blackOn60
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel()
) {
    // val state by viewModel.state.collectAsState()
    val products = listOf(
        ProductUiState(
            productName = "Apple",
            productPrice = 25.0,
            productId = 0,
            productImage = "https://img.freepik.com/free-photo/colorful-fruits-tasty-fresh-ripe-juicy-white-desk_179666-169.jpg",
            productQuantity = 5
        ),
        ProductUiState(
            productName = "Apple",
            productPrice = 25.0,
            productId = 0,
            productImage = "https://img.freepik.com/free-photo/colorful-fruits-tasty-fresh-ripe-juicy-white-desk_179666-169.jpg",
            productQuantity = 5
        ),
        ProductUiState(
            productName = "Apple",
            productPrice = 25.0,
            productId = 0,
            productImage = "https://img.freepik.com/free-photo/colorful-fruits-tasty-fresh-ripe-juicy-white-desk_179666-169.jpg",
            productQuantity = 5
        ),
        ProductUiState(
            productName = "Apple",
            productPrice = 25.0,
            productId = 0,
            productImage = "https://img.freepik.com/free-photo/colorful-fruits-tasty-fresh-ripe-juicy-white-desk_179666-169.jpg",
            productQuantity = 5
        ), ProductUiState(
            productName = "Apple",
            productPrice = 25.0,
            productId = 0,
            productImage = "https://img.freepik.com/free-photo/colorful-fruits-tasty-fresh-ripe-juicy-white-desk_179666-169.jpg",
            productQuantity = 5
        ), ProductUiState(
            productName = "Apple",
            productPrice = 25.0,
            productId = 0,
            productImage = "https://img.freepik.com/free-photo/colorful-fruits-tasty-fresh-ripe-juicy-white-desk_179666-169.jpg",
            productQuantity = 5
        ), ProductUiState(
            productName = "Apple",
            productPrice = 25.0,
            productId = 0,
            productImage = "https://img.freepik.com/free-photo/colorful-fruits-tasty-fresh-ripe-juicy-white-desk_179666-169.jpg",
            productQuantity = 5
        ),
        ProductUiState(
            productName = "Apple",
            productPrice = 25.0,
            productId = 0,
            productImage = "https://img.freepik.com/free-photo/colorful-fruits-tasty-fresh-ripe-juicy-white-desk_179666-169.jpg",
            productQuantity = 5
        ),
        ProductUiState(
            productName = "Apple",
            productPrice = 25.0,
            productId = 0,
            productImage = "https://img.freepik.com/free-photo/colorful-fruits-tasty-fresh-ripe-juicy-white-desk_179666-169.jpg",
            productQuantity = 5
        ),
        ProductUiState(
            productName = "Apple",
            productPrice = 25.0,
            productId = 0,
            productImage = "https://img.freepik.com/free-photo/colorful-fruits-tasty-fresh-ripe-juicy-white-desk_179666-169.jpg",
            productQuantity = 5
        ),
        ProductUiState(
            productName = "Apple",
            productPrice = 25.0,
            productId = 0,
            productImage = "https://img.freepik.com/free-photo/colorful-fruits-tasty-fresh-ripe-juicy-white-desk_179666-169.jpg",
            productQuantity = 5
        )
    )
    val state = ProductsUiState(
        products = products,
        category = CategoryUiState(R.drawable.icon_category, "Bedroom")
    )
    ProductsContent(state)
}

@Composable
fun ProductsContent(state: ProductsUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = MaterialTheme.dimens.space24,
                start = MaterialTheme.dimens.space16,
                end = MaterialTheme.dimens.space16
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(MaterialTheme.dimens.icon48),
                    painter = painterResource(id = R.drawable.icon_category),
                    contentDescription = "category icon",
                    tint = black37
                )
                Text(
                    text = "Bedroom",
                    style = MaterialTheme.typography.bodySmall,
                    color = blackOn60
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HoneyOutlineText(text = state.products.size.toString() + " Products")
                IconButton(
                    modifier = Modifier.size(MaterialTheme.dimens.icon24),
                    onClick = { /*TODO*/ },
                    backgroundColor = Color.Transparent
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_options),
                        contentDescription = "options icon",
                    )
                }
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
            contentPadding = PaddingValues(vertical = MaterialTheme.dimens.space24)
        ) {
            items(state.products.size) { index ->
                val product = state.products[index]
                ProductCard(
                    imageUrl = product.productImage,
                    productName = product.productName,
                    productPrice = product.productPrice.toString() + "$",
                    productQuantity = product.productQuantity.toString()
                )
            }
        }
    }
}

@Preview(name = "tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun PreviewProducts() {
    HoneyMartTheme {
        ProductsContent(state = ProductsUiState())
    }
}


