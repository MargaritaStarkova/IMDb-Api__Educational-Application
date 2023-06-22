package com.example.imdb_api.domain.api

import com.example.imdb_api.domain.models.Movie
import com.example.imdb_api.domain.models.SearchRequest
import kotlinx.coroutines.flow.Flow

interface MoviesInteractor {
    fun <R> getDataFromApi(request: SearchRequest): Flow<Pair<R?, String?>>
    
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
    
}