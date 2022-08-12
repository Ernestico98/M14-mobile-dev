package com.ernestico.weather.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ernestico.weather.R

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val stringResId: Int,
    @DrawableRes val drawRess: Int
) {

    object Home: BottomNavigationScreens(
        route = "Home",
        R.string.main_navigation_home,
        R.drawable.ic_home
    )

    object Search: BottomNavigationScreens(
        route = "Search",
        R.string.main_navigation_search,
        R.drawable.ic_search
    )

    object About: BottomNavigationScreens(
        route = "About",
        R.string.main_navigation_about,
        R.drawable.ic_info
    )
}