package com.mimc_software.vgarageandroid.addMaintenance.ui

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.mimc_software.vgarageandroid.R

data class MaintenanceOptions(
    val icon: Int,
    val maintenance: String,
    val maintenanceType: MaintenanceType
)

enum class MaintenanceType {
    LIGHTS,
    OIL,
    TIRES,
    LIQUIDS,
    BRAKES,
    ENGINE,
    SUSPENSION,
    OTHERS
}

private val maintenanceOptions = listOf(
    MaintenanceOptions(R.drawable.headlight, "Luces", MaintenanceType.LIGHTS),
    MaintenanceOptions(R.drawable.oil_change, "Aceite", MaintenanceType.OIL),
    MaintenanceOptions(R.drawable.wheel, "Neumáticos", MaintenanceType.TIRES),
    MaintenanceOptions(R.drawable.coolant_temperature, "Líquidos", MaintenanceType.LIQUIDS),
    MaintenanceOptions(R.drawable.brake_disc, "Frenos", MaintenanceType.BRAKES),
    MaintenanceOptions(R.drawable.engine, "Motor", MaintenanceType.ENGINE),
    MaintenanceOptions(R.drawable.suspension, "Suspensión", MaintenanceType.SUSPENSION),
    MaintenanceOptions(R.drawable.maintenance, "Otros", MaintenanceType.OTHERS)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMaintenanceMenu(
    selectedMaintenance: MaintenanceType,
    onMaintenanceSelected: (MaintenanceType) -> Unit,
    modifier: Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedOption = remember(selectedMaintenance) {
        maintenanceOptions.find { it.maintenanceType == selectedMaintenance } ?: maintenanceOptions.last()
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedOption.maintenance,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.menuAnchor(
                type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                enabled = true
            ),
            label = { Text("Tipo de Mantenimiento") },
            trailingIcon = {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    maintenanceOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.maintenance) },
                            leadingIcon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(option.icon),
                                    contentDescription = option.maintenance
                                )
                            },
                            onClick = {
                                onMaintenanceSelected(option.maintenanceType)
                                expanded = false
                            }
                        )
                    }
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(selectedOption.icon),
                    contentDescription = selectedOption.maintenance
                )
            },
        )
    }
}
