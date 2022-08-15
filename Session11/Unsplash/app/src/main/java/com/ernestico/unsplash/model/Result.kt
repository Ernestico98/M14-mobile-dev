package com.ernestico.unsplash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Result(
    val blur_hash: String?,
    val color: String?,
    val created_at: String?,
    val current_user_collections: @RawValue List<Any>?,
    val description: String?,
    val height: Int?,
    val id: String?,
    val liked_by_user: Boolean?,
    val likes: Int?,
    val links: LinksXX?,
    val urls: UrlsX?,
    val user: UserX?,
    val width: Int?
): Parcelable