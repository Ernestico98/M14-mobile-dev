package com.ernestico.weather.screens

import android.app.Activity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ernestico.weather.MainViewModel

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
) {
    mainViewModel.setTopBarText("Weather Status")

}