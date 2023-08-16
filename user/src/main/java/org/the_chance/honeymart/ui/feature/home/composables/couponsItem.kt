package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black87
import org.the_chance.honymart.ui.theme.white

@Preview
@Composable
fun Copouns() {

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .width(width = 320.dp)
            .height(height = 135.dp)
            .background(white)
            .padding(start = 16.dp)
    ) {

        Column {
            Text(
                text = "Bronze foundation",
                style = Typography.displaySmall.copy(color = black87),
                modifier = Modifier

                    .padding(top = 8.dp)
            )
            Text(
                text = "Start Date",
                style = Typography.displaySmall.copy(color = black87),
                modifier = Modifier
                    .padding(top = 8.dp)
            )

        }
    }
}