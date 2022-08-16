package com.ernestico.unsplash.databaseModel

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Database(entities = [UnsplashModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun unsplashDao(): UnsplashDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(context, AppDatabase::class.java, "db").build()
                INSTANCE = db
                db
            }
        }

        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(2)
    }


}