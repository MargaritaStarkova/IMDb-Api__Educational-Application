package com.example.imdb_api.data

import com.example.imdb_api.data.converts.MovieCastConverter
import com.example.imdb_api.data.dto.cast.MovieCastRequest
import com.example.imdb_api.data.dto.cast.MoviesFullCastResponse
import com.example.imdb_api.data.dto.details.MovieDetailsRequest
import com.example.imdb_api.data.dto.details.MovieDetailsResponse
import com.example.imdb_api.data.dto.search.MoviesSearchRequest
import com.example.imdb_api.data.dto.search.MoviesSearchResponse
import com.example.imdb_api.data.storage.LocalStorage
import com.example.imdb_api.domain.api.MoviesRepository
import com.example.imdb_api.domain.models.Movie
import com.example.imdb_api.domain.models.MovieCast
import com.example.imdb_api.domain.models.MovieDetails
import com.example.imdb_api.core.util.Resource

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
    private val movieCastConverter: MovieCastConverter,
) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {

        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> Resource.Error("Проверьте подключение к интернету")
            200 -> {
                val stored = localStorage.getSavedFavorites()
                val resultList = (response as MoviesSearchResponse).results

                if (resultList.isNullOrEmpty()) {
                    Resource.Error("Ничего не нашлось", null)

                } else {
                    Resource.Success(resultList.map {
                        Movie(
                            id = it.id,
                            resultType = it.resultType,
                            image = it.image,
                            title = it.title,
                            description = it.description,
                            inFavorite = stored.contains(it.id)
                        )

                    })
                }
            }

            else -> Resource.Error("Ошибка сервера", null)
        }
    }

    override fun addMoviesToFavorites(movie: Movie) {
        localStorage.addToFavorites(movie.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }
    
    override fun searchMovieDetails(movieId: String): Resource<MovieDetails> {
        val response = networkClient.doRequest(MovieDetailsRequest(movieId))
        return when (response.resultCode) {
            -1 -> Resource.Error("Проверьте подключение к интернету")
            200 -> {
                val result = (response as MovieDetailsResponse)
            
                if (result == null) {
                    Resource.Error("Ничего не нашлось", null)
                
                } else {
                    with(response) {
                        Resource.Success(MovieDetails(id, title, imDbRating, year,
                            countries, genres, directors, writers, stars, plot)) }
                }
            }
            else -> Resource.Error("Ошибка сервера", null)
        }
    }
    
    override fun searchMovieCast(movieId: String): Resource<MovieCast> {
        val response = networkClient.doRequest(MovieCastRequest(movieId))
        return when (response.resultCode) {
            -1 -> Resource.Error("Проверьте подключение к интернету")
            200 -> {
                val result = (response as MoviesFullCastResponse)
                
                if (result == null) {
                    Resource.Error("Ничего не нашлось", null)
                    
                } else {
                    Resource.Success(
                        data = movieCastConverter.convert(response)
                    )

                }
            }
            else -> Resource.Error("Ошибка сервера", null)
        }
    }
}

