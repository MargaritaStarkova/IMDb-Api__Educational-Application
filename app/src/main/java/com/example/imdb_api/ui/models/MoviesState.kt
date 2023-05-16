package com.example.imdb_api.ui.models

import com.example.imdb_api.domain.models.Movie

sealed interface MoviesState {
    object Loading : MoviesState

    data class Content(
        val listFilms: List<Movie>
    ) : MoviesState

    data class Error(
        val errorMessage: String
    ) : MoviesState
}
