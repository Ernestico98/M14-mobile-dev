package com.harbourspace.unsplash.data.cb

import com.harbourspace.unsplash.model.UnsplashCollection
import com.harbourspace.unsplash.model.UnsplashItem

interface UnsplashResult {

    fun onDataFetchedSuccess(images: List<UnsplashItem>)

    fun onCollectionsFetchedSuccess(collections: List<UnsplashCollection>)

    fun onDataFetchedFailed()
}