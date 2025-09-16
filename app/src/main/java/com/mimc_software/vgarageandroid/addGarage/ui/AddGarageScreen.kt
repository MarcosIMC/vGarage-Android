package com.mimc_software.vgarageandroid.addGarage.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mimc_software.vgarageandroid.R

@Composable
fun AddGarageScreen(
    modifier: Modifier,
    navigationController: NavHostController,
    addGarageScreenViewModel: AddGarageScreenViewModel,
    onNavigateToMain: () -> Unit
) {
    val uiState by addGarageScreenViewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        when(uiState) {
            is AddGarageUiState.NavigateToMain -> {
                onNavigateToMain()
            }
            else -> Unit
        }
    }

    var garageName by rememberSaveable { mutableStateOf("") }
    AppBar(navigationController)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .weight(0.1f),
            text = "Introduce los datos para crear tu garaje.",
            fontSize = 20.sp,
            color = colorResource(R.color.appColor),
            fontWeight = FontWeight.Bold
        )
        TextField(
            value = garageName,
            onValueChange = { garageName = it },
            singleLine = true,
            maxLines = 1,
            modifier = modifier.align(Alignment.CenterHorizontally),
            placeholder = { Text("Nombre del garaje ...") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        Text(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .weight(0.1f),
            text = "¿Qué hace vGarage?",
            fontSize = 20.sp,
            color = colorResource(R.color.appColor),
            fontWeight = FontWeight.Bold
        )
        Carousel(modifier = modifier.weight(0.3f))
        Text(
            modifier = modifier.align(Alignment.CenterHorizontally),
            text = "Ten un listado de los mantenimientos",
            color = colorResource(R.color.appColor),
            fontWeight = FontWeight.Bold
        )
        AddGarageButton(
            garageName,
            modifier = modifier.weight(0.1f),
            onAddGarage = { addGarageScreenViewModel.onGarageCreated(it) })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navigationController: NavHostController) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.appColor),
            titleContentColor = colorResource(R.color.textWhite),
        ), title = {
            Text("Nuevo Garaje")
        }, navigationIcon = {
            IconButton(
                onClick = { navigationController.navigate("landing") }, colors = IconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = colorResource(R.color.textWhite),
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = colorResource(R.color.textWhite)
                )
            ) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "")
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carousel(modifier: Modifier) {
    val carouselState = rememberCarouselState { 3 }

    HorizontalMultiBrowseCarousel(
        state = carouselState, preferredItemWidth = 300.dp, itemSpacing = 10.dp
    ) { item ->
        Box(modifier = Modifier.size(300.dp)) {
            Image(
                painter = painterResource(
                    id = when (item) {
                        0 -> R.drawable.car_repair
                        1 -> R.drawable.lista_guy
                        else -> R.drawable.girl_location
                    }
                ),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun AddGarageButton(garageName: String, modifier: Modifier, onAddGarage: (String) -> Unit) {
    Button(
        onClick = {
            onAddGarage(garageName)
        },
        modifier = Modifier.fillMaxWidth(),
        enabled = !garageName.isEmpty(),
        colors = ButtonColors(
            containerColor = colorResource(R.color.botonInAppColor),
            contentColor = colorResource(R.color.textWhite),
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.White
        )
    ) {
        Text(text = "Abrir garaje")
    }
}