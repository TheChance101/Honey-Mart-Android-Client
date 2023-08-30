package org.the_chance.honeymart.ui.features.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.primary100
import java.util.Locale

@Composable
fun CircleWithText(
    text: Char,
    modifier: Modifier = Modifier,
    circleColor: Color = primary100,
    textColor: Color = Color.White,
    textSize: TextUnit = 48.sp
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(vertical = 70.dp)
    ) {
        Box(
            modifier = Modifier
                .size(103.dp)
                .clip(CircleShape)
                .background(circleColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text.uppercase(Locale.ROOT),
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = textColor,
                    fontSize = textSize,
                    baselineShift = BaselineShift(0.2f)
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview
@Composable
fun PreviewCircleWithText() {
    HoneyMartTheme {
        CircleWithText(text = 'M')
    }
}