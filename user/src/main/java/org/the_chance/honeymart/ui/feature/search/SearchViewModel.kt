package org.the_chance.honeymart.ui.feature.search

import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor()
: BaseViewModel<SearchUiState, SearchUiEffect>(SearchUiState()), SearchInteraction{
    override val TAG: String = this::class.simpleName.toString()
}