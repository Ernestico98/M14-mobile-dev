package com.ernestico.unsplash.data

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import com.ernestico.unsplash.data.cb.UnsplashResult
import com.ernestico.unsplash.model.UnsplashItem
import com.ernestico.unsplash.model.UnsplashSearch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

private const val TAG = "UnsplashApiProvider"
private const val BASE_URL = "https://api.unsplash.com/"

class UnsplashApiProvider {

    private val retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create<UnsplashAPIClient>()
    }

    fun fetchImages(cb: UnsplashResult) {
        retrofit.fetchPhotos().enqueue(object : Callback<List<UnsplashItem>> {
            override fun onResponse(
                call: Call<List<UnsplashItem>>, response: Response<List<UnsplashItem>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d(TAG, "Response : ${response.body()}")
                    cb.onDataFetchedSuccess(response.body()!!)
                } else {
                    cb.onDataFetchedFailed()
                }
            }

            override fun onFailure(call: Call<List<UnsplashItem>>, t: Throwable) {
                Log.d(TAG, "Error loading images", t)
                cb.onDataFetchedFailed()
            }
        })
    }

    fun searchImages(keyword: String, cb: UnsplashResult) {
        retrofit.searchPhotos(keyword).enqueue(object : Callback<UnsplashSearch> {
            override fun onResponse(call: Call<UnsplashSearch>, response: Response<UnsplashSearch>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d(TAG, "searchImages | response: ${response.body()}")
                    response.body()!!.results?.let { cb.onDataFetchedSuccess(it) }
                } else {
                    cb.onDataFetchedFailed()
                }
            }

            override fun onFailure(call: Call<UnsplashSearch>, t: Throwable) {
                Log.e(TAG, "searchImages | error loading images", t)
                cb.onDataFetchedFailed()
            }
        })
    }



}