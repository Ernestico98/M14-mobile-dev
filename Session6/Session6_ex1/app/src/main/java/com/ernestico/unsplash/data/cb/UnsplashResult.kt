package com.ernestico.unsplash.data.cb

import com.ernestico.unsplash.model.UnsplashItem

interface UnsplashResult {

    fun onDataFetchedSuccess(images: List<UnsplashItem>)

    fun onDataFetchedFailed()
}