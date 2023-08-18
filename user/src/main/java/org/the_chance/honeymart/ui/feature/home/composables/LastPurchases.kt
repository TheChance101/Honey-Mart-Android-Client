package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black60


@Preview
@Composable
fun LastPurchasesItems(
    modifier: Modifier = Modifier,
    image: String = "",
    label: String = "blah blah blah",
    onClick: () -> Unit = {},

    ) {
    Box(
        modifier = modifier
            .size(width = 149.dp, height = 152.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Column {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                ImageNetwork(
                    imageUrl = image,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(topStart = 16.dp))
                        .background(color = Color.Red)
                )
                ImageNetwork(
                    imageUrl = image,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(topEnd = 16.dp))
                        .background(color = Color.Green)
                )
            }
            Row(
                modifier = Modifier.weight(1f)
            ) {
                ImageNetwork(
                    imageUrl = image,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(bottomStart = 16.dp))
                        .background(color = Color.Blue)
                )
                ImageNetwork(
                    imageUrl = image,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(bottomEnd = 16.dp))
                        .background(color = Color.Yellow)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = label,
                style = Typography.displayLarge.copy(black60),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}



