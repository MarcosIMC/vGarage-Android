package com.mimc_software.vgarageandroid.addMaintenance.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mimc_software.vgarageandroid.R

@Composable
fun AddMaintenanceScreen(
    modifier: Modifier,
    navigationController: NavHostController,
    addMaintenanceViewModel: AddMaintenanceViewModel,
    vehicleId: String
) {
    AppBar(navigationController, vehicleId)
    Body(modifier, addMaintenanceViewModel, navigationController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navigationController: NavHostController, vehicleId: String) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.appColor),
            titleContentColor = colorResource(R.color.white),
        ),
        title = { Text("Nuevo Mantenimiento") },
        navigationIcon = {
            IconButton(
                onClick = {navigationController.popBackStack() },
                colors = IconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = colorResource(R.color.textWhite),
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = colorResource(R.color.textWhite)
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Vuelta a main"
                )
            }
        }
    )
}

@Composable
fun Body(modifier: Modifier, addMaintenanceViewModel: AddMaintenanceViewModel, navHostController: NavHostController) {
    Column(
        modifier = modifier.fillMaxSize().padding(20.dp)
    ) {

    }
}