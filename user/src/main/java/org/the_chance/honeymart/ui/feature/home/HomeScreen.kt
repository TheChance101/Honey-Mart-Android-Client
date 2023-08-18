package org.the_chance.honeymart.ui.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.home.composables.CouponsItem
import org.the_chance.honeymart.ui.feature.home.composables.Hexagon
import org.the_chance.honeymart.ui.feature.home.composables.HomeHorizontalItems
import org.the_chance.honeymart.ui.feature.home.composables.HorizontalPagerIndicator
import org.the_chance.honeymart.ui.feature.home.composables.ItemLabel
import org.the_chance.honeymart.ui.feature.home.composables.LastPurchasesItems
import org.the_chance.honeymart.ui.feature.home.composables.NewProductsItems
import org.the_chance.honeymart.ui.feature.home.composables.SearchBar
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black87
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white30

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState(initialPage = 1)

    HomeContent(
        state = state,
        pagerState = pagerState
    )
//    LaunchedEffect(Unit) {
//        while (true) {
//            delay(3000)
//            pagerState.animateScrollToPage(page = (pagerState.currentPage + 1) % 3)
//        }
//    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    state: HomeUiState,
    pagerState: PagerState
) {

    AppBarScaffold {
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            contentPadding = PaddingValues(bottom = MaterialTheme.dimens.space16),
            columns = GridCells.Fixed(2)
        ) {

            item(
                span = { GridItemSpan(2) },
            ) {
                HorizontalPager(
                    contentPadding = PaddingValues(MaterialTheme.dimens.space12),
                    pageCount = state.markets.size,
                    state = pagerState,
                ) {
                    ImageNetwork(
                        imageUrl = state.markets[it].marketImage,
                        contentDescription = "null",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.dimens.space4)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimens.space24))
                            .height(MaterialTheme.dimens.heightItemMarketCard)
                            .clickable(onClick = {}),
                    )
                }
            }

            item(
                span = { GridItemSpan(2) },
            ) {
                HorizontalPagerIndicator(
                    itemCount = 3,
                    selectedPage = pagerState.currentPage,
                )
            }

            item(
                span = { GridItemSpan(2) },
            ) {
                SearchBar(
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.dimens.space16)
                        .padding(top = MaterialTheme.dimens.space8),
                    icon = painterResource(id = R.drawable.ic_search),
                    onClick = {}
                )
            }

            item(
                span = { GridItemSpan(2) },
            ) {
                ItemLabel(
                    label = stringResource(org.the_chance.user.R.string.markets),
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.dimens.space16)
                        .padding(
                            top =
                            MaterialTheme.dimens.space8
                        )
                )
            }

            item(
                span = { GridItemSpan(2) },
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)
                ) {
                    items(state.markets.size) { itemIndex ->
                        HomeHorizontalItems(
                            name = state.markets[itemIndex].marketName,
                            image = state.markets[itemIndex].marketImage,
                        )
                    }
                }
            }

            item(
                span = { GridItemSpan(2) },
            ) {
                ItemLabel(
                    label = stringResource(org.the_chance.user.R.string.categories),
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.dimens.space16)
                        .padding(
                            top =
                            MaterialTheme.dimens.space8
                        )
                )
            }

            item(
                span = { GridItemSpan(2) },
            ) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)

                ) {
                    items(10) {
                        Hexagon()
                    }
                }
            }

            item(
                span = { GridItemSpan(2) },
            ) {
                LazyRow(
                    contentPadding = PaddingValues(
                        horizontal = MaterialTheme.dimens.space16,
                        vertical = MaterialTheme.dimens.space8
                    )
                )

                {
                    items(state.coupons.size) {
                        CouponsItem(state = state.coupons[it])
                    }
                }
            }

            item(
                span = { GridItemSpan(2) },
            ) {
                Text(
                    text = stringResource(R.string.new_products),
                    style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onSecondary),
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space16)
                )
            }

            item(
                span = { GridItemSpan(2) },
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)
                ) {
                    items(state.newProducts.size) {
                        NewProductsItems(
                            productName = state.newProducts[it].newProductName,
                            productPrice = state.newProducts[it].price.toString(),
                            imageUrl = state.newProducts[it].newProductImage,
                        )
                    }
                }
            }


            item(
                span = { GridItemSpan(2) },
            ) {
                ItemLabel(
                    label = stringResource(R.string.last_purchases),
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.dimens.space16)
                        .padding(
                            top =
                            MaterialTheme.dimens.space8
                        )
                )
            }

            item(
                span = { GridItemSpan(2) },
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)
                ) {
                    items(state.lastPurchases.size) {
                        LastPurchasesItems(
                            image = state.lastPurchases[it].imageUrl,
                            label = state.lastPurchases[it].marketName,
                        )
                    }
                }
            }

            item(
                span = { GridItemSpan(2) },
            ) {
                Text(
                    text = stringResource(R.string.discover_products),
                    style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onSecondary),
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space16)
                )
            }

            items(state.discoverProducts.size) {
                NewProductsItems(
                    productName = state.discoverProducts[it].productName,
                    productPrice = state.discoverProducts[it].productPrice.toString(),
                    imageUrl = state.discoverProducts[it].productImages[0],
                )
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
