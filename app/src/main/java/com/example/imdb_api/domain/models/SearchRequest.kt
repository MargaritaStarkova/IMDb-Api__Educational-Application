package com.example.imdb_api.domain.models

sealed interface SearchRequest {
    data class MovieCastRequest(val expression: String) : SearchRequest
    data class MovieDetailsRequest(val expression: String) : SearchRequest
    data class PersonsSearchRequest(val expression: String) : SearchRequest
    data class MoviesSearchRequest(val expression: String) : SearchRequest
}
