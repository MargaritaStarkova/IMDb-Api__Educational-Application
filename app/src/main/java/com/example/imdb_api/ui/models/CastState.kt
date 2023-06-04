package com.example.imdb_api.ui.models

import com.example.imdb_api.data.dto.cast.MoviesFullCastResponse
import com.example.imdb_api.domain.models.MovieCast

sealed interface CastState {
    
    object Loading: CastState
    
    data class Content(
        val movie: MovieCast
    ) : CastState
    
    data class Error(
        val message: String
    ) : CastState
    
}