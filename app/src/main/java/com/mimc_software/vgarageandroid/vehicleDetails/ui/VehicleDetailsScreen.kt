package com.mimc_software.vgarageandroid.vehicleDetails.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mimc_software.vgarageandroid.Navigation
import com.mimc_software.vgarageandroid.R
import com.mimc_software.vgarageandroid.addVehicle.ui.model.VehicleModel
import com.mimc_software.vgarageandroid.vehicleDetails.ui.model.MaintenanceModel

@Composable
fun VehicleDetailsScreen(
    navigationController: NavHostController,
    vehicleDetailsScreenViewModel: VehicleDetailsScreenViewModel,
    vehicleId: String
) {
    val uiState by vehicleDetailsScreenViewModel.uiState.collectAsState()
    val uiStateMaintenance by vehicleDetailsScreenViewModel.uiStateMaintenance.collectAsState()
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(vehicleId) {
        vehicleDetailsScreenViewModel.onGetVehicleDetails(vehicleId)
        vehicleDetailsScreenViewModel.onGetVehicleMaintenances(vehicleId)
    }

    DisposableEffect(lifecycleOwner, vehicleId) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                vehicleDetailsScreenViewModel.onGetVehicleMaintenances(vehicleId)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
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
        floatingActionButton = {
            FabAdd(
                navigationController = navigationController,
                vehicleId = vehicleId,
                selectedTab = selectedTab
            )
        }
    ) { paddingValues ->
        Body(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            uiState = uiState,
            uiStateMaintenance = uiStateMaintenance,
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
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
fun FabAdd(
    navigationController: NavHostController,
    vehicleId: String,
    selectedTab: Int
) {
    FloatingActionButton(
        onClick = {
            when (selectedTab) {
                0 -> navigationController.navigate(Navigation.AddMaintenance.createRoute(vehicleId))
                1 -> navigationController.navigate("addParking/$vehicleId")
            }
        },
        containerColor = colorResource(R.color.appColor),
        contentColor = colorResource(R.color.textWhite)
    ) {
        Icon(
            imageVector = when (selectedTab) {
                0 -> Icons.Filled.Build
                1 -> Icons.Filled.Place
                else -> Icons.Filled.Add
            },
            contentDescription = when (selectedTab) {
                0 -> "Añadir mantenimiento"
                1 -> "Añadir estacionamiento"
                else -> "Añadir"
            }
        )
    }
}

@Composable
fun Body(
    modifier: Modifier,
    uiState: VehicleDetailsUiState,
    uiStateMaintenance: VehicleMaintenanceUiState,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
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
            NavigationTab(
                modifier = modifier,
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
                vehicle = uiState.vehicleDetails,
                uiState = uiState,
                uiStateMaintenance = uiStateMaintenance
            )
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

@Composable
fun NavigationTab(
    modifier: Modifier,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    vehicle: VehicleModel,
    uiState: VehicleDetailsUiState.Loaded,
    uiStateMaintenance: VehicleMaintenanceUiState
) {
    val tabs = listOf("Mantenimiento", "Estacionamiento")
    val icons = listOf(Icons.Filled.Build, Icons.Filled.Place)

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab, contentColor = colorResource(R.color.appColor)) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { onTabSelected(index) },
                    text = { Text(title) },
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = title
                        )
                    }
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (selectedTab) {
                0 -> VehicleMaintenance(
                    vehicle = vehicle,
                    uiState = uiStateMaintenance
                )
                1 -> Text("Contenido de Estacionamiento")
            }
        }
    }
}

@Composable
fun VehicleMaintenance(vehicle: VehicleModel, uiState: VehicleMaintenanceUiState) {
    when (uiState) {
        is VehicleMaintenanceUiState.Idle -> {

        }
        is VehicleMaintenanceUiState.Loading -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator()
            }
        }
        is VehicleMaintenanceUiState.Loaded -> {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(Modifier.fillMaxWidth().height(100.dp)) {
                    if (vehicle.image.isNotBlank()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(vehicle.image)
                                .crossfade(true)
                                .build(),
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Vehicle Image",
                        )
                    } else {
                        Spacer(modifier = Modifier.size(60.dp))
                    }

                    Spacer(modifier = Modifier.padding(16.dp))
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Año: ${vehicle.year}")
                        Text("ITV: ${vehicle.revision}")

                        IconButton(onClick = { /* Acción para mostrar notas */ }) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Notas",
                                tint = colorResource(R.color.appColor)
                            )
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(Modifier.height(8.dp))

                LazyColumn() {
                    items(uiState.maintenances) { maintenance ->
                        MaintenanceItem(maintenance)
                    }
                }
            }
        }
        is VehicleMaintenanceUiState.Empty -> {

        }
    }
}

@Composable
fun MaintenanceItem(maintenance: MaintenanceModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(72.dp)
            .padding(20.dp)
            .clickable { /* Acción para mostrar detalles del mantenimiento */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(maintenance.title)
            Text(maintenance.date)
        }
    }
}
