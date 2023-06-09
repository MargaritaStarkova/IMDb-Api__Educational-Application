package com.example.imdb_api.domain.api

import com.example.imdb_api.domain.models.Movie
import com.example.imdb_api.domain.models.MovieCast
import com.example.imdb_api.domain.models.MovieDetails
import com.example.imdb_api.core.util.Resource
import com.example.imdb_api.domain.models.Person

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
    fun searchPersons(expression: String): Resource<List<Person>>
    fun addMoviesToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
    fun searchMovieDetails(movieId: String): Resource<MovieDetails>
    fun searchMovieCast(movieId: String): Resource<MovieCast>
}