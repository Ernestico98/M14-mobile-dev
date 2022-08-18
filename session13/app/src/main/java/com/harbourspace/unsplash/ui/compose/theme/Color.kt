package com.harbourspace.unsplash.ui.compose.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val colorLight = Color.White
val colorLightOn = Color.Black

val colorDark = Color.Black
val colorDarkOn = Color.White

val colorLightPalette = lightColors(
    primary = colorLight,
    primaryVariant = colorLight,
    secondary = colorLight,
    secondaryVariant = colorDark,
    background = colorLight,
    surface = Color.Transparent,
    error  = colorLight,
    onPrimary = colorLightOn,
    onSecondary = colorLightOn,
    onBackground = colorLightOn,
    onSurface = colorLightOn,
    onError = colorLightOn
)

val colorDarkPalette = darkColors(
    primary = colorDark,
    primaryVariant = colorDark,
    secondary = colorDark,
    secondaryVariant = colorLight,
    background = colorDark,
    surface = Color.Transparent,
    error  = colorDark,
    onPrimary = colorDarkOn,
    onSecondary = colorDarkOn,
    onBackground = colorDarkOn,
    onSurface = colorDarkOn,
    onError = colorDarkOn
)