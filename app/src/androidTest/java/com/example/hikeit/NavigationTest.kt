package com.example.hikeit

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HikeActivity>()

    @Before
    fun setup() = hiltRule.inject()

    @Test
    fun firstScreen_isSearchScreen() {
        composeTestRule.apply {
            onNodeWithTag("Search Screen").assertExists()
        }
    }


}