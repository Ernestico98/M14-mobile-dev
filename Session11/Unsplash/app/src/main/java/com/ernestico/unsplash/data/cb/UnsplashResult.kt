package com.ernestico.unsplash.data.cb

import com.ernestico.unsplash.model.PhotoDetails
import com.ernestico.unsplash.model.Result
import com.ernestico.unsplash.model.UnsplashItem

interface UnsplashResult {

    fun onDataFetchedSuccess(images: List<UnsplashItem>)

    fun onDataFetchedFailed()

    fun onDetailsFetchedSuccess(image : PhotoDetails)
}