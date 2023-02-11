package com.example.imdb_api

import androidx.annotation.DrawableRes

class FilmResponse(val results: ArrayList<FilmData>){
    data class Results (
        val id: Int,
        val resultType: Int
        val image: Int,
        val nameFilm: String,
        val description: String
    )
}