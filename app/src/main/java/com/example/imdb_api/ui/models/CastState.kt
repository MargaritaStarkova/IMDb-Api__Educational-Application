package com.example.imdb_api.ui.models

import com.example.imdb_api.data.dto.cast.MovieCastResponse

sealed interface CastState {
    
    data class Content(
        val movie: MovieCastResponse
    ) : CastState
    
    data class Error(
        val message: String
    ) : CastState
    
}