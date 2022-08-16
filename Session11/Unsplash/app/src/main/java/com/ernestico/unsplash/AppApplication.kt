package com.ernestico.unsplash

import android.app.Application
import com.ernestico.unsplash.databaseModel.AppDatabase
import com.ernestico.unsplash.databaseModel.UnsplashRepository

class AppApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }

    val repository by lazy { UnsplashRepository(database.unsplashDao()) }
}