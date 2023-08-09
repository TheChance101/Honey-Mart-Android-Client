package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.primary100

/**
 * Created by Aziza Helmy on 8/9/2023.
 */
@Composable
fun HoneyAuthFooter(
    text: String,
    textButtonText: String,
    onTextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = Typography.displaySmall.copy(color = black37),
            textAlign = TextAlign.Center
        )
        TextButton(
            onClick = onTextButtonClicked,
            colors = ButtonDefaults.textButtonColors(Color.Transparent)
        ) {
            Text(
                text = textButtonText,
                style = Typography.displayLarge.copy(color = primary100),
                textAlign = TextAlign.Center,
            )
        }
    }
}
