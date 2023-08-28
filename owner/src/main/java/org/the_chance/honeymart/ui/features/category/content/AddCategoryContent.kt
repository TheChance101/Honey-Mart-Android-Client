package org.the_chance.honeymart.ui.features.category.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.components.FormHeader
import org.the_chance.honeymart.ui.components.FormTextField
import org.the_chance.honeymart.ui.features.category.CategoriesInteractionsListener
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honeymart.ui.features.category.composable.CategoryIconItem
import org.the_chance.honeymart.ui.features.category.showButton
import org.the_chance.honymart.ui.composables.HoneyFilledIconButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.blackOn37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white

@Composable
fun AddCategoryContent(
    listener: CategoriesInteractionsListener,
    state: CategoriesUiState,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
            .padding(
                end = MaterialTheme.dimens.space16, bottom = MaterialTheme.dimens.space16
            )
            .clip(
                RoundedCornerShape(
                    topEnd = MaterialTheme.dimens.space16, topStart = MaterialTheme.dimens.space16
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_honey_sun),
            contentDescription = "",
            modifier = Modifier.align(Alignment.TopEnd)
        )

        Column(modifier = Modifier.fillMaxSize()) {
            FormHeader(
                title = stringResource(R.string.add_new_category),
                iconPainter = painterResource(id = R.drawable.icon_add_product)
            )

            FormTextField(
                text = state.newCategory.newCategoryName,
                modifier = Modifier.padding(top = MaterialTheme.dimens.space36),
                hint = stringResource(R.string.category_name),
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
                    start = MaterialTheme.dimens.space16,
                    top = MaterialTheme.dimens.space32
                ),
                text = stringResource(R.string.select_category_image),
                style = Typography.bodyMedium.copy(color = blackOn37)
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = MaterialTheme.dimens.categoryIconItem),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                modifier = Modifier.padding(MaterialTheme.dimens.space16)
            ) {
                items(count = state.categoryIcons.size) { index ->
                    CategoryIconItem(
                        iconPainter = painterResource(id = state.categoryIcons[index].icon),
                        isSelected = state.categoryIcons[index].isSelected,
                        categoryIconId = state.categoryIcons[index].categoryIconId,
                        onClick = {
                            listener.onClickNewCategoryIcon(
                                state.categoryIcons[index].categoryIconId
                            )
                        }
                    )
                }
            }

        }
        HoneyFilledIconButton(
            label = stringResource(R.string.add),
            onClick = {
                listener.onClickAddCategory(
                    name = state.newCategory.newCategoryName,
                    categoryIconID = state.newCategory.newIconId
                )
            },
            isEnable = state.showButton(),
            iconPainter = painterResource(id = R.drawable.icon_add_product),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    bottom = MaterialTheme.dimens.space64,
                    start = MaterialTheme.dimens.space24,
                    end = MaterialTheme.dimens.space24
                )
        )
    }
}

