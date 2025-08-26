package com.mimc_software.vgarageandroid.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
    AppBar(activeGarage)
    Box(modifier = modifier.fillMaxSize()) {
        FabAdd(Modifier.align(Alignment.BottomEnd), navigationController)
    }
}

@Composable
fun FabAdd(modifier: Modifier, navigationController: NavHostController) {
    FloatingActionButton(
        onClick = {
            navigationController.navigate("addVehicle")
        },
        modifier = modifier.padding(25.dp).testTag("btn_fab"),
        containerColor = colorResource(R.color.appColor),
        contentColor = colorResource(R.color.textWhite)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "")
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