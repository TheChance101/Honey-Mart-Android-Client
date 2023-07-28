package org.the_chance.honymart.ui.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
    @StringRes labelIdStringRes: Int,
    @DrawableRes idIconDrawableRes: Int? = null,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    background: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
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

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            idIconDrawableRes?.let {
                Icon(
                    painter = painterResource(id = idIconDrawableRes),
                    contentDescription = stringResource(id = labelIdStringRes),
                    modifier = Modifier
                        .padding(end = MaterialTheme.dimens.space8)
                        .size(MaterialTheme.dimens.iconMedium)
                )

            }

            Text(
                text = stringResource(id = labelIdStringRes),
                style = Typography.bodyMedium,
                color = contentColor
            )
        }

    }
}

@Preview
@Composable
private fun CustomButtonPreview() {
    HoneyMartTheme {
        CustomButton(labelIdStringRes = R.string.Sign_up) {}
    }
}

@Preview
@Composable
private fun CustomButtonWithIconPreview() {
    HoneyMartTheme {
        CustomButton(
            labelIdStringRes = R.string.order_now,
            idIconDrawableRes = R.drawable.icon_cart
        ) {}
    }
}

@Preview
@Composable
private fun CustomButtonNotEnablePreview() {
    HoneyMartTheme {
        CustomButton(
            labelIdStringRes = R.string.order_now,
            idIconDrawableRes = R.drawable.icon_cart,
            isEnable = false,
        ) {}
    }
}