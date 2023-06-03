package org.the_chance.ui.category

import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.ui.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor() : BaseViewModel() {
    override val TAG: String = this::class.java.simpleName
}