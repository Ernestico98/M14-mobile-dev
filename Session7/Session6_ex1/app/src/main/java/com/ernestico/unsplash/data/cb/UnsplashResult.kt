package com.ernestico.unsplash.data.cb

import com.ernestico.unsplash.model.Result
import com.ernestico.unsplash.model.UnsplashItem

interface UnsplashResult {

    fun onDataFetchedSuccess(images: List<UnsplashItem>)

//    fun onDataSearchSuccess(images: List<Result>)

    fun onDataFetchedFailed()
}