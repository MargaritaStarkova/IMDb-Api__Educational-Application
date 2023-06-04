package com.example.imdb_api.ui.models

import com.example.imdb_api.data.dto.cast.MoviesFullCastResponse
import com.example.imdb_api.domain.models.MovieCast
import com.example.imdb_api.ui.RVItem
import com.example.imdb_api.ui.movie_cast.MoviesCastRVItem

sealed interface CastState {
    
    object Loading: CastState
    
    data class Content(
        val fullTitle: String,
        val items: List<RVItem>,
    ) : CastState
    
    data class Error(
        val message: String
    ) : CastState
    
}