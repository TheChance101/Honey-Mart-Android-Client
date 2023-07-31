package org.the_chance.honeymart.ui.feature.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.cart.composables.ErrorPlaceHolder
import org.the_chance.honeymart.ui.feature.product.navigateToProductScreen
import org.the_chance.honymart.ui.composables.Loading

/**
 * Created by Aziza Helmy on 7/27/2023.
 */
@Composable
fun CategoriesScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current
    CategoryContent(state, listener = navController::navigateToProductScreen)
}

@Composable
fun CategoryContent(
    state: CategoriesUiState,
    listener: (categoryId: Long , marketId: Long , position:Int) -> Unit,
) {
    when {
        state.isLoading -> Loading()
        state.isError -> ErrorPlaceHolder()
        else ->
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 100.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(state.categories) { index, item ->
                    CategoryItem(
                        state = item,
                        onCategoryClicked =  listener ,
                        categoryId = item.categoryId,
                        marketId = state.marketId,
                        position = index,
                    )
                }
            }
    }
}


@Composable
@Preview
fun PreviewCategoryScreen() {
    CategoriesScreen()
}