package org.the_chance.honeymart.ui.feature.order_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.feature.uistate.OrderDetailsUiState
import org.the_chance.honymart.ui.composables.OrderDetailsCard
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun OrderDetailsScreen(
    viewModel: OrderDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    OrderDetailsContent(
        state = state,
    )
}

@Composable
private fun OrderDetailsContent(
    state: OrderDetailsUiState,
) {
    Column() {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(MaterialTheme.dimens.space16),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
            state = rememberLazyGridState(),
            content = {
                items(state.products){item ->
                    OrderDetailsCard(
                        imageUrl = state.products.,
                        orderName = ,
                        orderPrice = "$34",
                        orderCount = "1"
                    )
                }

            }
        )
    }
}