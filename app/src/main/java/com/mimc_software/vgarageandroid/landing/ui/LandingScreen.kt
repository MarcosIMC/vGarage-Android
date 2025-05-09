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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mimc_software.vgarageandroid.R

@Composable
fun LandingScreen(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Image(painter = painterResource(R.drawable.ic_android_black_24dp), contentDescription = "", modifier = modifier.align(Alignment.CenterHorizontally).size(80.dp))
            Text(text = "Texto prueba 1", modifier = modifier.align(Alignment.CenterHorizontally), color = colorResource(R.color.textWhite))
            Text(text = "Texto prueba 2", modifier = modifier.align(Alignment.CenterHorizontally), color = colorResource(R.color.textWhite))
            Text(text = "Texto prueba 3", modifier = modifier.align(Alignment.CenterHorizontally), color = colorResource(R.color.textWhite))
            Spacer(modifier = modifier.height(30.dp).weight(1f))
            Button(onClick = {}, modifier.align(Alignment.CenterHorizontally), colors = ButtonColors(
                containerColor = colorResource(R.color.botonInAppColor),
                contentColor = Color.White,
                disabledContainerColor = Color.Blue,
                disabledContentColor = Color.Blue
            )) {
                Text("Añadir garaje")
            }
        }
    }
}