package org.the_chance.honeymart.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white

@Composable
fun OrderDetailsCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    orderName: String,
    orderPrice: String,
    orderCount: String,
    onClickCard: (orderId: Long) -> Unit,
    onClickAddReview: () -> Unit,
    isAddReviewVisible: Boolean,
    orderId: Long,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.cardHeight)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClickCard(orderId) },
        colors =
        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onTertiary),
        shape = MaterialTheme.shapes.medium
    ) {
        Row {
            ImageNetwork(
                modifier = Modifier
                    .weight(0.3f),
                imageUrl = imageUrl,
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.7f)
                    .padding(MaterialTheme.dimens.space8),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space4),
            ) {
                Text(
                    text = orderName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = orderPrice,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space4)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_cart_details),
                        contentDescription = stringResource(R.string.cart_check_icon),
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(MaterialTheme.dimens.icon16)
                    )
                    Text(
                        text = "x$orderCount",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    if (isAddReviewVisible) {
                        Button(
                            modifier = Modifier
                                .height(
                                    MaterialTheme.dimens.space32
                                ),
                            onClick = onClickAddReview,
                            contentPadding = PaddingValues(
                                horizontal = MaterialTheme.dimens.space8,
                            ),
                        ) {
                            Icon(
                                modifier = Modifier.size(MaterialTheme.dimens.icon16),
                                tint = white,
                                painter = painterResource(id = R.drawable.ic_pen),
                                contentDescription = "Write a review",
                            )
                            Text(
                                modifier = Modifier.padding(start = MaterialTheme.dimens.space4),
                                text = "Write a review",
                                style = MaterialTheme.typography.displaySmall.copy(
                                    baselineShift = BaselineShift(0.2f)
                                ),
                                color = white,
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OrderDetailsCardPreview() {
    HoneyMartTheme {
        OrderDetailsCard(
            imageUrl = "https://img.freepik.com/free-photo/mid-century-modern-living-room-interior-design-with-monstera-tree_53876-129804.jpg",
            orderName = "To Kill a Mockingbird",
            orderPrice = "30,000$",
            orderCount = "3",
            orderId = 0,
            onClickCard = {},
            onClickAddReview = {},
            isAddReviewVisible = true
        )
    }
}