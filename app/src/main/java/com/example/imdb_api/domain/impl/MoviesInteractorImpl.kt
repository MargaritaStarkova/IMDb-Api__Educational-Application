package com.example.imdb_api.domain.impl

import com.example.imdb_api.domain.api.MoviesInteractor
import com.example.imdb_api.domain.api.MoviesRepository
import com.example.imdb_api.domain.models.Movie
import com.example.imdb_api.domain.models.SearchType
import com.example.imdb_api.domain.models.SearchType.CAST
import com.example.imdb_api.domain.models.SearchType.DETAILS
import com.example.imdb_api.domain.models.SearchType.MOVIES
import com.example.imdb_api.core.util.Resource
import java.util.concurrent.Executors

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    private val executor = Executors.newCachedThreadPool()
    
    override fun getDataFromApi(
        expression: String,
        type: SearchType,
        consumer: MoviesInteractor.Consumer,
    ) {
        executor.execute {
            
            when (type) {
                MOVIES -> {
                    when (val resource = repository.searchMovies(expression)) {
                        is Resource.Success -> {
                            consumer.consume(resource.data, null)
                        }
        
                        is Resource.Error -> {
                            consumer.consume(null, resource.message)
                        }
                    }
                }
                    DETAILS -> {
                        when (val resource = repository.searchMovieDetails(expression)) {
                            is Resource.Success -> {
                                consumer.consume(resource.data, null)
                            }
        
                            is Resource.Error -> {
                                consumer.consume(null, resource.message)
                            }
                        }
                    }
    
                CAST -> {
                    when (val resource = repository.searchMovieCast(expression)) {
                        is Resource.Success -> {
                            consumer.consume(resource.data, null)
                        }
            
                        is Resource.Error -> {
                            consumer.consume(null, resource.message)
                        }
                    }
                }
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