package com.example.imdb_api.domain.impl

import com.example.imdb_api.domain.api.MoviesInteractor
import com.example.imdb_api.domain.api.MoviesRepository
import com.example.imdb_api.domain.models.Movie
import com.example.imdb_api.util.Resource
import java.util.concurrent.Executors

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun getMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute {
            when(val resource = repository.searchMovies(expression)) {
                is Resource.Success -> { consumer.consume(resource.data, null) }
                is Resource.Error -> { consumer.consume(null, resource.message) }
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