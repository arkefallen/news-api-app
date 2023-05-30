package com.dicoding.android.jetpack.newsapiapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.android.jetpack.newsapiapp.data.Injection
import com.dicoding.android.jetpack.newsapiapp.data.Repository

class NewsViewModelFactory private constructor(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: NewsViewModelFactory? = null

        fun getInstance() : NewsViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: NewsViewModelFactory(Injection.provideRepository())
            }.also { instance = it }
    }
}