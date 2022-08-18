package com.harbourspace.unsplash.utils

import androidx.appcompat.app.AppCompatDelegate

internal const val EXTRA_TAB_SELECTED = "extra.tab.selected"
internal const val EXTRA_UNSPLASH_IMAGE_URL = "extra.unsplash.image.url"

fun isDarkThemeEnabled(): Boolean {
    return AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO
}