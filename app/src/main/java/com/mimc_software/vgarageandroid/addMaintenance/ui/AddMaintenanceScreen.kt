package com.mimc_software.vgarageandroid.addMaintenance.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mimc_software.vgarageandroid.R
import com.mimc_software.vgarageandroid.ui.theme.customComponents.CustomOutlinedTextField
import com.mimc_software.vgarageandroid.ui.theme.customComponents.DatePickerFieldToModal
import com.mimc_software.vgarageandroid.vehicleDetails.ui.model.MaintenanceModel
import java.time.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun AddMaintenanceScreen(
    modifier: Modifier,
    navigationController: NavHostController,
    addMaintenanceViewModel: AddMaintenanceViewModel,
    vehicleId: String
) {
    val state by addMaintenanceViewModel.uiState.collectAsState()
    val operationState by addMaintenanceViewModel.uiStateFlow.collectAsState()

    LaunchedEffect(operationState) {
        if (operationState is AddMaintenanceUiState.Success) {
            navigationController.popBackStack()
        }
    }

    AppBar(navigationController, vehicleId)
    Body(modifier, addMaintenanceViewModel, state, navigationController, vehicleId)
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
fun Body(
    modifier: Modifier, addMaintenanceViewModel: AddMaintenanceViewModel, state: AddMaintenanceDataUiState,
    navHostController: NavHostController, vehicleId: String
) {
    Column(
        modifier = modifier.fillMaxSize().padding(80.dp)
    ) {
        AddMaintenanceForm(
            modifier,
            addMaintenanceViewModel = addMaintenanceViewModel,
            state,
            navigationController = navHostController,
            vehicleId
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@Composable
fun AddMaintenanceForm(
    modifier: Modifier,
    addMaintenanceViewModel: AddMaintenanceViewModel,
    state: AddMaintenanceDataUiState,
    navigationController: NavHostController,
    vehicleId: String
) {
    var maintenanceTitleFieldIsTouched by remember { mutableStateOf(false) }
    var selectedMaintenance by rememberSaveable { mutableStateOf(MaintenanceType.OTHERS) }

    OutlinedTextField(
        value = state.maintenanceTitle,
        onValueChange = { addMaintenanceViewModel.onMaintenanceTitleChange(it) },
        label = { Text("Título del mantenimiento") },
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.build_24px),
                contentDescription = "Icono de mantenimiento"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    maintenanceTitleFieldIsTouched = true
                }
            },
        singleLine = true,
        colors = CustomOutlinedTextField.outlinedTextFieldColorsForm(),
        isError = addMaintenanceViewModel.maintenanceTitleHasError && maintenanceTitleFieldIsTouched,
        supportingText = {
            if (addMaintenanceViewModel.maintenanceTitleHasError && maintenanceTitleFieldIsTouched) {
                Text("El título del mantenimiento no puede estar vacío", color = Color.Red)
            }
        }
    )

    HorizontalDivider(Modifier, DividerDefaults.Thickness, colorResource(R.color.appColor))
    Text("Datos del mantenimiento", color = colorResource(R.color.appColor), modifier = Modifier.padding(8.dp))
    DropDownMaintenanceMenu(
        selectedMaintenance = selectedMaintenance,
        onMaintenanceSelected = { selectedMaintenance = it },
        modifier = modifier
    )
    DatePickerFieldToModal(
        modifier = modifier,
        value = state.maintenanceDate,
        onValueChange = { addMaintenanceViewModel.onMaintenanceDateChange(it) },
        label = "Fecha"
    )
    OutlinedTextField(
        value = state.maintenancePrice?.toString() ?: "",
        onValueChange = { addMaintenanceViewModel.onMaintenancePriceChange(it.toLongOrNull()) },
        label = { Text("Precio") },
        modifier = Modifier.fillMaxWidth(),
        colors = CustomOutlinedTextField.outlinedTextFieldColorsForm(),
        trailingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.euro_24),
                contentDescription = "Icono de precio",
                tint = colorResource(R.color.appColor)
            )
        }
    )
    OutlinedTextField(
        value = state.maintenanceNotes,
        onValueChange = { addMaintenanceViewModel.onMaintenanceNotesChange(it) },
        label = { Text("Notas") },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f),
        colors = CustomOutlinedTextField.outlinedTextFieldColorsForm(),
    )

    FilledTonalButton(
        onClick = {
            Log.d("AddMaintenance", "Creating maintenance for vehicleId: ${state.vehicleId}")
            val newMaintenance = MaintenanceModel(
                uid = Uuid.random().toString(),
                title = state.maintenanceTitle,
                kindMaintenance = selectedMaintenance.name,
                date = state.maintenanceDate ?: LocalDate.now(),
                price = state.maintenancePrice?.toDouble() ?: 0.0,
                description = state.maintenanceNotes,
                vehicleId = vehicleId
            )
            Log.d("AddMaintenance", "Maintenance created with maintenanceId: ${newMaintenance.uid}")
            addMaintenanceViewModel.onAddMaintenance(newMaintenance)
        },
        modifier.fillMaxWidth(),
        enabled = addMaintenanceViewModel.validateForm(),
        colors = ButtonColors(
            containerColor = colorResource(R.color.appColor),
            contentColor = colorResource(R.color.white),
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.save_24px),
                contentDescription = "Añadir mantenimiento"
            )
            Text("Añadir mantenimiento")
        }
    }

}
