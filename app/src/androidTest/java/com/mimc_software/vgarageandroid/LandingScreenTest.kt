package com.mimc_software.vgarageandroid

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.mimc_software.vgarageandroid.addGarage.ui.AddGarageScreen
import com.mimc_software.vgarageandroid.addGarage.ui.AddGarageScreenViewModel
import com.mimc_software.vgarageandroid.landing.ui.LandingScreen
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LandingScreenTest {
    @get:Rule
    val composeLandingScreen = createComposeRule()
    lateinit var navigationController: TestNavHostController
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
        navigationController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navigationController.navigatorProvider.addNavigator(ComposeNavigator())

        composeLandingScreen.setContent {
            val addGarageScreenViewModel: AddGarageScreenViewModel = viewModel()
            // Aquí es donde pasas el controlador al NavHost de tu app
            //LandingScreen(modifier = Modifier, navigationContoller = navigationController)
            NavHost(navController = navigationController, startDestination = "landing") {
                composable("landing") { LandingScreen(modifier = Modifier, navigationController) }
                composable("addGarage") { AddGarageScreen(
                    modifier = Modifier,
                    navigationController,
                    addGarageScreenViewModel,
                    onNavigateToMain = { navigationController.navigate(Navigation.Main.route) }
                ) }
            }
        }
    }


    @Test
    fun show_all_elements_in_the_view() {
        /*composeLandingScreen.setContent {
            LandingScreen(modifier = Modifier, navigationContoller = navigationContoller)
        }*/

        composeLandingScreen.onNodeWithText("Texto prueba 1").assertExists()
        composeLandingScreen.onNodeWithText("Texto prueba 2").assertExists()
        composeLandingScreen.onNodeWithText("Texto prueba 3").assertExists()
        composeLandingScreen.onNodeWithTag("add_garage_btn").assertExists()
    }

    @Test
    fun click_button_and_open_add_garage_view() {
        composeLandingScreen.onNodeWithTag("add_garage_btn").performClick()
        assert(navigationController.currentDestination?.route == "addGarage")
    }
}