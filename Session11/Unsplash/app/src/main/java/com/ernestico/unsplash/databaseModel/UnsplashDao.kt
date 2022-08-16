package com.ernestico.unsplash.databaseModel

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ernestico.unsplash.model.UnsplashItem

@Dao
interface UnsplashDAO {
    @Query("SELECT * FROM unsplash")
    fun getUnsplashItems(): LiveData<List<UnsplashModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(Unsplash: UnsplashModel)

    @Query("DELETE FROM Unsplash")
    fun deleteAll()
}