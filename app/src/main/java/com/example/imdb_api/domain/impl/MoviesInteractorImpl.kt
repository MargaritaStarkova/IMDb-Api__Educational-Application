package com.example.imdb_api.domain.impl

import com.example.imdb_api.core.util.Resource
import com.example.imdb_api.domain.api.MoviesInteractor
import com.example.imdb_api.domain.api.MoviesRepository
import com.example.imdb_api.domain.models.Movie
import com.example.imdb_api.domain.models.MovieCast
import com.example.imdb_api.domain.models.MovieDetails
import com.example.imdb_api.domain.models.Person
import com.example.imdb_api.domain.models.SearchRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {
    
    override fun <T> getDataFromApi(request: SearchRequest): Flow<Pair<T?, String?>> {
        
        val result = when (request) {
            is SearchRequest.MovieCastRequest -> repository.search<MovieCast>(request)
            is SearchRequest.MovieDetailsRequest -> repository.search<MovieDetails>(request)
            is SearchRequest.MoviesSearchRequest -> repository.search<List<Movie>>(request)
            is SearchRequest.PersonsSearchRequest -> repository.search<List<Person>>(request)
        }
        
        return result.map { result ->
            @Suppress("UNCHECKED_CAST") when (result) {
                is Resource.Error -> Pair(null, result.message)
                is Resource.Success -> Pair(result.data as T, null)
            }
        }
    }
    
    override fun addMovieToFavorites(movie: Movie) {
        repository.addMoviesToFavorites(movie)
    }
    
    override fun removeMovieFromFavorites(movie: Movie) {
        repository.removeMovieFromFavorites(movie)
    }
}