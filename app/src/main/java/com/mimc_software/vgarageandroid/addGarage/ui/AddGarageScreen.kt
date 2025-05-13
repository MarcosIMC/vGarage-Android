package com.mimc_software.vgarageandroid.addGarage.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mimc_software.vgarageandroid.R

@Composable
fun AddGarageScreen(modifier: Modifier) {
    var garageName by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = garageName,
            onValueChange = { garageName = it},
            singleLine = true,
            maxLines = 1,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        Carousel()
        Spacer(modifier = modifier.weight(1f))
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Abrir garaje")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carousel() {
    val carouselState = rememberCarouselState { 3 }

    HorizontalMultiBrowseCarousel(
        state = carouselState,
        preferredItemWidth = 300.dp,
        itemSpacing = 10.dp
    ) { item ->
        Box(modifier = Modifier.size(300.dp)) {
            Image(
                painter = painterResource(id = when(item) {
                    0 -> R.drawable.ic_android_black_24dp
                    1 -> R.drawable.ic_launcher_background
                    else -> R.drawable.ic_launcher_foreground
                }),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}