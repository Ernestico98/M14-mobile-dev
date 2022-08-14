package com.ernestico.weather.screens

import android.graphics.Paint
import android.hardware.lights.Light
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ernestico.weather.MainViewModel
import com.ernestico.weather.aimations.LoadingAnimation
import com.ernestico.weather.ui.theme.DarkColors
import com.ernestico.weather.ui.theme.LightColors

@Composable
fun SearchScreen(
    mainViewModel: MainViewModel
) {
    mainViewModel.setTopBarText("Search Location")

    val visible = remember { mutableStateOf(false) }
    val searchEnabled = remember { mutableStateOf(true) }
    val textValue = remember { mutableStateOf("") }
    val showList = remember { mutableStateOf(false) }
    val colorTheme = if(isSystemInDarkTheme()) DarkColors else LightColors

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .padding(top = 40.dp)
                .height(60.dp),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "Use Current Location",
                fontSize = 20.sp
            )
        }

        Button(
            modifier = Modifier
                .padding(top = 40.dp)
                .height(60.dp),
            onClick = {
                visible.value = true
                searchEnabled.value = false
            },
            enabled = searchEnabled.value
        ) {
            Text(
                text = "Search for location",
                fontSize = 20.sp
            )
        }

        AnimatedVisibility(visible = visible.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
            ) {
                Spacer(modifier = Modifier.weight(0.15f))

                TextField(
                    value = textValue.value,
                    onValueChange = { value ->
                        textValue.value = value
                    },
                    singleLine = true,
                    modifier = Modifier.weight(0.7f),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                mainViewModel.setGeoData(null)
                                mainViewModel.fetchGeo(place = textValue.value)
                                showList.value = true
                            }
                        ) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Search Button"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.weight(0.15f))
            }
        }

        if (showList.value)
            ShowPlacesList(mainViewModel = mainViewModel)
    }
}

@Composable
fun ShowPlacesList(
    mainViewModel: MainViewModel,
) {
    val geoData = mainViewModel.geoResponse.observeAsState()

    if (geoData.value == null) {
        LoadingAnimation()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().
                    padding(vertical = 10.dp)
        ) {
            if (geoData.value != null) {
                items(geoData.value!!) { data ->
                    Button(
                        modifier = Modifier
//                            .height(100.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        onClick = {
                            mainViewModel.setUseLocation(false)
                            mainViewModel.setLocation(lon = data.lon!!, lat = data.lat!!)
                        }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "City name: ${data.name}")
                            Text(text = "Country code: ${data.country}")
                            Text(text = "Latitude: ${data.lat}")
                            Text(text = "Longitude: ${data.lon}")
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

