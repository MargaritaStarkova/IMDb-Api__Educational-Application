package com.example.imdb_api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmApi {
    @GET("/en/API/SearchMovie/k_3ph8h7kw/{expression}")
    fun getFilms(@Path("expression") expression:String): Call<FilmResponse>
}