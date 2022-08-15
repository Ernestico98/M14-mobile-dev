package com.ernestico.unsplash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashSearch(
    val results: List<UnsplashItem>?,
    val total: Int?,
    val total_pages: Int?
): Parcelable