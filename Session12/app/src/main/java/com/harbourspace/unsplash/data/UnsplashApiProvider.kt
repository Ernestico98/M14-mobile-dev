package com.harbourspace.unsplash.data

import android.util.Log
import com.harbourspace.unsplash.data.cb.UnsplashResult
import com.harbourspace.unsplash.model.UnsplashCollection
import com.harbourspace.unsplash.model.UnsplashItem
import com.harbourspace.unsplash.model.UnsplashSearch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

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
            override fun onResponse(call: Call<List<UnsplashItem>>, response: Response<List<UnsplashItem>>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d(TAG, "fetchImages | response: ${response.body()}")
                    cb.onDataFetchedSuccess(response.body()!!)
                } else {
                    cb.onDataFetchedFailed()
                }
            }

            override fun onFailure(call: Call<List<UnsplashItem>>, t: Throwable) {
                Log.e(TAG, "fetchImages | error loading images", t)
                cb.onDataFetchedFailed()
            }
        })
    }

    fun searchImages(keyword: String, cb: UnsplashResult) {
        retrofit.searchPhotos(keyword).enqueue(object : Callback<UnsplashSearch> {
            override fun onResponse(call: Call<UnsplashSearch>, response: Response<UnsplashSearch>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d(TAG, "searchImages | response: ${response.body()}")
                    cb.onDataFetchedSuccess(response.body()!!.results)
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

    fun fetchCollections(cb: UnsplashResult) {
        retrofit.fetchCollections().enqueue(object : Callback<List<UnsplashCollection>> {
            override fun onResponse(call: Call<List<UnsplashCollection>>, response: Response<List<UnsplashCollection>>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d(TAG, "fetchCollections | response: ${response.body()}")
                    cb.onCollectionsFetchedSuccess(response.body()!!)
                } else {
                    cb.onDataFetchedFailed()
                }
            }

            override fun onFailure(call: Call<List<UnsplashCollection>>, t: Throwable) {
                Log.e(TAG, "fetchCollections | error loading images", t)
                cb.onDataFetchedFailed()
            }
        })
    }
}