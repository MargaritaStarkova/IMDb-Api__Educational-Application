package com.example.imdb_api.domain.api

import com.example.imdb_api.domain.models.Movie
import com.example.imdb_api.domain.models.SearchType

interface MoviesInteractor {
    fun getDataFromApi(expression: String, type: SearchType, consumer: Consumer)
    
    fun addMovieToFavorites(movie: Movie)
    
    fun removeMovieFromFavorites(movie: Movie)
    
    interface Consumer {
        fun <T> consume(data: T?, errorMessage: String?)
    }
}