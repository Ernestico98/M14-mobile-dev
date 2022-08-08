package com.ernestico.unsplash.data

import com.ernestico.unsplash.model.UnsplashItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

private const val AUTHORIZATION_CLIENT_ID = "Client-ID"
private const val ACCESS_KEY = "9SVr2iEgdEwPZuoFosWgtu1CmdQzHKmG9dkWP1_4I7o"

interface UnsplashAPIClient {

    @Headers("Authorization: $AUTHORIZATION_CLIENT_ID $ACCESS_KEY")
    @GET("photos")
    fun fetchPhotos() : Call<List<UnsplashItem>>
}