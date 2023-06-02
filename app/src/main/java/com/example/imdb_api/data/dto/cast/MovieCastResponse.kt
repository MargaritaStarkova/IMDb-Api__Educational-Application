package com.example.imdb_api.data.dto.cast

import com.example.imdb_api.data.dto.Response

data class Directors(
    val job: String,
    val items: List<Director>,
)

data class Director(
    val id: String,
    val name: String,
    val description: String,
)

data class Writers(
    val job: String,
    val items: List<Writer>,
)

data class Writer(
    val id: String,
    val name: String,
    val description: String,
)

data class Actors(
    val job: String,
    val items: List<Actor>,
)

data class Actor(
    val id: String,
    val image: String,
    val name: String,
    val asCharacter: String,
)

data class Others(
    val job: String,
    val items: List<Other>,
)

data class Other(
    val id: String,
    val name: String,
    val description: String,
)

class MovieCastResponse
    (
    val imDbId: String,
    val title: String,
    val fullTitle: String,
    val type: String,
    val year: String,
    val directors: Directors,
    val writers: Writers,
    val actors: Actors,
    val others: Others,
    val errorMessage: String,
) : Response()



