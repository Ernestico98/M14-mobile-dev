package com.ernestico.weather.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ernestico.weather.MainViewModel

@Composable
fun AboutScreen(
    mainViewModel: MainViewModel
) {
    mainViewModel.setTopBarText("About")
}