package com.example.imdb_api.ui.models

import com.example.imdb_api.core.root.RVItem

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