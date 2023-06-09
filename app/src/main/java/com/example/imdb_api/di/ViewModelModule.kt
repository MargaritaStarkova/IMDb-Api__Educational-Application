package com.example.imdb_api.di

import com.example.imdb_api.presentation.cast.CastViewModel
import com.example.imdb_api.presentation.movies.MoviesSearchViewModel
import com.example.imdb_api.presentation.persons.PersonsViewModel
import com.example.imdb_api.presentation.poster.DetailsViewModel
import com.example.imdb_api.presentation.poster.PosterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    
    viewModel {
        MoviesSearchViewModel(androidContext() as MoviesApplication, get())
    }
    
    viewModel {(movieId: String) ->
        DetailsViewModel(movieId, get())
    }
    
    viewModel {(posterUrl: String) ->
        PosterViewModel(posterUrl)
    }
    
    viewModel {(movieId: String) ->
        CastViewModel(movieId, get())
    }
    viewModel {
        PersonsViewModel(androidContext() as MoviesApplication, get())
    }
}
    
