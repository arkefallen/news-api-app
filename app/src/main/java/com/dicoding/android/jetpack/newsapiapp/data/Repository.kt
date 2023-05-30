package com.dicoding.android.jetpack.newsapiapp.data

import com.dicoding.android.jetpack.newsapiapp.BuildConfig
import com.dicoding.android.jetpack.newsapiapp.data.network.APIService
import com.dicoding.android.jetpack.newsapiapp.data.response.ArticlesItem

class Repository private constructor(
    private val apiService: APIService
){

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            apiService: APIService
        ) : Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService)
            }.also { instance = it }
    }

    suspend fun getNews() : List<ArticlesItem> {
        val response = apiService.getNews("us", BuildConfig.API_KEY)
        return response.articles
    }
}