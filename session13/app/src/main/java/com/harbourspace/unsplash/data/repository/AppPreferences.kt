package com.harbourspace.unsplash.data.repository

import android.app.Activity
import android.content.Context
import com.harbourspace.unsplash.utils.isDarkThemeEnabled

private const val KEY_DARK_THEME = "key_dark_theme"

class AppPreferences(context: Activity) {

    private val preferences = context.getPreferences(Context.MODE_PRIVATE)

    fun setDarkTheme(value: Boolean) {
        with (preferences.edit()) {
            putBoolean(KEY_DARK_THEME, value)
            apply()
        }
    }

    fun getDarkTheme(): Boolean {
        return preferences.getBoolean(KEY_DARK_THEME, isDarkThemeEnabled())
    }
}