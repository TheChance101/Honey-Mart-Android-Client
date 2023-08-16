package org.the_chance.honeymart.ui.features.products

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
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.EmptyPlaceholder
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honeymart.ui.features.products.composables.ProductCard
import org.the_chance.honymart.ui.composables.HoneyOutlineText
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.blackOn60
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ProductsOnCategory(state: CategoriesUiState) {
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
                    if (state.categories.isNotEmpty()){
                        Text(
                            text = state.categories[state.categoryId.toInt()].categoryName,
                            style = MaterialTheme.typography.bodySmall,
                            color = blackOn60
                        )
                    }

                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HoneyOutlineText(text = state.productsQuantity)
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
            EmptyPlaceholder(state = state.products.isEmpty(), emptyObjectName = "Product")
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                contentPadding = PaddingValues(vertical = MaterialTheme.dimens.space24)
            ) {
                items(state.products.size) { index ->
                    val product = state.products[index]
                    ProductCard(
                        imageUrl = product.productImage,
                        productName = product.productName,
                        productPrice = product.productPrice,
                    )
                }
            }
        }


}