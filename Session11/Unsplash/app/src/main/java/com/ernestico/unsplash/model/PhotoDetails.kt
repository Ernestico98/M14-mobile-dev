package com.ernestico.unsplash.model

data class PhotoDetails(
    val blur_hash: String?,
    val color: String?,
    val created_at: String?,
    val current_user_collections: List<CurrentUserCollectionX>?,
    val description: String?,
    val downloads: Int?,
    val exif: Exif?,
    val height: Int?,
    val id: String?,
    val liked_by_user: Boolean?,
    val likes: Int?,
    val links: LinksXXXX?,
    val location: Location?,
    val public_domain: Boolean?,
    val tags: List<Tag>?,
    val updated_at: String?,
    val urls: UrlsXX?,
    val user: User?,
    val width: Int?,
    val views: Int?
)