package com.mimc_software.vgarageandroid.vehicleDetails.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import com.mimc_software.vgarageandroid.R

@Composable
fun VehicleDetailsScreen(
    navigationController: NavHostController,
    vehicleDetailsScreenViewModel: VehicleDetailsScreenViewModel,
    vehicleId: String
) {
    val uiState by vehicleDetailsScreenViewModel.uiState.collectAsState()

    LaunchedEffect(vehicleId) {
        vehicleDetailsScreenViewModel.onGetVehicleDetails(vehicleId)
    }

    Scaffold(
        topBar = {
            AppBar(
                vehicleName = when(uiState) {
                    is VehicleDetailsUiState.Loaded -> (uiState as VehicleDetailsUiState.Loaded).vehicleDetails.displayName
                    else -> ""
                },
                navigationController
            )
        },
        floatingActionButton = { FabAdd(navigationController, vehicleId = vehicleId) }
    ) { paddingValues ->
        Body(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            uiState = uiState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(vehicleName: String, navigationController: NavHostController) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.appColor),
            titleContentColor = colorResource(R.color.textWhite)
        ),
        title = {
            Text(vehicleName)
        },
        navigationIcon = {
            IconButton(
                onClick = { navigationController.navigate("main") },
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
fun FabAdd(navigationController: NavHostController, vehicleId: String) {
    FloatingActionButton(
        onClick = {
            navigationController.navigate("addMaintenance/$vehicleId")
        },
        containerColor = colorResource(R.color.appColor),
        contentColor = colorResource(R.color.textWhite)
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Añadir mantenimiento"
        )
    }
}

@Composable
fun Body(
    modifier: Modifier,
    uiState: VehicleDetailsUiState
) {
    when(uiState) {
        is VehicleDetailsUiState.Idle -> {

        }
        is VehicleDetailsUiState.Loading -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
                CircularProgressIndicator()
            }
        }
        is VehicleDetailsUiState.Loaded -> {
            Text("Loaded: ${uiState.vehicleDetails.displayName}")
        }
        is VehicleDetailsUiState.Empty -> {
            Log.d("MainScreen","Dentro del error empty")
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
                Text("No se pudo cargar los datos de su vehículo, inténtelo de nuevo más tarde")
            }
        }
        is VehicleDetailsUiState.Error -> {
            Text("Error: ${uiState.message}")
        }
    }
}