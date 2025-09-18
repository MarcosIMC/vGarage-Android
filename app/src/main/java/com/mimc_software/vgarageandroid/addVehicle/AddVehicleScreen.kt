package com.mimc_software.vgarageandroid.addVehicle

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mimc_software.vgarageandroid.R
import com.mimc_software.vgarageandroid.ui.theme.customComponents.CustomOutlinedTextField
import com.mimc_software.vgarageandroid.ui.theme.customComponents.DatePickerFieldToModal

@Composable
fun AddVehicle(
    modifier: Modifier,
    navigationController: NavHostController,
    addVehicleViewModel: AddVehicleViewModel
) {
    val state by addVehicleViewModel.uiState.collectAsState()

    AppBar(navigationController)
    Body(modifier, addVehicleViewModel, state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navigationController: NavHostController) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.appColor),
            titleContentColor = colorResource(R.color.textWhite)
        ),
        title = { Text("Nuevo vehículo") },
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
fun Body(modifier: Modifier, addVehicleViewModel: AddVehicleViewModel, state: AddVehicleUiState) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            IconButton(
                onClick = {},
                modifier = modifier
                    .size(100.dp)
                    .border(3.dp, colorResource(R.color.appColor), CircleShape)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_photo_alternate_24px),
                    contentDescription = "Añadir foto de la galería",
                    tint = colorResource(R.color.appColor),
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier,
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ElevatedButton(
                    onClick = { /* TODO: Implement vehicle addition logic */ },
                    Modifier.fillMaxWidth(0.6f),
                    colors = ButtonColors(
                        containerColor = colorResource(R.color.white),
                        contentColor = colorResource(R.color.appColor),
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent
                    )
                ) {
                    Text("Añadir foto")
                }

                FilledTonalButton(
                    onClick = {},
                    Modifier.fillMaxWidth(0.6f),
                    colors = ButtonColors(
                        containerColor = colorResource(R.color.appColor),
                        contentColor = colorResource(R.color.white),
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.photo_camera_24px),
                        contentDescription = "Abrir cámara"
                    )
                }
            }
        }

        HorizontalDivider(Modifier, DividerDefaults.Thickness, colorResource(R.color.appColor))

        AddVehicleForm(modifier, addVehicleViewModel, state)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVehicleForm(
    modifier: Modifier,
    addVehicleViewModel: AddVehicleViewModel,
    state: AddVehicleUiState
) {
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        value = state.vehicleName,
        onValueChange = { addVehicleViewModel._onVehicleNameChange(it) },
        label = { Text("Marca") },
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.directions_car_24px),
                contentDescription = "Car icon"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = CustomOutlinedTextField.outlinedTextFieldColorsForm(),
    )

    OutlinedTextField(
        value = state.vehicleBrand,
        onValueChange = { addVehicleViewModel._onVehicleBrandChange(it) },
        label = { Text("Modelo") },
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.car_tag_24px),
                contentDescription = "Car icon"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = CustomOutlinedTextField.outlinedTextFieldColorsForm(),
    )

    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        DatePickerFieldToModal(
            modifier = Modifier.weight(1f),
            value = state.vehicleYear,
            onValueChange = { addVehicleViewModel._onVehicleYearChange(it) },
            label = "Año"
        )

        DatePickerFieldToModal(
            modifier = Modifier.weight(1f),
            value = state.vehicleRevision,
            onValueChange = { addVehicleViewModel._onVehicleRevisionChange(it) },
            label = "ITV"
        )
    }

    OutlinedTextField(
        value = state.vehicleOthers,
        onValueChange = { addVehicleViewModel._onVehicleOthersChange(it) },
        label = { Text("Notas") },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f),
        colors = CustomOutlinedTextField.outlinedTextFieldColorsForm(),
    )

    FilledTonalButton(
        onClick = {},
        modifier.fillMaxWidth(),
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
                contentDescription = "Añadir vehículo"
            )
            Text("Añadir vehículo")
        }
    }
}
