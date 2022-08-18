package com.harbourspace.unsplash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class CurrentUserCollection(
    val cover_photo: @RawValue Any,
    val id: Int,
    val last_collected_at: String,
    val published_at: String,
    val title: String,
    val updated_at: String,
    val user: @RawValue Any
) : Parcelable