package com.ernestico.unsplash.databaseModel

import androidx.lifecycle.LiveData
import com.ernestico.unsplash.model.UnsplashItem

class UnsplashRepository(private val unsplashDao: UnsplashDAO) {

    val allUnsplash: LiveData<List<UnsplashModel>> = unsplashDao.getUnsplashItems()
//    val allUnsplash: LiveData<List<UnsplashModel>> = emptyList<UnsplashModel>() as LiveData<List<UnsplashModel>>

    fun insert(unsplashItem: UnsplashModel) {
        AppDatabase.databaseWriteExecutor.execute {
            unsplashDao.insert(unsplashItem)
        }
    }
}