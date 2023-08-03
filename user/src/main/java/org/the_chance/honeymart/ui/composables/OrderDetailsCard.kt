package org.the_chance.honeymart.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white

@Composable
fun OrderDetailsCard(
    imagePainter: Painter,
    orderName: String,
    orderPrice: String,
    orderCount: String,
    orderId: Long,
    onClickCard: (orderId: Long) -> Unit,
    modifier: Modifier = Modifier
) {
        Box(
            modifier = modifier
                .size(width = 160.dp, MaterialTheme.dimens.heightItem)
                .clip(MaterialTheme.shapes.medium)
                .clickable { onClickCard(orderId) }
        ) {
            ImageNetwork(
                modifier = Modifier.fillMaxSize(),
                imagePainter = rememberAsyncImagePainter(model = imagePainter),
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        start = MaterialTheme.dimens.space8,
                        bottom = MaterialTheme.dimens.space8,
                        end = MaterialTheme.dimens.space8
                    )
            ) {
                Text(
                    text = orderName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "$orderPrice$",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_cart_details),
                        contentDescription = stringResource(R.string.cart_check_icon),
                        tint = white,
                        modifier = Modifier.padding(end = MaterialTheme.dimens.space4)
                    )
                    Text(
                        text = "x$orderCount",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
}

@Preview
@Composable
fun OrderDetailsCardPreview() {
    OrderDetailsCard(
        imagePainter = rememberAsyncImagePainter(model = "https://img.freepik.com/free-photo/mid-century-modern-living-room-interior-design-with-monstera-tree_53876-129804.jpg"),
        orderName = "To Kill a Mockingbird",
        orderPrice = "30,000",
        orderCount = "3",
        orderId = 0,
        onClickCard = {}
    )
}