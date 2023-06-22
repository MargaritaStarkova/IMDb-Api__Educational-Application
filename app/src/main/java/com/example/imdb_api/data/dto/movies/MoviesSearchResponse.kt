package com.example.imdb_api.data.dto.movies

import com.example.imdb_api.data.dto.Response

class MoviesSearchResponse(
    val results: List<MovieDto>?
) : Response() {

}