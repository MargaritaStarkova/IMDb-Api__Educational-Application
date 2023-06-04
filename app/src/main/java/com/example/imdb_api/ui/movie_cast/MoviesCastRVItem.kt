package com.example.imdb_api.ui.movie_cast

import com.example.imdb_api.domain.models.MovieCastPerson

sealed interface MoviesCastRVItem {
    
    data class HeaderItem(
        val headerText: String,
    ) : MoviesCastRVItem
    
    data class PersonItem(
        val data: MovieCastPerson,
    ) : MoviesCastRVItem
}