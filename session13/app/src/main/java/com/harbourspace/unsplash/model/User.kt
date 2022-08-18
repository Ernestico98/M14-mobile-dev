package com.harbourspace.unsplash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val bio: String?,
    val id: String,
    val instagram_username: String?,
    val links: Links,
    val location: String?,
    val name: String,
    val portfolio_url: String?,
    val profile_image: ProfileImage,
    val total_collections: Int,
    val total_likes: Int,
    val total_photos: Int,
    val twitter_username: String?,
    val username: String
) : Parcelable