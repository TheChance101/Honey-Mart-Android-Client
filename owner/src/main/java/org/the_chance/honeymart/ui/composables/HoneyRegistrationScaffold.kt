package org.the_chance.honeymart.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black87
import org.the_chance.honymart.ui.theme.dimens


/**
 * Created by Aziza Helmy on 8/5/2023.
 */
@Composable
fun HoneyRegistrationScaffold(
    title: String,
    subTitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)) {
        Text(
            text = title,
            modifier = Modifier.padding(top = MaterialTheme.dimens.space16),
            style = Typography.displayMedium.copy(black87), textAlign = TextAlign.Center
        )
        Text(
            text = subTitle,
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space16
            ),
            style = Typography.bodySmall.copy(black37),
            textAlign = TextAlign.Center
        )
        content
        HoneyFilledButton(label = "", onClick = {})
    }
}

@Preview(name = "phone", device = Devices.PHONE, showSystemUi = true)
@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun HoneyRegistrationScaffoldPreview() {
    HoneyRegistrationScaffold(
        title = "Aziza", subTitle = "Whaaat",
        content = {
            HoneyTextField(
                text = "email",
                hint = "email",
                iconPainter = painterResource(id = R.drawable.ic_email),
                onValueChange = { },
                errorMessage = ""
            )
        },
    )
    HoneyTextField(
        text = "password",
        hint = "pass",
        iconPainter = painterResource(id = R.drawable.ic_password),
        onValueChange = { },
        errorMessage = ""
    )

}