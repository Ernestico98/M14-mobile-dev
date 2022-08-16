package com.harbourspace.unsplash.model

data class UnsplashSearch(
    val results: List<UnsplashItem>,
    val total: Int,
    val total_pages: Int
)