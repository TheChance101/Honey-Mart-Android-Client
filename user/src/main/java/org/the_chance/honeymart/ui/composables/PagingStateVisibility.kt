package org.the_chance.honeymart.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun <T : Any> PagingStateVisibility(
    items: LazyPagingItems<T>,
) {
    when {
        items.loadState.append is LoadState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Loading(
                    modifier = Modifier
                        .padding(MaterialTheme.dimens.space12)
                        .align(Alignment.Center),
                    size = MaterialTheme.dimens.space64,
                    state = true
                )
            }
        }

        items.loadState.append is LoadState.Error -> {
            Text(
                text = stringResource(id = R.string.error_loading_data),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.space16),
                textAlign = TextAlign.Center
            )
            HoneyFilledButton(
                label = stringResource(id = R.string.try_again_text),
                onClick = { items.retry() },
                modifier = Modifier.wrapContentWidth(),
            )
        }

        items.loadState.refresh is LoadState.Error -> {
            ConnectionErrorPlaceholder(
                state = true,
                onClickTryAgain = { items.retry() },
            )
        }

    }
}

