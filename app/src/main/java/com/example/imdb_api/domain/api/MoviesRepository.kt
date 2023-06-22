package com.example.imdb_api.domain.api

import com.example.imdb_api.core.util.Resource
import com.example.imdb_api.domain.models.Movie
import com.example.imdb_api.domain.models.SearchRequest
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    
    fun <T> search(request: SearchRequest): Flow<Resource<T>>
    fun addMoviesToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
    
}