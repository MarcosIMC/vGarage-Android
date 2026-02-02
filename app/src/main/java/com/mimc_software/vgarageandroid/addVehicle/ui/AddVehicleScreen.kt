package com.mimc_software.vgarageandroid.addVehicle.ui

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.mimc_software.vgarageandroid.R
import com.mimc_software.vgarageandroid.ui.theme.customComponents.CustomOutlinedTextField
import com.mimc_software.vgarageandroid.ui.theme.customComponents.DatePickerFieldToModal
import androidx.core.net.toUri
import androidx.navigation.compose.rememberNavController
import com.mimc_software.vgarageandroid.Navigation
import com.mimc_software.vgarageandroid.addVehicle.ui.model.VehicleModel
import com.mimc_software.vgarageandroid.ui.theme.customComponents.galleryLauncher
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun AddVehicle(
    modifier: Modifier,
    navigationController: NavHostController,
    addVehicleViewModel: AddVehicleViewModel,
    garageId: String
) {
    val state by addVehicleViewModel.uiState.collectAsState()

    // Set garageId when composable is launched
    LaunchedEffect(garageId) {
        addVehicleViewModel._onGarageIdChange(garageId)
    }

    AppBar(navigationController)
    Body(modifier, addVehicleViewModel, state, navigationController)
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
fun Body(
    modifier: Modifier,
    addVehicleViewModel: AddVehicleViewModel,
    state: AddVehicleDataUiState,
    navigationController: NavHostController
) {
    val galleryLauncher = galleryLauncher { uri ->
        if (uri != null) {
            addVehicleViewModel._onVehicleImageChange(uri.toString())
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            LoadImageVehicle(
                imageUriString = state.vehicleImage,
                modifier = modifier.size(120.dp),
                addVehicleViewModel
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier,
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ElevatedButton(
                    onClick = {
                        if (state.vehicleImage == null) {
                            galleryLauncher.launch("image/*")
                        } else {
                            addVehicleViewModel._onVehicleImageChange(null)
                        }
                    },
                    Modifier.fillMaxWidth(0.6f),
                    colors = ButtonColors(
                        containerColor = colorResource(R.color.white),
                        contentColor = colorResource(R.color.appColor),
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent
                    )
                ) {
                    Text(if (state.vehicleImage == null) "Añadir foto" else "Eliminar foto")
                }

                CameraButton(addVehicleViewModel)
            }
        }

        HorizontalDivider(Modifier, DividerDefaults.Thickness, colorResource(R.color.appColor))

        AddVehicleForm(modifier, addVehicleViewModel, state, navigationController)
    }
}

@Composable
fun LoadImageVehicle(
    imageUriString: String?,
    modifier: Modifier = Modifier,
    addVehicleViewModel: AddVehicleViewModel,
    size: Dp = 100.dp,
    borderColor: Color = colorResource(R.color.appColor),
    borderWidth: Dp = 3.dp
) {

    val galleryLauncher = galleryLauncher { uri ->
        if (uri != null) {
            addVehicleViewModel._onVehicleImageChange(uri.toString())
        }
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .border(borderWidth, borderColor, CircleShape)
            .clickable {
                galleryLauncher.launch("image/*")
            },
        contentAlignment = Alignment.Center
    ) {
        if (!imageUriString.isNullOrEmpty()) {
            val context = LocalContext.current
            val bitmap = remember(imageUriString) {
                // Convertimos la URI String a Uri
                val uri = imageUriString.toUri()
                // Decodificamos a Bitmap desde ContentResolver
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)
                }
            }

            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Foto del vehículo",
                    modifier = Modifier.size(size),
                    contentScale = ContentScale.Crop
                )
            } else {
                // fallback si no se pudo decodificar
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_photo_alternate_24px),
                    contentDescription = "Cámara",
                    modifier = Modifier.size(size),
                    tint = colorResource(R.color.appColor)
                )
            }
        } else {
            // fallback si no hay imagen
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.add_photo_alternate_24px),
                contentDescription = "Cámara",
                modifier = Modifier.size(size),
                tint = colorResource(R.color.appColor)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun AddVehicleForm(
    modifier: Modifier,
    addVehicleViewModel: AddVehicleViewModel,
    state: AddVehicleDataUiState,
    navigationController: NavHostController
) {
    val uiEvent by addVehicleViewModel.uiEvent.collectAsState(initial = null)

    LaunchedEffect(uiEvent) {
        when(uiEvent) {
            is AddVehicleUiEvent.NavigationToMain -> {
                navigationController.navigate(Navigation.Main.route) {
                    popUpTo(Navigation.Main.route) {
                        inclusive = true
                    }
                }

                navigationController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("feedbackMessage", "El vehículo se añadió correctamente.")
            }
            else -> Unit
        }
    }

    var vehicleNameFieldIsTouched by rememberSaveable { mutableStateOf(false) }
    var vehicleModelFieldIsTouched by rememberSaveable { mutableStateOf(false) }
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
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    vehicleNameFieldIsTouched = true
                }
            },
        singleLine = true,
        colors = CustomOutlinedTextField.outlinedTextFieldColorsForm(),
        isError = addVehicleViewModel.vehicleNameHasError && vehicleNameFieldIsTouched,
        supportingText = {
            if (addVehicleViewModel.vehicleNameHasError && vehicleNameFieldIsTouched) {
                Text(
                    text = "El nombre del vehículo no puede estar vacío",
                    color = Color.Red
                )
            }
        }
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
        modifier = Modifier.fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    vehicleModelFieldIsTouched = true
                }
            },
        singleLine = true,
        colors = CustomOutlinedTextField.outlinedTextFieldColorsForm(),
        isError = addVehicleViewModel.vehicleModelHasError && vehicleModelFieldIsTouched,
        supportingText = {
            if (addVehicleViewModel.vehicleModelHasError && vehicleModelFieldIsTouched) {
                Text(
                    text = "El modelo del vehículo no puede estar vacío",
                    color = Color.Red
                )
            }
        }
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
        onClick = {
            Log.d("AddVehicleScreen", "Creating vehicle with garageId: ${state.garageId}")
            val newVehicle = VehicleModel(
                uid = Uuid.random().toString(),
                brand = state.vehicleName,
                model = state.vehicleBrand,
                year = state.vehicleYear.toString(),
                revision = state.vehicleRevision?.toString() ?: "",
                image = state.vehicleImage ?: "",
                others = state.vehicleOthers,
                displayName = "${state.vehicleName} ${state.vehicleBrand}",
                garageId = state.garageId
            )
            Log.d("AddVehicleScreen", "Vehicle created with garageId: ${newVehicle.garageId}")
            addVehicleViewModel.onAddVehicle(newVehicle)
        },
        modifier.fillMaxWidth(),
        enabled = addVehicleViewModel.validateForm(),
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

@Composable
fun CameraButton(addVehicleViewModel: AddVehicleViewModel) {
    val context = LocalContext.current

    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            addVehicleViewModel.onCameraOpen()
        } else {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            addVehicleViewModel.onPhotoCaptured(saveBitmapToGallery(context, it))
        }
    }

    LaunchedEffect(Unit) {
        addVehicleViewModel.uiCameraEvent.collect { event ->
            when (event) {
                is AddVehicleUiCameraEvent.OpenCamera -> {
                    cameraLauncher.launch(null)
                }
            }
        }
    }

    FilledTonalButton(
        onClick = {
            when {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    addVehicleViewModel.onCameraOpen()
                }

                else -> {
                    permissionsLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        },
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

fun saveBitmapToGallery(
    context: Context,
    bitmap: Bitmap
): Uri {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "vehicle_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/vGarage")
        put(MediaStore.Images.Media.IS_PENDING, 1)
    }
    val resolver = context.contentResolver
    val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    val imageUri = resolver.insert(collection, contentValues)

    if (imageUri != null) {
        resolver.openOutputStream(imageUri)?.use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        contentValues.clear()
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        resolver.update(imageUri, contentValues, null, null)
    }
    return imageUri!!
}
