package com.ernestico.unsplash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserX(
    val first_name: String,
    val id: String,
    val instagram_username: String,
    val last_name: String,
    val links: LinksXXX,
    val name: String,
    val portfolio_url: String,
    val profile_image: ProfileImageX,
    val twitter_username: String,
    val username: String
): Parcelable