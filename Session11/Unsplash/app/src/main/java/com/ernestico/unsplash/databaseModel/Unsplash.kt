package com.ernestico.unsplash.databaseModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Unsplash")
class UnsplashModel (
    @PrimaryKey(autoGenerate = false)
    var id: String,
    @ColumnInfo(name = "userName")
    val userName: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String
)