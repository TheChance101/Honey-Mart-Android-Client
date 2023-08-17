package org.the_chance.honeymart.ui.feature.home.composables

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun CouponsItem() {
    val color = MaterialTheme.colorScheme
    Row {
        Column(
            modifier = Modifier
                .drawBehind {
                    val path = couponLeftPath(
                        size = Size(
                            width = size.width,
                            height = size.height
                        ),
                        cornerRadius = 8.dp.toPx()
                    )
                    drawPath(
                        path = path,
                        color = color.secondary,
                    )
                }
                .padding(
                    horizontal = MaterialTheme.dimens.space16,
                    vertical = MaterialTheme.dimens.space8
                ),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
        ) {
            Text(
                text = "Bronze foundation",
                style = MaterialTheme.typography.displaySmall.copy(color = MaterialTheme.colorScheme.onSecondary),
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
            ) {
                CouponDataItem(
                    label = "Start Date",
                    value = "10.08.2023"
                )
                CouponDataItem(
                    label = "End Date",
                    value = "10.08.2023"
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
            ) {
                CouponDataItem(
                    label = "No. Deal",
                    value = "10"
                )
                CouponDataItem(
                    label = "Price",
                    value = "$200"
                )
                CouponDataItem(
                    label = "Offer Price",
                    value = "$100",
                    valueColor = MaterialTheme.colorScheme.primary
                )
            }

            Button(
                onClick = {},
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .height(21.dp)
                    .width(74.dp),
                contentPadding = PaddingValues(
                    bottom = 2.dp,
                    top = 0.dp,
                    end = 0.dp,
                    start = 0.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(.5F),
                )
            ) {
                Text(
                    text = "Get coupon",
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Column(
            Modifier
                .drawBehind {
                    val rectangleGap = 4.dp.toPx()
                    val rectangleHeight = 4.dp.toPx()

                    var centerYStart = size.height - rectangleHeight - 2.dp.toPx()

                    while (centerYStart > 8.dp.toPx() * 2) {
                        drawRect(
                            color = primary100,
                            topLeft = Offset(x = -1.dp.toPx(), y = centerYStart - 8.dp.toPx()),
                            size = Size(width = 2.dp.toPx(), height = rectangleHeight),
                        )
                        centerYStart -= rectangleHeight + rectangleGap
                    }

                    val path = couponRightPath(
                        size = Size(
                            width = size.width,
                            height = size.height
                        ),
                        cornerRadius = 8.dp.toPx(),
                        smallCornerRadius = 2.dp.toPx(),
                        mediumCornerRadius = 4.dp.toPx()
                    )
                    drawPath(
                        path = path,
                        color = color.primary,
                    )
                }
                .padding(
                    top = MaterialTheme.dimens.space16,
                    bottom = MaterialTheme.dimens.space8,
                    end = MaterialTheme.dimens.space16,
                    start = MaterialTheme.dimens.space16
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .width(100.dp)
                    .height(87.dp)
                    .clip(RoundedCornerShape(12.dp)),
                painter = painterResource(
                    id =
                    org.the_chance.design_system.R.drawable.test
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier.padding(top = MaterialTheme.dimens.space8),
                text = "Coupon Code",
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
            )
            Text(
                modifier = Modifier.padding(top = MaterialTheme.dimens.space4),
                text = "445902378",
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
            )
        }


    }
}

fun couponLeftPath(size: Size, cornerRadius: Float): Path {
    return Path().apply {
        reset()
        // Top left arc
        arcTo(
            rect = Rect(
                offset = Offset.Zero,
                size = Size(cornerRadius, cornerRadius)
            ),
            startAngleDegrees = 180.0f,
            sweepAngleDegrees = 90.0f,
            forceMoveTo = false
        )

        lineTo(x = size.width - cornerRadius, y = 0f)
        // Top right arc
        arcTo(
            rect = Rect(
                left = size.width - cornerRadius,
                top = -cornerRadius,
                right = size.width + cornerRadius,
                bottom = cornerRadius
            ),
            startAngleDegrees = 180.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        lineTo(x = size.width, y = size.height - cornerRadius)
        // Bottom right arc
        arcTo(
            rect = Rect(
                left = size.width - cornerRadius,
                top = size.height - cornerRadius,
                right = size.width + cornerRadius,
                bottom = size.height + cornerRadius
            ),
            startAngleDegrees = 270.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )
        lineTo(x = cornerRadius, y = size.height)
        // Bottom left arc
        arcTo(
            rect = Rect(
                offset = Offset(0f, size.height - cornerRadius),
                size = Size(cornerRadius, cornerRadius)
            ),
            startAngleDegrees = 90.0f,
            sweepAngleDegrees = 90.0f,
            forceMoveTo = false
        )
        lineTo(x = 0f, y = cornerRadius)
        close()
    }
}

fun couponRightPath(
    size: Size,
    cornerRadius: Float,
    smallCornerRadius: Float,
    mediumCornerRadius: Float,
): Path {
    return Path().apply {
        reset()

        arcTo(
            rect = Rect(
                left = -cornerRadius,
                top = -cornerRadius,
                right = cornerRadius,
                bottom = cornerRadius
            ),
            startAngleDegrees = 90.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )

        lineTo(x = size.width, y = 0f)

        val radius = mediumCornerRadius
        var centerYEnd = smallCornerRadius + radius


        while (centerYEnd < size.height) {
            arcTo(
                rect = Rect(
                    left = size.width - radius,
                    top = centerYEnd - radius,
                    right = size.width + radius,
                    bottom = centerYEnd + radius
                ),
                startAngleDegrees = -90.0f,
                sweepAngleDegrees = -180.0f,
                forceMoveTo = false
            )
            centerYEnd += 2 * radius + mediumCornerRadius
        }

        lineTo(x = size.width, y = size.height)
        // Bottom left arc
        arcTo(
            rect = Rect(
                left = -cornerRadius,
                top = size.height - cornerRadius,
                right = cornerRadius,
                bottom = size.height + cornerRadius
            ),
            startAngleDegrees = 0.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )

        lineTo(x = 0f, y = cornerRadius)
        close()
    }
}

@Composable
fun CouponDataItem(
    label: String,
    value: String,
    labelColor: Color = MaterialTheme.colorScheme.onBackground,
    valueColor: Color = MaterialTheme.colorScheme.onSecondary
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.displaySmall.copy(color = labelColor)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.displaySmall.copy(color = valueColor)
        )
    }
}


@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PreviewCoupons() {
    HoneyMartTheme {
        CouponsItem()
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewCouponsDark() {
    HoneyMartTheme {
        CouponsItem()
    }
}
