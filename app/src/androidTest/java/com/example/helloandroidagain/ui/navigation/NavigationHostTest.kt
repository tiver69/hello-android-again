package com.example.helloandroidagain.ui.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationHostTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            NavigationHost(navController)
        }
    }

    @Test
    fun testInitialScreenIsRoll() {
        composeTestRule
            .onNodeWithContentDescription("${Roll.name} Screen")
            .assertIsDisplayed()
    }

    @Test
    fun testArchiveNavigation() {
        composeTestRule
            .onNodeWithContentDescription("Go to ${Archive.route}")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("${Archive.name} Screen")
            .assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, Archive.routeWithArgs)
    }
}
