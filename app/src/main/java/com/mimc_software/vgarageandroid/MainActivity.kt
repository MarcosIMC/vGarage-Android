package com.mimc_software.vgarageandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mimc_software.vgarageandroid.addGarage.ui.AddGarageScreen
import com.mimc_software.vgarageandroid.addGarage.ui.AddGarageScreenViewModel
import com.mimc_software.vgarageandroid.addVehicle.AddVehicle
import com.mimc_software.vgarageandroid.addVehicle.AddVehicleViewModel
import com.mimc_software.vgarageandroid.landing.ui.LandingScreen
import com.mimc_software.vgarageandroid.main.ui.MainScreen
import com.mimc_software.vgarageandroid.main.ui.MainScreenViewModel
import com.mimc_software.vgarageandroid.ui.theme.VGarageAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val addGarageScreenViewModel: AddGarageScreenViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private val mainScreenViewModel: MainScreenViewModel by viewModels()
    private val addVehicleViewModel: AddVehicleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val launchDestination = mainActivityViewModel.startDestination
            VGarageAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (launchDestination == null) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    } else {
                        val navigationController = rememberNavController()
                        NavHost(navController = navigationController, startDestination = launchDestination) {
                            composable(Navigation.Landing.route) { LandingScreen(modifier = Modifier.padding(innerPadding).background(color = colorResource(id = R.color.appColor)), navigationController) }
                            composable(Navigation.AddGarage.route) { AddGarageScreen(
                                modifier = Modifier.padding(innerPadding),
                                navigationController,
                                addGarageScreenViewModel,
                                onNavigateToMain = { navigationController.navigate(Navigation.Main.route) }
                            ) }
                            composable(Navigation.Main.route) { MainScreen(Modifier.padding(innerPadding), navigationController, mainScreenViewModel, mainActivityViewModel.activeGarage) }
                            composable(Navigation.AddVehicle.route) { AddVehicle(Modifier.padding(innerPadding), navigationController, addVehicleViewModel) }
                        }
                    }
                }
            }
        }
    }
}
