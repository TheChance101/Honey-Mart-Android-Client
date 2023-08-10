package org.the_chance.honeymart.ui.addCategory

import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.util.ErrorHandler

interface AddCategoryListener {
    fun changeNameCategory(nameCategory: String)
    fun onClickAddCategory()
    fun onClickCategory(categoryImageId: Int)
}