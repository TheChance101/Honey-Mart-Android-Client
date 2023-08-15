package org.the_chance.honymart.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun HoneyFilledButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    isButtonEnabled: Boolean = true,
    isLoading: Boolean = false,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    background: Color = MaterialTheme.colorScheme.primary,
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        enabled = isButtonEnabled,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.space16)
            .height(MaterialTheme.dimens.heightPrimaryButton),
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = contentColor,
            disabledContentColor = contentColor,
            disabledContainerColor = background.copy(.5F),

            )
    ) {
        Loading(state = isLoading)
        AnimatedVisibility(
            visible = !isLoading,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)),
            exit = fadeOut(animationSpec = tween(durationMillis = 500))
        ) {
            Text(
                text = label,
                style = Typography.bodyMedium,
                color = contentColor
            )
        }
    }
}


@Preview
@Composable
private fun HoneyMartButtonPreview() {
    HoneyMartTheme {
        HoneyFilledButton(
            label = stringResource(id = R.string.Sign_up),
            onClick = {})
    }
}
