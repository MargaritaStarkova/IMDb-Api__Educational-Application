package com.example.imdb_api

import androidx.annotation.DrawableRes

data class FilmData(
    val id: Int,
    @DrawableRes val image: Int,
    val nameFilm: String,
    val description: String
)
