package com.mimc_software.vgarageandroid

import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.mimc_software.vgarageandroid.addGarage.ui.AddGarageScreen
import com.mimc_software.vgarageandroid.addGarage.ui.AddGarageScreenViewModel
import com.mimc_software.vgarageandroid.landing.ui.LandingScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LandingScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        hiltRule.inject()

        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        ).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        composeRule.setContent {
            val addGarageViewModel: AddGarageScreenViewModel = hiltViewModel()

            NavHost(
                navController = navController,
                startDestination = "landing"
            ) {
                composable("landing") {
                    LandingScreen(
                        modifier = Modifier,
                        navigationController = navController
                    )
                }
                composable("addGarage") {
                    AddGarageScreen(
                        modifier = Modifier,
                        navigationController = navController,
                        addGarageScreenViewModel = addGarageViewModel,
                        onNavigateToMain = {
                            navController.navigate("main")
                        }
                    )
                }
            }
        }
    }

    @Test
    fun show_all_elements_in_the_view() {
        composeRule.onNodeWithText("Olvidé el último mantenimiento").assertExists()
        composeRule.onNodeWithText("Se me pasó la ITV.").assertExists()
        composeRule.onNodeWithText("¿Dónde aparqué?").assertExists()
        composeRule.onNodeWithTag("add_garage_btn").assertExists()
    }

    /*@Test
    fun click_button_and_open_add_garage_view() {
        composeRule.onNodeWithTag("add_garage_btn").performClick()
        assert(navController.currentDestination?.route == "addGarage")
    }*/
}
