package org.the_chance.honeymart.ui.features.signup.market_info.composables

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.white200

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddImageButton(
    singlePhotoPickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.size(MaterialTheme.dimens.card),
        colors = CardDefaults.cardColors(white200),
        onClick = {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .size(MaterialTheme.dimens.icon48),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_add_to_cart),
                contentDescription = stringResource(R.string.icon_add),
                modifier = Modifier.size(MaterialTheme.dimens.icon24),
                tint = black60
            )
        }
    }
}