package org.the_chance.honeymart.ui.feature.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.EmptyCategoriesPlaceholder
import org.the_chance.honeymart.ui.feature.category.composables.CardChip
import org.the_chance.honeymart.ui.feature.category.composables.CategoriesAppBarScaffold
import org.the_chance.honeymart.ui.feature.category.composables.HexagonItem
import org.the_chance.honeymart.ui.feature.product.navigateToProductScreen
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CategoriesScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current

    CategoriesAppBarScaffold(navController) {
        CategoryContent(state, listener = viewModel)
    }
    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                is CategoryUiEffect.ClickCategoryEffect -> {
                    navController.navigateToProductScreen(
                        it.categoryId,
                        it.marketId,
                        it.position
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryContent(
    state: MarketDetailsUiState,
    listener: CategoryInteractionListener,
) {
    Loading(state.isLoading)

    ConnectionErrorPlaceholder(state.isError, listener::onGetData)

    ContentVisibility(state.showLazyCondition()) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(
                bottom = MaterialTheme.dimens.space56,
                end = MaterialTheme.dimens.space16,
                start = MaterialTheme.dimens.space16,
            ),
        ) {
            item(span = { GridItemSpan(3) }) {
                Column(
                    modifier = Modifier.padding(
                        bottom = MaterialTheme.dimens.space16
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = state.marketName,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(
                            top = MaterialTheme.dimens.space16,
                            bottom = MaterialTheme.dimens.space8
                        )
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space4)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.map_pointer),
                            contentDescription = stringResource(R.string.map_pointer),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        )
                        Text(
                            text = state.address,
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Row(
                        modifier = Modifier.padding(vertical = MaterialTheme.dimens.space16),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
                    ) {
                        CardChip(
                            text = "${state.categoriesCount} Categories",
                            icon = painterResource(id = R.drawable.boxes)
                        )
                        CardChip(
                            text = "${state.productsCount} Items",
                            icon = painterResource(id = R.drawable.box_minimalistic)
                        )
                    }
                    ImageNetwork(
                        imageUrl = state.imageUrl,
                        contentDescription = stringResource(id = R.string.category_image),
                        modifier = Modifier
                            .height(198.dp)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimens.space24))
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
            itemsIndexed(state.categories) { index, item ->
                HexagonItem(
                    item,
                    listener::onClickCategory,
                    index,
                )
            }
            item(span = { GridItemSpan(3) }) {
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.space16))
                EmptyCategoriesPlaceholder(state.categories.isEmpty())
            }
        }

    }
}


@Composable
@Preview
fun PreviewCategoryScreen() {
    CategoriesScreen()
}