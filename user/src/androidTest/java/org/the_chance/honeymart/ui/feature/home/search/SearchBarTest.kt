package org.the_chance.honeymart.ui.feature.home.search

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth
import org.junit.Rule
import org.junit.Test
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.home.composables.SearchBar

class SearchBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun checkTextIsDisplayed() {
        composeTestRule.setContent {
            SearchBar(
                icon = painterResource(id = R.drawable.ic_search),
                text = "search",
                onClick = { /*TODO*/ })
        }

        composeTestRule.onNodeWithText("search").assertIsDisplayed()
    }

    @Test
    fun checkIconIsDisplayed() {
        composeTestRule.setContent {
            SearchBar(
                icon = painterResource(id = R.drawable.ic_search),
                onClick = { /*TODO*/ })
        }

        composeTestRule.onNodeWithContentDescription("SearchIcon").assertIsDisplayed()
    }

    @Test
    fun checkSearchBarClick() {
        var isClicked = false

        composeTestRule.setContent {
            SearchBar(
                icon = painterResource(id = R.drawable.ic_search),
                onClick = { isClicked = true })
        }
        composeTestRule.onNodeWithContentDescription("SearchWidget").performClick()
        Truth.assertThat(isClicked).isTrue()
    }

    @Test
    fun searchBar_DisplaysText_Search() {
        testDisplayText("search")
    }

    @Test
    fun searchBar_DisplaysText_Find() {
        testDisplayText("find")
    }

    @Test
    fun searchBar_DisplaysText_Lookup() {
        testDisplayText("lookup")
    }

    private fun testDisplayText(text: String) {
        composeTestRule.setContent {
            SearchBar(
                icon = painterResource(id = R.drawable.ic_search),
                text = text,
                onClick = { /*TODO*/ }
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }

}