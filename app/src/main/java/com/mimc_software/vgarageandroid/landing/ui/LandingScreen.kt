package com.mimc_software.vgarageandroid.landing.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mimc_software.vgarageandroid.R

@Composable
fun LandingScreen(modifier: Modifier, navigationController: NavHostController) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Image(
                painter = painterResource(R.drawable.logoapp),
                contentDescription = "app logo",
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .size(150.dp)
            )
            Text(
                text = stringResource(R.string.problem_statement_forgot_maintenance),
                modifier = modifier.align(Alignment.CenterHorizontally),
                color = colorResource(R.color.textWhite)
            )
            Text(
                text = stringResource(R.string.problem_statement_forgot_itv),
                modifier = modifier.align(Alignment.CenterHorizontally),
                color = colorResource(R.color.textWhite)
            )
            Text(
                text = stringResource(R.string.problem_statement_forgot_parking),
                modifier = modifier.align(Alignment.CenterHorizontally),
                color = colorResource(R.color.textWhite)
            )
            Text(
                text = stringResource(R.string.slogan_app),
                modifier = modifier.padding(horizontal = 20.dp),
                color = colorResource(R.color.textWhite)
            )
            Spacer(modifier = modifier
                .height(30.dp)
                .weight(1f))
            Button(
                onClick = { navigationController.navigate("addGarage") },
                modifier
                    .align(Alignment.CenterHorizontally)
                    .testTag("add_garage_btn"),
                colors = ButtonColors(
                    containerColor = colorResource(R.color.botonInAppColor),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Blue,
                    disabledContentColor = Color.Blue
                )
            ) {
                Text(stringResource(R.string.button_add_garage))
            }
        }
    }
}