package com.mimc_software.vgarageandroid.ui.theme.customComponents

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.mimc_software.vgarageandroid.R

object CustomOutlinedTextField {
    @Composable
    fun outlinedTextFieldColorsForm(): TextFieldColors {
        return OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(R.color.appColor),
            unfocusedBorderColor = colorResource(R.color.appColor),
            focusedLabelColor = colorResource(R.color.appColor),
            cursorColor = colorResource(R.color.appColor)
        )
    }
}