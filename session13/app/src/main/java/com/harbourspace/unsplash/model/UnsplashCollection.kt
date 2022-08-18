package com.harbourspace.unsplash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashCollection(
    val cover_photo: CoverPhoto,
    val description: String?,
    val id: String,
    val last_collected_at: String,
    val links: Links,
    val `private`: Boolean,
    val published_at: String,
    val share_key: String,
    val title: String,
    val total_photos: Int,
    val updated_at: String,
    val user: User
): Parcelable