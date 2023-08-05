package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun HoneyFilledIconButton(
    label: String,
    onClick: () -> Unit,
    iconPainter: Painter,
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    background: Color = MaterialTheme.colorScheme.primary,
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        enabled = isEnable,
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.heightPrimaryButton),
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = contentColor,
            disabledContentColor = contentColor,
            disabledContainerColor = background.copy(.5F),

            )
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = label,
            modifier = Modifier
                .padding(end = MaterialTheme.dimens.space8)
                .size(MaterialTheme.dimens.icon24)
        )

        Text(
            text = label,
            style = Typography.bodyMedium,
            color = contentColor
        )

    }
}

@Preview
@Composable
private fun HoneyMartButtonWithIconPreview() {
    HoneyMartTheme {
        HoneyFilledIconButton(
            label = stringResource(id = R.string.order_now),
            iconPainter = painterResource(R.drawable.icon_cart),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun HoneyMartButtonNotEnablePreview() {
    HoneyMartTheme {
        HoneyFilledIconButton(
            label = stringResource(id = R.string.order_now),
            iconPainter = painterResource(R.drawable.icon_cart),
            isEnable = false,
            onClick = {}
        )
    }
}