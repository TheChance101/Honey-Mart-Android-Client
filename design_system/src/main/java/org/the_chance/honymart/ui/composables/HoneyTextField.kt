package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black16
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.error
import org.the_chance.honymart.ui.theme.white200

@Composable
fun HoneyTextField(
    hint: String,
    iconPainter: Painter,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    text: String = "",
    errorMessage: String = "",
    isError: Boolean = errorMessage.isNotEmpty(),
) {
    Column {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.space16)
                .height(MaterialTheme.dimens.heightOutlinedTextField),
            value = text,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = hint,
                    color = if (isError) error else black37,
                    style = Typography.displaySmall,
                )
            },
            shape = Shapes.medium,
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                focusedSupportingTextColor = if (isError) error else black37,
                focusedContainerColor = (MaterialTheme.colorScheme.onTertiary),
                disabledContainerColor = (MaterialTheme.colorScheme.onTertiary),
                focusedBorderColor = if (isError) error else black16,
                unfocusedBorderColor = if (isError) error else black16,
            ),
            trailingIcon = {
                if (isError) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_error_password),
                        contentDescription = stringResource(R.string.copy_button),
                        tint = error
                    )
                }
            },
            leadingIcon = {
                Icon(
                    painter = iconPainter,
                    contentDescription = stringResource(R.string.copy_button),
                    tint = if (isError) error else white200
                )
            },
            isError = isError,
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = error,
                style = Typography.displaySmall,
                modifier = Modifier.padding(
                    start = MaterialTheme.dimens.space16,
                    top = MaterialTheme.dimens.zero
                )
            )
        }
    }
}


@Preview(name = "phone", device = Devices.PHONE, showSystemUi = true)
@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun TextFieldPreview() {
    HoneyTextField(
        hint = "Email",
        iconPainter = painterResource(id = R.drawable.ic_email),
        isError = true,
        errorMessage = stringResource(R.string.that_s_not_a_valid_email),
        onValueChange = {}
    )
}

