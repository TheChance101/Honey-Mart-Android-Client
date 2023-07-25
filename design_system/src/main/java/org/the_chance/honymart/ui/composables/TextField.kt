package org.the_chance.honymart.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black16
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.error
import org.the_chance.honymart.ui.theme.white200

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChange: (String) -> Unit = {},
    hint: String ,
    isTrailingIcon: Boolean = false,
    @DrawableRes idIconDrawableRes: Int,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth().padding(horizontal = 16.dp)
            .height(64.dp),
        value = text,
        onValueChange = onValueChange,
        label = {
            Text(
                text = hint,
                color =  if (isTrailingIcon) error else black37,
                style = Typography.displaySmall
            )
                },
        shape = Shapes.medium,
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (isTrailingIcon) error else black16,
            unfocusedBorderColor = if (isTrailingIcon) error else black16
        ),
        trailingIcon = {
            if (isTrailingIcon) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_error_password),
                        contentDescription = "Copy button",
                        tint = error
                    )
            }
        },
        leadingIcon = {
            Icon(
                painter =
                painterResource(idIconDrawableRes),
                contentDescription = "Copy button",
                tint = if (isTrailingIcon) error else white200
            )
        },
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TextFieldPreview() {
    TextField(hint = "Email", idIconDrawableRes = R.drawable.ic_email,
        isTrailingIcon = true)
}