package org.the_chance.honeymart.ui.feature.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
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
import org.the_chance.honeymart.ui.feature.category.composables.CardChip
import org.the_chance.honeymart.ui.feature.category.composables.CategoriesScreenTopBar
import org.the_chance.honeymart.ui.feature.category.composables.CategoryItem
import org.the_chance.honeymart.ui.feature.category.composables.HexagonItem
import org.the_chance.honeymart.ui.feature.product.navigateToProductScreen
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                is CategoryUiEffect.ClickCategoryEffect -> navController.navigateToProductScreen(
                    it.categoryId,
                    it.marketId,
                    it.position
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(), topBar = {
            TopAppBar(
                { CategoriesScreenTopBar(onClickBack = { navController.navigateUp() }) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            CategoryContent(state, listener = viewModel)
        }
    }
}


@Composable
fun CategoryContent(
    state: CategoriesUiState,
    listener: CategoryInteractionListener,
) {
    Loading(state.isLoading)

    ConnectionErrorPlaceholder(state.isError, listener::onGetData)

    ContentVisibility(state.showLazyCondition()) {
        Column(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.space16),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Contemporary Touli Market",
                style = MaterialTheme.typography.headlineLarge,
                color = colorResource(id = R.color.black_87),
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
                    contentDescription = stringResource(id = R.string.map_pointer),
                    tint = colorResource(id = R.color.black_37),
                )
                Text(
                    text = "Knowledge St. Modern Touli District",
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(id = R.color.black_37),
                    textAlign = TextAlign.Center,
                )
            }
            Row(
                modifier = Modifier.padding(vertical = MaterialTheme.dimens.space16),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
            ) {
                CardChip(text = "16 Categories", icon = painterResource(id = R.drawable.boxes))
                CardChip(
                    text = "124 Items",
                    icon = painterResource(id = R.drawable.box_minimalistic)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.market),
                contentDescription = stringResource(id = R.string.category_image),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(MaterialTheme.dimens.space24))
                    .height(198.dp)
                    .fillMaxWidth(), contentScale = ContentScale.FillBounds
            )
            LazyVerticalGrid(
                state = rememberLazyGridState(),
                columns = GridCells.Fixed(count = 3),
                contentPadding = PaddingValues(MaterialTheme.dimens.space2),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space2),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space2),
                modifier = Modifier.fillMaxWidth(),
            ) {
                itemsIndexed(state.categories) { index, item ->
                    HexagonItem(
                        item,
                        listener::onClickCategory,
                        index,
                    )
                }
            }
        }
    }
}


@Composable
@Preview
fun PreviewCategoryScreen() {
    CategoriesScreen()
}