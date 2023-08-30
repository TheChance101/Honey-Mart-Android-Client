package org.the_chance.honeymart.ui.features.category.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.components.FormHeader
import org.the_chance.honeymart.ui.components.FormTextField
import org.the_chance.honeymart.ui.features.category.CategoriesInteractionsListener
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honeymart.ui.features.category.composable.CategoryIconItem
import org.the_chance.honeymart.ui.features.category.showAddUpdateCategoryButton
import org.the_chance.honeymart.ui.features.category.showButton
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.blackOn37
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun AddCategoryContent(
    listener: CategoriesInteractionsListener,
    state: CategoriesUiState,
) {

    Column(
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.dimens.space16,
            )
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = MaterialTheme.shapes.medium
            )
            .verticalScroll(rememberScrollState())
    ) {
        FormHeader(
            title = stringResource(R.string.add_new_category),
            iconPainter = painterResource(id = R.drawable.icon_add_product)
        )


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
        ) {

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
                modifier = Modifier
                    .padding(
                        start = MaterialTheme.dimens.space16,
                        top = MaterialTheme.dimens.space32
                    )
                    .align(Alignment.Start),
                text = stringResource(R.string.select_category_image),
                style = Typography.bodyMedium.copy(color = blackOn37)
            )
            Row(
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
            ) {
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

            HoneyFilledButton(
                label = stringResource(R.string.add),
                onClick = {
                    listener.onClickAddCategory(
                        name = state.newCategory.newCategoryName,
                        categoryIconID = state.newCategory.newIconId
                    )
                },
                isButtonEnabled = state.showAddUpdateCategoryButton(),
                isLoading = state.isLoading,
                icon = R.drawable.icon_add_product,
                modifier = Modifier
                    .padding(
                        bottom = MaterialTheme.dimens.space64,
                        start = MaterialTheme.dimens.space24,
                        end = MaterialTheme.dimens.space24
                    )
            )

        }

    }
}

