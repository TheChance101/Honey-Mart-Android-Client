package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.PagingLoading
import org.the_chance.honeymart.ui.composables.ProductItem
import org.the_chance.honeymart.ui.feature.see_all_markets.MarketUiState
import org.the_chance.honeymart.ui.feature.coupons.CouponUiState
import org.the_chance.honeymart.ui.feature.home.HomeInteractionListener
import org.the_chance.honeymart.ui.feature.home.HomeUiState
import org.the_chance.honeymart.ui.feature.home.composables.coupon.CouponsItem
import org.the_chance.honeymart.ui.feature.home.formatCurrencyWithNearestFraction
import org.the_chance.honeymart.ui.feature.marketInfo.CategoryUiState
import org.the_chance.honeymart.ui.feature.new_products.RecentProductUiState
import org.the_chance.honeymart.ui.feature.orders.OrderUiState
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.composables.categoryIcons
import org.the_chance.honymart.ui.theme.black16
import org.the_chance.honymart.ui.theme.dimens

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun HomeContentSuccessScreen(
    state: HomeUiState,
    pagerState: PagerState,
    listener: HomeInteractionListener
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        contentPadding = PaddingValues(bottom = MaterialTheme.dimens.space16),
        columns = GridCells.Fixed(2)
    ) {
        item(span = { GridItemSpan(2) })
        {
            MarketsPager(
                markets = state.shuffledMarket,
                pagerState = pagerState,
                onClickPagerItem = listener::onClickPagerItem
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
                onClick = listener::onClickSearchBar
            )
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            Markets(
                markets = state.markets,
                onClickMarket = listener::onClickPagerItem,
                onClickSeeAll = listener::onClickSeeAllMarkets
            )
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            Categories(
                isCategoryLoading = state.isCategoryLoading,
                markets = state.markets,
                categories = state.categories,
                selectedMarketId = state.selectedMarketId,
                onChipClick = listener::onClickChipCategory,
                oncClickCategory = listener::onClickCategory
            )
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            Coupons(
                coupons = state.coupons,
                onClickCoupon = listener::onClickGetCoupon
            )
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            RecentProducts(
                recentProducts = state.recentProducts,
                onClickRecentProduct = listener::onClickProductItem,
                onClickSeeAll = listener::onClickSeeAllNewProducts
            )
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            LastPurchases(
                lastPurchases = state.lastPurchases,
                onClickProduct = listener::onClickLastPurchases,
                onClickSeeAll = listener::onClickLastPurchasesSeeAll
            )
        }

        item(span = { GridItemSpan(2) }) {
            AnimatedVisibility(
                visible = state.discoverProducts.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = stringResource(R.string.discover_products),
                    style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onSecondary),
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space16)
                )
            }
        }

        itemsIndexed(
            items = state.discoverProducts,
            key = { _, item -> item.productId }) { index, discoverProduct ->
            listener.onChangeProductsScrollPosition(index)
            ProductItem(
                modifier = if (index % 2 == 0) Modifier.padding(start = MaterialTheme.dimens.space16)
                else Modifier.padding(end = MaterialTheme.dimens.space16),
                productName = discoverProduct.productName,
                productPrice = discoverProduct.priceInCurrency,
                imageUrl = discoverProduct.imageUrl,
                onClick = { listener.onClickProductItem(discoverProduct.productId) },
            )
        }
        item(span = { GridItemSpan(2) }) {
            PagingLoading(state = state.isPagingLoading && state.discoverProducts.isNotEmpty())
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LastPurchases(
    lastPurchases: List<OrderUiState>,
    onClickProduct: (Long) -> Unit,
    onClickSeeAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = lastPurchases.isNotEmpty(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        ) {
            ItemLabel(
                label = stringResource(R.string.last_purchases),
                modifier = modifier
                    .padding(horizontal = MaterialTheme.dimens.space16)
                    .padding(
                        top =
                        MaterialTheme.dimens.space8
                    ),
                onClick = onClickSeeAll
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)
            ) {
                items(items = lastPurchases, key = { it.orderId }) { lastPurchase ->
                    LastPurchasesItems(
                        modifier = Modifier.animateItemPlacement(),
                        image = lastPurchase.imageUrl,
                        label = lastPurchase.marketName,
                        onClick = { onClickProduct(lastPurchase.orderId) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RecentProducts(
    recentProducts: List<RecentProductUiState>,
    onClickRecentProduct: (Long) -> Unit,
    onClickSeeAll: () -> Unit
) {
    AnimatedVisibility(
        visible = recentProducts.isNotEmpty(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        ) {
            ItemLabel(
                label = stringResource(R.string.new_products),
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.dimens.space16)
                    .padding(top = MaterialTheme.dimens.space8),
                onClick = onClickSeeAll
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)
            ) {
                items(items = recentProducts, key = { it.productId }) { recentProduct ->
                    ProductItem(
                        modifier = Modifier.animateItemPlacement(),
                        productName = recentProduct.productName,
                        productPrice = recentProduct.price.formatCurrencyWithNearestFraction(),
                        imageUrl = recentProduct.productImage,
                        onClick = { onClickRecentProduct(recentProduct.productId) }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Coupons(
    coupons: List<CouponUiState>,
    onClickCoupon: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.dimens.space16,
            vertical = MaterialTheme.dimens.space8
        )
    )
    {
        items(items = coupons, key = { it.couponId }) { coupon ->
            CouponsItem(
                modifier = Modifier.animateItemPlacement(),
                coupon = coupon,
                onClickGetCoupon = { onClickCoupon(coupon.couponId) })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Categories(
    isCategoryLoading: Boolean,
    categories: List<CategoryUiState>,
    markets: List<MarketUiState>,
    selectedMarketId: Long,
    onChipClick: (Long) -> Unit,
    oncClickCategory: (Long, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = markets.isNotEmpty(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
        ) {
            Text(
                text = stringResource(R.string.categories),
                style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onSecondary),
                modifier = modifier
                    .padding(horizontal = MaterialTheme.dimens.space16)
                    .padding(top = MaterialTheme.dimens.space8)
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            ) {
                items(items = markets) { market ->
                    CustomChip(
                        state = selectedMarketId == market.marketId,
                        text = market.marketName,
                        style = MaterialTheme.typography.displayLarge,
                        onClick = { onChipClick(market.marketId) })
                }
            }
            Box {
                Loading(
                    state = isCategoryLoading,
                    modifier = Modifier.height(MaterialTheme.dimens.widthItemMarketCard)
                )
                this@Column.AnimatedVisibility(
                    visible = !isCategoryLoading,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    ) {
                        itemsIndexed(
                            categories,
                            key = { _, category -> category.categoryId }
                        ) { index, category ->
                            HomeCategoriesItem(
                                modifier = Modifier
                                    .padding(horizontal = MaterialTheme.dimens.space8)
                                    .animateItemPlacement(),
                                label = category.categoryName,
                                onClick = { oncClickCategory(category.categoryId, index) },
                                painter = painterResource(
                                    id = categoryIcons[category.categoryImageId]
                                        ?: R.drawable.ic_cup_paper
                                ),
                            )
                        }
                    }
                }
                this@Column.AnimatedVisibility(
                    visible = categories.isEmpty() && isCategoryLoading.not(),
                    enter = fadeIn(), exit = fadeOut(),
                ) {
                    Text(
                        text = stringResource(R.string.no_categories),
                        modifier = Modifier
                            .height(66.dp)
                            .fillMaxWidth()
                            .padding(top = MaterialTheme.dimens.space16)
                            .padding(MaterialTheme.dimens.space16),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Markets(
    markets: List<MarketUiState>,
    onClickMarket: (Long) -> Unit,
    onClickSeeAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
    ) {
        ItemLabel(
            label = stringResource(R.string.markets),
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.space16)
                .padding(top = MaterialTheme.dimens.space8),
            onClick = onClickSeeAll
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)
        ) {
            items(items = markets, key = { it.marketId }) { market ->
                HomeMarketItem(
                    modifier = Modifier.animateItemPlacement(),
                    name = market.marketName,
                    image = market.marketImage,
                    onClick = { onClickMarket(market.marketId) }
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun MarketsPager(
    markets: List<MarketUiState>,
    pagerState: PagerState,
    onClickPagerItem: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        HorizontalPager(
            contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space12),
            pageCount = markets.size,
            state = pagerState,
        ) {
            ImageNetwork(
                imageUrl = markets[it].marketImage,
                contentDescription = stringResource(id = R.string.market_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.space4)
                    .clip(shape = RoundedCornerShape(MaterialTheme.dimens.space24))
                    .background(black16)
                    .height(MaterialTheme.dimens.heightItemMarketCard)
                    .clickable(onClick = { onClickPagerItem(markets[it].marketId) }),
            )
        }
        HorizontalPagerIndicator(
            itemCount = if (markets.size > 3) 3 else markets.size,
            selectedPage = pagerState.currentPage,
        )
    }
}