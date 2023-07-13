package com.example.imdb_api.ui.models

import com.example.imdb_api.domain.models.Movie

sealed interface HistoryState {
    
    object Loading : HistoryState
    
    data class Content(
        val movies: List<Movie>,
    ) : HistoryState
    
    data class Empty(
        val message: String,
    ) : HistoryState
    
}
