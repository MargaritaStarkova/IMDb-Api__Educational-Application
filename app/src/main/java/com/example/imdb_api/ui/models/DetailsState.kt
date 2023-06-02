package com.example.imdb_api.ui.models

import com.example.imdb_api.domain.models.MovieDetails

sealed interface DetailsState {
    
    data class Content(
        val movie: MovieDetails
    ) : DetailsState
    
    data class Error(
        val message: String
    ) : DetailsState
    
}