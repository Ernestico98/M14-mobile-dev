package com.ernestico.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ernestico.weather.navigation.BottomNavigationScreens
import com.ernestico.weather.screens.AboutScreen
import com.ernestico.weather.screens.HomeScreen
import com.ernestico.weather.screens.SearchScreen
import com.ernestico.weather.ui.theme.DarkColors
import com.ernestico.weather.ui.theme.LightColors
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

private val TAG = "MAIN_ACTIVITY"

class MainActivity : ComponentActivity() {

    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation(fusedLocationProviderClient, mainViewModel)

        setContent {

            val topBarText = mainViewModel.topBarText.observeAsState()

            MaterialTheme(
                colors = if(isSystemInDarkTheme()) DarkColors else LightColors
            ) {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TopAppBar(
                        ) {
                            Text(
                                text = "${topBarText.value}",
                                fontSize = 22.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                        Scaffold(
                            bottomBar = {
                                AddBottomBarNavigation(navController)
                            },
                            modifier = Modifier.padding(start=10.dp, end=10.dp, bottom=10.dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = it.calculateBottomPadding())
                            ) {
                                NavHost(
                                    navController = navController,
                                    startDestination = BottomNavigationScreens.Search.route
                                ) {
                                    composable(BottomNavigationScreens.Home.route) {
                                        HomeScreen(
                                            mainViewModel = mainViewModel,
                                        )
                                    }
                                    composable(BottomNavigationScreens.Search.route) {
                                        SearchScreen(
                                            mainViewModel = mainViewModel
                                        )
                                    }
                                    composable(BottomNavigationScreens.About.route) {
                                        AboutScreen(
                                            mainViewModel = mainViewModel
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
        }
    }



    @Composable
    fun AddBottomBarNavigation(
        navController: NavHostController
    ) {
        val items = listOf(
            BottomNavigationScreens.Home,
            BottomNavigationScreens.Search,
            BottomNavigationScreens.About
        )

        val selectedIndex = remember { mutableStateOf(1) }

        BottomNavigation(
            modifier = Modifier.clip(RoundedCornerShape(20.dp))
        ) {
            items.forEachIndexed { index, bottomNavigationScreens ->

                val isSelected = (index == selectedIndex.value)
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = bottomNavigationScreens.drawRess),
                            contentDescription = stringResource(id = bottomNavigationScreens.stringResId),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {   
                        Text(text = stringResource(id = bottomNavigationScreens.stringResId))
                    },
                    selected = isSelected,
                    alwaysShowLabel = true,
                    onClick = {
                        if (!isSelected) {
                            selectedIndex.value = index
                            navController.navigate(bottomNavigationScreens.route)
                        }
                    }
                )
            }
        }
    }
}
