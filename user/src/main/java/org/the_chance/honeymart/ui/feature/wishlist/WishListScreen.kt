package org.the_chance.honeymart.ui.feature.wishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.feature.uistate.WishListUiState
import org.the_chance.honymart.ui.composables.ItemFavorite

@Composable
fun WishListScreen(
    viewModel: WishListViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    WishListContent(state = state)


}

@Composable
private fun WishListContent(
    state: WishListUiState,

    ) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(state.products) {
                ItemFavorite(
                    imageUrlMarket =
                    "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg",
                    name = "",
                    price = "$34",
                    description = "1",
                    onClick = {})

            }

        })
}

@Preview(showSystemUi = true)
@Composable
fun PreviewWishListScreen() {
    WishListScreen()

}