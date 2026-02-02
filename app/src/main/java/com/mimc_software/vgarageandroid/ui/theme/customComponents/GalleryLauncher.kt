package com.mimc_software.vgarageandroid.ui.theme.customComponents

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun galleryLauncher(onImageSelected: (Uri?) -> Unit): ManagedActivityResultLauncher<String, Uri?> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = onImageSelected
    )
}