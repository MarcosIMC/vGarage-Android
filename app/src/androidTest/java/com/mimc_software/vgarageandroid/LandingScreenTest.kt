package com.mimc_software.vgarageandroid

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.mimc_software.vgarageandroid.landing.ui.LandingScreen
import org.junit.Rule
import org.junit.Test

class LandingScreenTest {
    @get:Rule
    val composeLandingScreen = createComposeRule()

    @Test
    fun show_all_elements_in_the_view() {
        composeLandingScreen.setContent {
            LandingScreen(modifier = Modifier, navigationContoller = navigationContoller)
        }

        composeLandingScreen.onNodeWithText("Texto prueba 1").assertExists()
        composeLandingScreen.onNodeWithText("Texto prueba 2").assertExists()
        composeLandingScreen.onNodeWithText("Texto prueba 3").assertExists()
        composeLandingScreen.onNodeWithTag("add_garage_btn").assertExists()
    }
}