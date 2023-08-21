package org.the_chance.honeymart.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import org.the_chance.honeymart.ui.feature.product.ProductUiState

fun LazyListScope.PagingStateVisibility(products: LazyPagingItems<ProductUiState>) {
    when {
        products.loadState.refresh is LoadState.Loading -> {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(64.dp)
                            .padding(16.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
        products.loadState.append is LoadState.Loading -> {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(64.dp)
                            .padding(16.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
        products.loadState.refresh is LoadState.Error || products.loadState.append is LoadState.Error -> {
            item {
                Text(
                    text = "Error loading data.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}