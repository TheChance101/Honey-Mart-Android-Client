package org.the_chance.honeymart.ui.features.category.categories

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.components.FormHeader
import org.the_chance.honeymart.ui.components.FormTextField
import org.the_chance.honeymart.ui.features.category.CategoriesInteractionsListener
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honeymart.ui.features.category.Visibility
import org.the_chance.honeymart.ui.features.category.showButton
import org.the_chance.honeymart.ui.features.update_category.components.CategoryIconItem
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyOutlineButton
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun UpdateCategoryContent(
    state: CategoriesUiState,
    listener: CategoriesInteractionsListener
) {
    AnimatedVisibility(visible = state.showScreenState.showUpdateCategory) {
        Column(
            modifier = Modifier.fillMaxHeight()
                .padding(bottom = 40.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        end = MaterialTheme.dimens.space16,
                        bottom = MaterialTheme.dimens.space16
                    )
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                FormHeader(
                    title = stringResource(R.string.update_category),
                    iconPainter = painterResource(id = R.drawable.icon_update_category)
                )
                FormTextField(
                    text = state.newCategory.newCategoryName,
                    hint = stringResource(R.string.new_category_name),
                    keyboardType = KeyboardType.Text,
                    onValueChange = listener::onNewCategoryNameChanged,
                    errorMessage = when (state.newCategory.categoryNameState) {
                        ValidationState.BLANK_TEXT_FIELD -> stringResource(R.string.category_name_can_t_be_blank)
                        ValidationState.SHORT_LENGTH_TEXT -> stringResource(R.string.category_name_is_too_short)
                        else -> ""
                    }
                )
                Text(
                    modifier = Modifier.padding(
                        top = MaterialTheme.dimens.space24, start = MaterialTheme.dimens.space16
                    ),
                    text = stringResource(R.string.select_category_image),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center,
                )
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = MaterialTheme.dimens.categoryIconItem),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                    modifier = Modifier.padding(MaterialTheme.dimens.space16)
                ) {
                    if (state.categoryIcons.isNotEmpty())
                        items(count = state.categoryIcons.size) { index ->
                        CategoryIconItem(
                            iconPainter = painterResource(id = state.categoryIcons[index].icon),
                            isSelected = state.categoryIcons[index].isSelected,
                            categoryIconId = state.categoryIcons[index].categoryIconId,
                            onClick = { listener.onClickNewCategoryIcon(state.categoryIcons[index]
                                .categoryIconId) }
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1F))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.dimens.space16,
                            vertical = MaterialTheme.dimens.space24
                        ),
                    horizontalArrangement = Arrangement.End
                ) {
                    HoneyFilledButton(
                        label = "Save update",
                        isButtonEnabled = state.showButton(),
                        onClick = { listener.updateCategory(state) },
                        modifier = Modifier.width(IntrinsicSize.Max)
                    )
                    HoneyOutlineButton(
                        label = "Cancel",
                        onClick = { listener.resetShowState(Visibility.UPDATE_CATEGORY) },
                        modifier = Modifier.width(IntrinsicSize.Max)
                    )
                }
            }
        }
    }
}


