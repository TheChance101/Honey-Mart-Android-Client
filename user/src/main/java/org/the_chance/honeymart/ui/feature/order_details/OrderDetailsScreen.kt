package org.the_chance.honeymart.ui.feature.order_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.the_chance.honeymart.ui.feature.uistate.OrderDetailsUiState
import org.the_chance.honymart.ui.composables.OrderDetailsCard
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun OrderDetailsScreen(
//    viewModel: OrderDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    OrderDetailsContent(
        state = state,
        viewModel = viewModel,
    )
}

@Composable
private fun OrderDetailsContent(
    state: OrderDetailsUiState?,
    viewModel: OrderDetailsViewModel,
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
                itemsIndexed(state!!.products) { index, item ->
                    OrderDetailsCard(
                        imageUrl = "https://img.freepik.com/free-photo/mid-century-modern-living-room-interior-design-with-monstera-tree_53876-129804.jpg",
                        orderName = ,
                        orderPrice = "$34",
                        orderCount = "1"
                    )
                }

            }
        )
    }
}