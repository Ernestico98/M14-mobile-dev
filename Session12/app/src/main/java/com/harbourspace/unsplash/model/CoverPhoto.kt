package com.harbourspace.unsplash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoverPhoto(
    val blur_hash: String,
    val color: String,
    val description: String?,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val urls: Urls,
    val user: User,
    val width: Int
): Parcelable