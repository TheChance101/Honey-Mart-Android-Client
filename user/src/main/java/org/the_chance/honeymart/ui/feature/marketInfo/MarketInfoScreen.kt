package org.the_chance.honeymart.ui.feature.marketInfo

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.EmptyCategoriesPlaceholder
import org.the_chance.honeymart.ui.composables.EventHandler
import org.the_chance.honeymart.ui.feature.home.composables.HomeCategoriesItem
import org.the_chance.honeymart.ui.feature.marketInfo.composables.CardChip
import org.the_chance.honeymart.ui.feature.marketInfo.composables.CategoriesAppBarScaffold
import org.the_chance.honeymart.ui.feature.product.navigateToProductScreen
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.composables.categoryIcons
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun MarketInfoScreen(
    viewModel: MarketInfoViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current

    CategoriesAppBarScaffold(navController) {
        MarketInfoContent(state, listener = viewModel)
    }

    EventHandler(
        effects = viewModel.effect,
        handleEffect = { effect, navControllers ->
            when (effect) {
                is MarketInfoUiEffect.ClickMarketEffect -> {
                    navControllers.navigateToProductScreen(
                        effect.categoryId,
                        effect.marketId,
                        effect.position
                    )
                }
            }
        })
}

@Composable
fun MarketInfoContent(
    state: MarketInfoUiState,
    listener: MarketInteractionListener,
) {
    Loading(state.isLoading)

    ConnectionErrorPlaceholder(state.isError, listener::onGetData)

    ContentVisibility(state.showLazyCondition()) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth(),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(
                bottom = MaterialTheme.dimens.widthItemMarketCard / 2,
                end = MaterialTheme.dimens.space16,
                start = MaterialTheme.dimens.space16,
            ),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space2),
        ) {
            item(span = { GridItemSpan(3) }) {
                Column(
                    modifier = Modifier.padding(bottom = MaterialTheme.dimens.space8),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = state.marketName,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(
                            top = MaterialTheme.dimens.space16,
                            bottom = MaterialTheme.dimens.space8
                        )
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space4),
                        verticalAlignment = Alignment.CenterVertically,
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
                            text = state.categoriesCountState,
                            icon = painterResource(id = R.drawable.boxes)
                        )
                        CardChip(
                            text = state.productsCountState,
                            icon = painterResource(id = R.drawable.box_minimalistic)
                        )
                    }
                    ImageNetwork(
                        imageUrl = state.imageUrl,
                        contentDescription = stringResource(id = R.string.category_image),
                        modifier = Modifier
                            .height(MaterialTheme.dimens.space198)
                            .clip(shape = RoundedCornerShape(MaterialTheme.dimens.space24))
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            itemsIndexed(state.categories) { index, item ->
                val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                val isNarrowScreen = screenWidth < 600.dp

                if (index == 1) {
                    BottomHalfHexagonCanvas()
                }

                HomeCategoriesItem(
                    modifier = Modifier
                        .offset(
                            x = 0.dp,
                            y = if (index % 3 == 1)
                                MaterialTheme.dimens.widthItemMarketCard / 1.6f
                            else 8.dp
                        )
                        .padding(
                            bottom = 24.dp,
                            top = 8.dp
                        ),
                    label = item.categoryName,
                    onClick = { listener.onClickCategory(item.categoryId, index) },
                    backgroundColor = if (index / 3 % 2 == 0) {
                        MaterialTheme.colorScheme.onTertiary
                    } else {
                        primary100.copy(alpha = 0.16f)
                    },
                    width = if (isNarrowScreen) 100.dp else MaterialTheme.dimens.widthItemMarketCard,
                    painter = painterResource(
                        id = categoryIcons[item.categoryImageId] ?: R.drawable.ic_cup_paper
                    )
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
fun BottomHalfHexagonCanvas(
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val hexagonSize = size.maxDimension

        val path = Path().apply {
            val angleRadians = Math.toRadians(60.0).toFloat()
            val radius = hexagonSize / 1.7f

            (0..3).forEach { i ->
                val currentAngle = angleRadians * i
                val x = center.x + radius * cos(currentAngle)
                val y = center.y + radius * sin(currentAngle)
                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
            close()
        }
        drawIntoCanvas {
            it.drawOutline(
                outline = Outline.Generic(path),
                paint = Paint().apply {
                    color = primary100.copy(alpha = 0.16f)
                    pathEffect = PathEffect.cornerPathEffect(16.dp.toPx())
                }
            )
        }
    }
}