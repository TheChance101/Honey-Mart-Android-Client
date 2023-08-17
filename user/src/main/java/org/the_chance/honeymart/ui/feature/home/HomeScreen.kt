package org.the_chance.honeymart.ui.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.home.composables.CouponsItem
import org.the_chance.honeymart.ui.feature.home.composables.Hexagon
import org.the_chance.honeymart.ui.feature.home.composables.HomeHorizontalItems
import org.the_chance.honeymart.ui.feature.home.composables.HorizontalPagerIndicator
import org.the_chance.honeymart.ui.feature.home.composables.ItemPager
import org.the_chance.honeymart.ui.feature.home.composables.LastPurchasesItems
import org.the_chance.honeymart.ui.feature.home.composables.NewProductsItems
import org.the_chance.honeymart.ui.feature.home.composables.searchBar
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
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

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(white30)
            .verticalScroll(rememberScrollState())
    ) {

        val (pager, indicator, searchBar, marketText,
            marketSeeAll, marketItems, categoriesText,
            categoriesSeeAll, categoryItems, coupounItem,
            productsText, newProductsItems, lastPurchasesText,
            lastPurchasesItems, discoverProducts, discoverProductsItems) = createRefs()

        HorizontalPager(
            contentPadding = PaddingValues(MaterialTheme.dimens.space16),
            pageCount = state.markets.size,
            state = pagerState,
            modifier = Modifier.constrainAs(pager) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            ItemPager(marketImage = state.markets[it].marketImage, onclick = {})
        }
        HorizontalPagerIndicator(
            itemCount = 3,
            selectedPage = pagerState.currentPage,
            modifier = Modifier.constrainAs(indicator) {
                top.linkTo(pager.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        searchBar(
            modifier = Modifier
                .padding(MaterialTheme.dimens.space16)
                .clickable { }
                .constrainAs(searchBar) {
                    top.linkTo(indicator.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            icon = painterResource(id = R.drawable.searchicon)
        )
        Text(
            text = stringResource(org.the_chance.user.R.string.markets),
            style = Typography.bodySmall.copy(black87),
            modifier = Modifier
                .padding(MaterialTheme.dimens.space16)
                .constrainAs(marketText) {
                    top.linkTo(searchBar.bottom)
                    start.linkTo(parent.start)
                }
        )
        Icon(
            painter = painterResource(id = R.drawable.seall),
            contentDescription = null,
            tint = black37,
            modifier = Modifier
                .padding(MaterialTheme.dimens.space24)
                .constrainAs(marketSeeAll) {
                    top.linkTo(searchBar.bottom)
                    end.linkTo(parent.end)
                }
        )
        LazyRow(
            modifier = Modifier
                .constrainAs(marketItems) {
                    top.linkTo(marketText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            items(state.markets.size) { itemIndex ->
                HomeHorizontalItems(
                    name = state.markets[itemIndex].marketName,
                    image = state.markets[itemIndex].marketImage,
                )
            }
        }
        Text(
            text = stringResource(org.the_chance.user.R.string.categories),
            style = Typography.bodySmall.copy(black87),
            modifier = Modifier
                .padding(MaterialTheme.dimens.space16)
                .constrainAs(categoriesText) {
                    top.linkTo(marketItems.bottom)
                    start.linkTo(parent.start)
                }
        )
        Icon(
            painter = painterResource(id = R.drawable.seall),
            contentDescription = null,
            tint = black37,
            modifier = Modifier
                .padding(MaterialTheme.dimens.space24)
                .constrainAs(categoriesSeeAll) {
                    top.linkTo(marketItems.bottom)
                    end.linkTo(parent.end)
                }
        )
        LazyRow(
            contentPadding = PaddingValues(MaterialTheme.dimens.space8),
            modifier = Modifier
                .constrainAs(categoryItems) {
                    top.linkTo(categoriesText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            items(10) {
                Hexagon()
            }
        }
        LazyRow(
            contentPadding = PaddingValues(MaterialTheme.dimens.space8),
            modifier = Modifier
                .constrainAs(coupounItem) {
                    top.linkTo(categoryItems.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        {
            items(10) {
                CouponsItem()
            }
        }
        Text(
            text = "new Products",
            style = Typography.bodySmall.copy(black87),
            modifier = Modifier
                .padding(MaterialTheme.dimens.space16)
                .constrainAs(productsText) {
                    top.linkTo(coupounItem.bottom)
                    start.linkTo(parent.start)
                }
        )
        LazyRow(
            contentPadding = PaddingValues(MaterialTheme.dimens.space8),
            modifier = Modifier
                .constrainAs(newProductsItems) {
                    top.linkTo(productsText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            items(10) {
                NewProductsItems()
            }
        }
        Text(
            text = "Last Purchases",
            style = Typography.bodySmall.copy(black87),
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(lastPurchasesText) {
                    top.linkTo(newProductsItems.bottom)
                    start.linkTo(parent.start)
                }
        )
        LazyRow(
            contentPadding = PaddingValues(MaterialTheme.dimens.space8),
            modifier = Modifier
                .constrainAs(lastPurchasesItems) {
                    top.linkTo(lastPurchasesText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            items(10) {
                LastPurchasesItems()
            }
        }
        Text(
            text = "Last Purchases",
            style = Typography.bodySmall.copy(black87),
            modifier = Modifier
                .padding(MaterialTheme.dimens.space16)
                .constrainAs(discoverProducts) {
                    top.linkTo(lastPurchasesItems.bottom)
                    start.linkTo(parent.start)
                }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(discoverProductsItems) {
                    top.linkTo(discoverProducts.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            LazyRow(
                contentPadding = PaddingValues(MaterialTheme.dimens.space8),
            ) {
                items(10) {
                    NewProductsItems()
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
