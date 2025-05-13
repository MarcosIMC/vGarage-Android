package com.mimc_software.vgarageandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mimc_software.vgarageandroid.addGarage.ui.AddGarageScreen
import com.mimc_software.vgarageandroid.landing.ui.LandingScreen
import com.mimc_software.vgarageandroid.ui.theme.VGarageAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VGarageAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navigationContoller = rememberNavController()
                    NavHost(navController = navigationContoller, startDestination = Navigation.Landing.route) {
                        composable(Navigation.Landing.route) { LandingScreen(modifier = Modifier.padding(innerPadding).background(color = colorResource(id = R.color.appColor)), navigationContoller) }
                        composable(Navigation.AddGarage.route) { AddGarageScreen(modifier = Modifier.padding(innerPadding), navigationContoller) }
                    }
                }
            }
        }
    }
}