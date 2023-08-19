package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.delay
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.home.HomeInteractionListener
import org.the_chance.honeymart.ui.feature.home.HomeUiState
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.dimens

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun HomeContentSuccessScreen(
    state: HomeUiState,
    pagerState: PagerState,
    listener: HomeInteractionListener
) {

    LazyVerticalGrid(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        contentPadding = PaddingValues(bottom = MaterialTheme.dimens.space16),
        columns = GridCells.Fixed(2)
    ) {

        item(
            span = { GridItemSpan(2) },
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                horizontalAlignment = Alignment.CenterHorizontally,
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
                            .clickable(onClick = { listener.onClickPagerItem(state.markets[it].marketId) }),
                    )
                }
                HorizontalPagerIndicator(
                    itemCount = 3,
                    selectedPage = pagerState.currentPage,
                )
            }
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            SearchBar(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.dimens.space16)
                    .padding(top = MaterialTheme.dimens.space8),
                icon = painterResource(id = R.drawable.ic_search),
                onClick = listener::onClickSearchBar
            )
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            ) {
                ItemLabel(
                    label = stringResource(R.string.markets),
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.dimens.space16)
                        .padding(top = MaterialTheme.dimens.space8)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)
                ) {
                    items(state.markets.size) { itemIndex ->
                        HomeMarketItem(
                            name = state.markets[itemIndex].marketName,
                            image = state.markets[itemIndex].marketImage,
                            onclick = { listener.onClickPagerItem(state.markets[itemIndex].marketId) }
                        )
                    }
                }
            }

        }

        item(
            span = { GridItemSpan(2) },
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            ) {
                ItemLabel(
                    label = stringResource(R.string.categories),
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.dimens.space16)
                        .padding(
                            top =
                            MaterialTheme.dimens.space8
                        )
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)

                ) {
                    items(10) {
                        Hexagon()
                    }
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
                    CouponsItem(
                        state = state.coupons[it],
                        onClick = { listener.onClickCouponClipped(state.coupons[it].couponId) })
                }
            }
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            ) {
                Text(
                    text = stringResource(R.string.new_products),
                    style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onSecondary),
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space16)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)
                ) {
                    items(state.newProducts.size) {
                        NewProductsItems(
                            productName = state.newProducts[it].newProductName,
                            productPrice = state.newProducts[it].price.toString(),
                            imageUrl = state.newProducts[it].newProductImage,
                            onClickFavorite = { listener.onClickFavoriteNewProduct(state.newProducts[it].newProductId) },
                            isFavoriteIconClicked = state.newProducts[it].isFavorite,
                            onClick = { listener.onClickProductItem(state.newProducts[it].newProductId) }
                        )
                    }
                }
            }

        }

        item(
            span = { GridItemSpan(2) },
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
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
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)
                ) {
                    items(state.lastPurchases.size) {
                        LastPurchasesItems(
                            image = state.lastPurchases[it].imageUrl,
                            label = state.lastPurchases[it].marketName,
                            onClick = { listener.onClickProductItem(state.lastPurchases[it].orderId) }

                        )
                    }
                }
            }
        }

        item(span = { GridItemSpan(2) }) {
            Text(
                text = stringResource(R.string.discover_products),
                style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onSecondary),
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space16)
            )
        }

        items(state.discoverProducts.size) {
            NewProductsItems(
                modifier = if (it % 2 == 0) Modifier.padding(start = MaterialTheme.dimens.space16)
                else Modifier.padding(end = MaterialTheme.dimens.space16),
                productName = state.discoverProducts[it].productName,
                productPrice = state.discoverProducts[it].productPrice.toString(),
                imageUrl = state.discoverProducts[it].productImages[0],
                onClickFavorite = { listener.onClickFavoriteDiscoverProduct(state.discoverProducts[it].productId) },
                onClick = { listener.onClickProductItem(state.discoverProducts[it].productId) },
                isFavoriteIconClicked = state.discoverProducts[it].isFavorite
            )
        }
    }
    LaunchedEffect(key1 = state.markets.isNotEmpty()) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(page = (pagerState.currentPage + 1) % 3)

        }
    }
}