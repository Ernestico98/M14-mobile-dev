package com.harbourspace.unsplash.data

import com.harbourspace.unsplash.model.UnsplashCollection
import com.harbourspace.unsplash.model.UnsplashItem
import com.harbourspace.unsplash.model.UnsplashSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val AUTHORIZATION_CLIENT_ID = "Client-ID"
private const val ACCESS_KEY = "ngak5Lv2ZsDvYfnAJjyMP0mnV23pWs5hcvOBXceV3Wc"

interface UnsplashAPIClient {

    @Headers("Authorization: $AUTHORIZATION_CLIENT_ID $ACCESS_KEY")
    @GET("photos")
    fun fetchPhotos() : Call<List<UnsplashItem>>

    @Headers("Authorization: $AUTHORIZATION_CLIENT_ID $ACCESS_KEY")
    @GET("search/photos")
    fun searchPhotos(@Query(value = "query") keyword : String): Call<UnsplashSearch>

    @Headers("Authorization: $AUTHORIZATION_CLIENT_ID $ACCESS_KEY")
    @GET("collections")
    fun fetchCollections() : Call<List<UnsplashCollection>>
}