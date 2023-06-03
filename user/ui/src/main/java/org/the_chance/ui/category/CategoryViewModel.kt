package org.the_chance.ui.category

import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.ui.BaseViewModel
@HiltViewModel
class CategoryViewModel : BaseViewModel() {
    override val TAG: String = this::class.java.simpleName
}