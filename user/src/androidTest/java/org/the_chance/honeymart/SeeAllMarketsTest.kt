package org.the_chance.honeymart

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test
import org.the_chance.honeymart.ui.feature.SeeAllmarkets.MarketContent
import org.the_chance.honeymart.ui.feature.SeeAllmarkets.MarketInteractionListener
import org.the_chance.honeymart.ui.feature.SeeAllmarkets.MarketsUiState
import org.the_chance.honymart.ui.theme.HoneyMartTheme

class SeeAllMarketsTest {

    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()


    @Test
    fun onGetMarketSuccess_iaActivity() {
        testRule.setContent {
            HoneyMartTheme {
                MarketContent(state = MarketsUiState(
                    isLoading = false,
                    markets = flow {}
                ), listener = MarketInteractionListener
                )
            }
        }
    }
}