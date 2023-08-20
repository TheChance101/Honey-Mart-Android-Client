package org.the_chance.honeymart.ui.features.signup.market_info.composables


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import org.the_chance.honeymart.ui.features.signup.market_info.MarketInfoInteractionsListener
import org.the_chance.honeymart.ui.features.signup.market_info.MarketInfoUiState
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.owner.R

@Composable
fun MarketFieldsScaffold(
    state: MarketInfoUiState,
    listener: MarketInfoInteractionsListener,
) {
    val context = LocalContext.current
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            val imageByteArray =
                uri?.let {
                    context.contentResolver.openInputStream(it)?.use { inputStream ->
                        inputStream.readBytes()
                    }
                } ?: byteArrayOf()
            listener.onImageSelected(imageByteArray)
        }
    )

    Column {
        HoneyTextField(
            text = state.marketNameState.value,
            hint = stringResource(R.string.market_name),
            iconPainter = painterResource(R.drawable.icon_shop),
            onValueChange = listener::onMarketNameInputChange,
            errorMessage = state.marketNameState.errorState,
        )
        HoneyTextField(
            text = state.marketAddressState.value,
            hint = stringResource(R.string.address),
            iconPainter = painterResource(R.drawable.icon_map_point),
            onValueChange = listener::onMarketAddressInputChange,
            errorMessage = state.marketAddressState.errorState,
        )
        HoneyTextField(
            text = state.marketDescriptionState.value,
            hint = stringResource(R.string.description),
            iconPainter = painterResource(R.drawable.icon_document_add),
            onValueChange = listener::onMarketDescriptionInputChanged,
            errorMessage = state.marketDescriptionState.errorState,
        )
        Column(modifier = Modifier.padding(MaterialTheme.dimens.space16)) {
            Text(
                modifier = Modifier.padding(bottom = MaterialTheme.dimens.space8),
                text = stringResource(R.string.market_images),
                style = MaterialTheme.typography.displaySmall,
                color = if (state.isMarketImageEmpty) org.the_chance.honymart.ui.theme.error else black37,
                textAlign = TextAlign.Center,
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                if (state.isMarketImageEmpty) {
                    AddImageButton(photoPickerLauncher)
                } else {
                    ItemImageProduct(state.image, listener::onClickRemoveSelectedImage)
                }
            }
        }
    }
    HoneyFilledButton(
        label = stringResource(R.string.send),
        onClick = listener::onClickSendButton,
        background = primary100,
        contentColor = Color.White,
        isLoading = state.isLoading
    )
}
