package com.example.imdb_api.data.dto.search

import com.example.imdb_api.data.dto.Response

class MoviesSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<MovieDto>?
) : Response() {

}