package com.dicoding.android.jetpack.newsapiapp.data.network

import com.dicoding.android.jetpack.newsapiapp.data.response.IndonesiaLiveNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country : String,
        @Query("apiKey") apiKey : String
    ) : IndonesiaLiveNewsResponse
}