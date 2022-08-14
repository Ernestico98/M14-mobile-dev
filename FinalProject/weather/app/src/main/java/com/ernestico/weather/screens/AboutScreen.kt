package com.ernestico.weather.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ernestico.weather.MainViewModel

@Composable
fun AboutScreen(
    mainViewModel: MainViewModel,
    navController: NavController
) {
    mainViewModel.setTopBarText("About")
}