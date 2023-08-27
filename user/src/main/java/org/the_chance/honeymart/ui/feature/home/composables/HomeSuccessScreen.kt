package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.category.CategoryUiState
import org.the_chance.honeymart.ui.feature.home.CouponUiState
import org.the_chance.honeymart.ui.feature.home.HomeInteractionListener
import org.the_chance.honeymart.ui.feature.home.HomeUiState
import org.the_chance.honeymart.ui.feature.home.MarketUiState
import org.the_chance.honeymart.ui.feature.new_products.RecentProductUiState
import org.the_chance.honeymart.ui.feature.home.composables.coupon.CouponsItem
import org.the_chance.honeymart.ui.feature.home.formatCurrencyWithNearestFraction
import org.the_chance.honeymart.ui.feature.orders.OrderUiState
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.black16
import org.the_chance.honymart.ui.theme.dimens

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun HomeContentSuccessScreen(
    state: HomeUiState,
    pagerState: PagerState,
    listener: HomeInteractionListener
) {

    val listState = rememberLazyGridState()
    val scope = rememberCoroutineScope()

    LazyVerticalGrid(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        contentPadding = PaddingValues(bottom = MaterialTheme.dimens.space16),
        columns = GridCells.Fixed(2)
    ) {
        item(span = { GridItemSpan(2) })
        {
            MarketsPager(
                markets = state.markets,
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
                onClickSeeAll = {}
            )
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            Categories(
                markets = state.markets,
                categories = state.categories,
                selectedMarketId = state.selectedMarketId,
                onChipClick = listener::onClickChipCategory,
                onClickSeeAll = {},
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
                onClickFavorite = listener::onClickFavoriteNewProduct,
                onClickSeeAll = listener::onClickSeeAllNewProducts
            )
        }

        item(
            span = { GridItemSpan(2) },
        ) {
            LastPurchases(
                lastPurchases = state.lastPurchases,
                onClickProduct = listener::onClickProductItem,
                onClickSeeAll = {}
            )
        }

        item(span = { GridItemSpan(2) }) {
            AnimatedVisibility(visible = state.discoverProducts.isNotEmpty()) {
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
            ProductItem(
                modifier = if (index % 2 == 0) Modifier.padding(start = MaterialTheme.dimens.space16)
                else Modifier.padding(end = MaterialTheme.dimens.space16),
                productName = discoverProduct.productName,
                productPrice = discoverProduct.productPrice.formatCurrencyWithNearestFraction(),
                imageUrl = discoverProduct.productImages.takeIf { it.isNotEmpty() }
                    ?.get(0) ?: "",
                onClickFavorite = { listener.onClickFavoriteDiscoverProduct(discoverProduct.productId) },
                onClick = { listener.onClickProductItem(discoverProduct.productId) },
                isFavoriteIconClicked = discoverProduct.isFavorite
            )
        }
    }

    AnimatedVisibility(visible = !listState.isScrollingUp(), enter = fadeIn(), exit = fadeOut()) {
        ScrollToTopButton {
            scope.launch {
                listState.animateScrollToItem(0)
            }
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
    AnimatedVisibility(visible = lastPurchases.isNotEmpty()) {
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
    onClickSeeAll: () -> Unit,
    onClickFavorite: (Long) -> Unit,
) {
    AnimatedVisibility(visible = recentProducts.isNotEmpty()) {
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
                        onClickFavorite = { onClickFavorite(recentProduct.productId) },
                        isFavoriteIconClicked = recentProduct.isFavorite,
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
    categories: List<CategoryUiState>,
    markets: List<MarketUiState>,
    selectedMarketId: Long,
    onChipClick: (Long) -> Unit,
    onClickSeeAll: () -> Unit,
    oncClickCategory: (Long, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(visible = markets.isNotEmpty()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
        ) {
            ItemLabel(
                label = stringResource(R.string.categories),
                modifier = modifier
                    .padding(horizontal = MaterialTheme.dimens.space16)
                    .padding(top = MaterialTheme.dimens.space8),
                onClick = onClickSeeAll
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            ) {
                items(items = markets) { market ->
                    CustomChip(
                        state = selectedMarketId == market.marketId,
                        text = market.marketName,
                        onClick = { onChipClick(market.marketId) })
                }
            }
            LazyRow(contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.space16)) {
                itemsIndexed(
                    categories,
                    key = { _, category -> category.categoryId }) { index, category ->
                    HomeCategoriesItem(
                        modifier = Modifier.animateItemPlacement(),
                        label = category.categoryName,
                        onClick = { oncClickCategory(category.categoryId, index) }
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