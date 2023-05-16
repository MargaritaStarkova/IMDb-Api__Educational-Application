package com.example.imdb_api.di

import com.example.imdb_api.presentation.movies.MoviesSearchViewModel
import com.example.imdb_api.presentation.poster.PosterView
import com.example.imdb_api.presentation.poster.PosterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    
    viewModel {
        MoviesSearchViewModel(androidContext() as MoviesApplication, get())
    }
    
    viewModel {(view: PosterView, url: String) ->
        PosterViewModel(view, url)
    }
}