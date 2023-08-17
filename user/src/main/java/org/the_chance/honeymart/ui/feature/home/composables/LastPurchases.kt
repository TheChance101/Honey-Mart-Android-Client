package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.user.R


@Preview
@Composable
fun LastPurchasesItems(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(width = 149.dp, height = 152.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Column {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = org.the_chance.design_system.R.drawable.test),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(topStart = 16.dp))
                        .background(color = Color.Red)
                )
                Image(
                    painter = painterResource(id = org.the_chance.design_system.R.drawable.image_test),
                    contentDescription = null,
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
                Image(
                    painter = painterResource(id = org.the_chance.design_system.R.drawable.image_test),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(bottomStart = 16.dp))
                        .background(color = Color.Blue)
                )
                Image(
                    painter = painterResource(id = org.the_chance.design_system.R.drawable.test),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(bottomEnd = 16.dp))
                        .background(color = Color.Yellow)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Image 1",
                style = Typography.displayLarge.copy(black60),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}



