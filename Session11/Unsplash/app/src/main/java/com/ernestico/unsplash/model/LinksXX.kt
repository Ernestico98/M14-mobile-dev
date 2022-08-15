package com.ernestico.unsplash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LinksXX(
    val download: String?,
    val html: String?,
    val self: String?
): Parcelable