package com.example.imdb_api.data.dto.cast

import com.example.imdb_api.data.dto.Response

data class MoviesFullCastResponse(
    val actors: List<Actor>,
    val directors: Directors,
    val errorMessage: String,
    val fullTitle: String,
    val imDbId: String,
    val others: List<Other>,
    val title: String,
    val type: String,
    val writers: Writers,
    val year: String
) : Response()

data class Actor(
    val asCharacter: String,
    val id: String,
    val image: String,
    val name: String
)

data class Directors(
    val items: List<Item>,
    val job: String
)

data class Other(
    val items: List<Item>,
    val job: String
)

data class Writers(
    val items: List<Item>,
    val job: String
)

data class Item(
    val description: String,
    val id: String,
    val name: String,
)



