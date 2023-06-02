package com.example.imdb_api.domain.api

import com.example.imdb_api.domain.models.Movie

interface MoviesInteractor {
    fun getDataFromApi(expression: String, consumer: Consumer)
    
    fun addMovieToFavorites(movie: Movie)
    
    fun removeMovieFromFavorites(movie: Movie)
    
    interface Consumer {
        fun <T> consume(data: T?, errorMessage: String?)
    }
}