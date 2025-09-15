package com.mimc_software.vgarageandroid

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.mimc_software.vgarageandroid.addVehicle.AddVehicleViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainScreenTest {
    @get:Rule
    val composeMainScreen = createComposeRule()
    lateinit var navigationController: TestNavHostController
//    val hiltRule = HiltAndroidRule(this)
    private lateinit var addVehicleViewModel: AddVehicleViewModel

    @Test
    fun show_all_elements_in_the_view() {
        composeMainScreen.onNodeWithTag("bt_fab").assertExists()
        composeMainScreen.onNodeWithTag("btn_settings").assertExists()
    }
}