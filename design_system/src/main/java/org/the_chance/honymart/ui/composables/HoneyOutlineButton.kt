package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun HoneyOutlineButton(
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    background: Color = Color.Transparent,
) {
    Button(
        onClick = onClick,
        enabled = isEnable,
        modifier = modifier
            .wrapContentWidth()
            .height(MaterialTheme.dimens.heightPrimaryButton)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = Shapes.medium
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = contentColor,
            disabledContentColor = contentColor,
            disabledContainerColor = background.copy(.5F),
        )
    ) {
        Text(
            text = label,
            style = Typography.bodySmall.copy(fontWeight = FontWeight.Normal),
            color = contentColor
        )
    }

}

@Preview
@Composable
private fun CustomButtonSmallPreview() {
    HoneyMartTheme {
        HoneyOutlineButton(label = stringResource(id = R.string.Sign_up), onClick = {})
    }
}

