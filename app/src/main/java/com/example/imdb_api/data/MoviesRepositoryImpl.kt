package com.example.imdb_api.data

import com.example.imdb_api.data.dto.MoviesSearchRequest
import com.example.imdb_api.data.dto.MoviesSearchResponse
import com.example.imdb_api.data.storage.LocalStorage
import com.example.imdb_api.domain.api.MoviesRepository
import com.example.imdb_api.domain.models.Movie
import com.example.imdb_api.util.Resource

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
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
}

