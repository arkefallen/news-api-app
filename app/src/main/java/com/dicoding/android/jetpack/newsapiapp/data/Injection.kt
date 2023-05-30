package com.dicoding.android.jetpack.newsapiapp.data

import com.dicoding.android.jetpack.newsapiapp.data.network.APIConfig

object Injection {
    fun provideRepository() : Repository {
        val apiService = APIConfig.getAPIService()
        return Repository.getInstance(apiService)
    }
}