package com.mimc_software.vgarageandroid.main.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mimc_software.vgarageandroid.R
import com.mimc_software.vgarageandroid.addGarage.ui.model.GarageModel

@Composable
fun MainScreen(
    modifier: Modifier,
    navigationController: NavHostController,
    mainScreenViewModel: MainScreenViewModel,
    activeGarage: GarageModel?
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val uiState by mainScreenViewModel.uiState.collectAsState()

    LaunchedEffect(activeGarage?.uid, navigationController.currentBackStackEntry) {
        activeGarage?.let {
            Log.d("MainScreen","Garage ID: ${it.uid}")
            mainScreenViewModel.onGetVehicles(it.uid)
        }
    }

    val feedbackMessage by mainScreenViewModel.feedbackMessage.collectAsState()
    LaunchedEffect(feedbackMessage) {
        feedbackMessage?.let {
            snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
            mainScreenViewModel.clearFeedbackMessage()
        }
    }

    Scaffold(
        topBar = { AppBar(activeGarage) },
        floatingActionButton = { FabAdd(navigationController, activeGarage?.uid) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Body(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            uiState = uiState
        )
    }
}
@Composable
fun FabAdd(navigationController: NavHostController, garageId: String?) {
    FloatingActionButton(
        onClick = {
            garageId?.let {
                navigationController.navigate("addVehicle/$it")
            }
        },
        containerColor = colorResource(R.color.appColor),
        contentColor = colorResource(R.color.textWhite)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Añadir vehículo")
    }
}

@Composable
fun Body(modifier: Modifier, uiState: GetVehiclesUiState) {
    when(uiState) {
        is GetVehiclesUiState.Idle -> {

        }
        is GetVehiclesUiState.Loading -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
                CircularProgressIndicator()
            }
        }
        is GetVehiclesUiState.Empty -> {
            Log.d("MainScreen","Dentro del error empty")
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
                Text("No hay vehículos, añade uno para que aparezca aquí")
            }
        }
        is GetVehiclesUiState.Loaded -> {
            Log.d("MainScreen","Dentro del loaded")
            LazyColumn(modifier = modifier) {
                items(uiState.vehicles) { vehicle ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(vehicle.displayName)
                    }

                }
            }
        }
        is GetVehiclesUiState.Error -> {
            //TODO: Add an error message
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(activeGarage: GarageModel?) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.appColor),
            titleContentColor = colorResource(R.color.textWhite)
        ),
        title = {
            Text(activeGarage?.name ?: "error")
        },
        actions = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings options",
                    tint = colorResource(R.color.textWhite),
                    modifier = Modifier.testTag("btn_settings")
                )
            }
        }
    )
}