package com.harbourspace.unsplash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
) : Parcelable