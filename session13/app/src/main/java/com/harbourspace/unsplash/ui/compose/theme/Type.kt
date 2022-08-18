package com.harbourspace.unsplash.ui.compose.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.harbourspace.unsplash.R

val fontSizeLarge = 17.sp
val fontSizeMedium = 15.sp
val fontSizeSmall = 13.sp

private val ApercuFontFamily = FontFamily(
    Font(R.font.apercu_bold_pro, FontWeight.Bold),
    Font(R.font.apercu_medium_pro, FontWeight.Medium),
    Font(R.font.apercu_regular_pro, FontWeight.Normal)
)

val typography = Typography(
    h1 = TextStyle(
        fontFamily = ApercuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = fontSizeLarge
    ),

    h2 = TextStyle(
        fontFamily = ApercuFontFamily,
        fontSize = fontSizeMedium
    ),

    h3 = TextStyle(
        fontFamily = ApercuFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = fontSizeMedium
    ),

    subtitle1 = TextStyle(
        fontFamily = ApercuFontFamily,
        fontSize = fontSizeSmall
    )
)