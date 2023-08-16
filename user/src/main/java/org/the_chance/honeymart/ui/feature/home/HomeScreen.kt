package org.the_chance.honeymart.ui.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import org.the_chance.honeymart.ui.feature.home.composables.Hexagon
import org.the_chance.honeymart.ui.feature.home.composables.HomeHorizontalItems
import org.the_chance.honeymart.ui.feature.home.composables.HorizontalPagerIndicator
import org.the_chance.honeymart.ui.feature.home.composables.ItemPager
import org.the_chance.honeymart.ui.feature.home.composables.searchBar
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black87
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
    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(3000)
            pagerState.animateScrollToPage(page = (pagerState.currentPage + 1) % 3)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    state: HomeUiState,
    pagerState: PagerState
) {
    ConstraintLayout(
        modifier = Modifier
            .verticalScroll(
                enabled = true,
                state = rememberScrollState()
            )
            .fillMaxSize()
            .background(white30)
    ) {
        val (pager, indicator, searchBar, marketText,
            marketSeeAll, marketItems, categoriesText,
            categoriesSeeAll, categoryItems) = createRefs()

        HorizontalPager(
            contentPadding = PaddingValues(16.dp),
            pageCount = 3,
            state = pagerState,
            modifier = Modifier.constrainAs(pager) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            ItemPager()
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
                .padding(16.dp)
                .constrainAs(searchBar) {
                    top.linkTo(indicator.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            icon = painterResource(id = org.the_chance.design_system.R.drawable.searchicon)
        )
        Text(
            text = "Markets",
            style = Typography.bodySmall.copy(black87),
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(marketText) {
                    top.linkTo(searchBar.bottom)
                    start.linkTo(parent.start)
                }
        )
        Icon(
            painter = painterResource(id = org.the_chance.design_system.R.drawable.seall),
            contentDescription = null,
            tint = black37,
            modifier = Modifier
                .padding(24.dp)
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
            items(10) { itemIndex ->
                HomeHorizontalItems(
                    categoryName = "marketName",
                )
            }
        }
        Text(
            text = "Categories",
            style = Typography.bodySmall.copy(black87),
            modifier = Modifier
                .padding(16.dp)
                .constrainAs(categoriesText) {
                    top.linkTo(marketItems.bottom)
                    start.linkTo(parent.start)
                }
        )
        Icon(
            painter = painterResource(id = org.the_chance.design_system.R.drawable.seall),
            contentDescription = null,
            tint = black37,
            modifier = Modifier
                .padding(24.dp)
                .constrainAs(categoriesSeeAll) {
                    top.linkTo(marketItems.bottom)
                    end.linkTo(parent.end)
                }
        )
        LazyRow(
            contentPadding = PaddingValues(8.dp),
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
    }
}

@Preview
@Composable

fun HomeScreenPreview() {
    HomeScreen()
}
