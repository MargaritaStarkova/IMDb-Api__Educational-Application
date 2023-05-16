package com.example.imdb_api.domain.api

import com.example.imdb_api.domain.models.Movie

interface MoviesInteractor {
    fun getMovies(expression: String, consumer: MoviesConsumer)

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)

    fun interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }
}