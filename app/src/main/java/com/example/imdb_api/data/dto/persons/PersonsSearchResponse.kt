package com.example.imdb_api.data.dto.persons

import com.example.imdb_api.data.dto.Response
import com.example.imdb_api.domain.models.Person

class PersonsSearchResponse(
    val errorMessage: String,
    val expression: String,
    val results: List<PersonDto>,
    val searchType: String
) : Response()