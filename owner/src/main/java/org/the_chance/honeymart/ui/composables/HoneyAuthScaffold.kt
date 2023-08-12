package org.the_chance.honeymart.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.composables.HoneyAppBarTitle
import org.the_chance.honymart.ui.theme.dimens

/**
 * Created by Aziza Helmy on 8/5/2023.
 */
@Composable
fun HoneyAuthScaffold(
    content: @Composable () -> Unit
) {
    Box {
        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = org.the_chance.owner.R.drawable.auth_image),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxHeight()
            )

            content()
        }

        Image(
            painter = painterResource(org.the_chance.design_system.R.drawable.image_group),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(TopEnd)
                .size(120.dp)
        )

        HoneyAppBarTitle(
            titleColor = Color.White,
            modifier = Modifier.padding(
                top = MaterialTheme.dimens.space32,
                start = MaterialTheme.dimens.space24
            )
        )
    }
}

@Preview(name = "phone", device = Devices.PHONE, showSystemUi = true)
@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun HoneyRegistrationScaffoldPreview() {

}