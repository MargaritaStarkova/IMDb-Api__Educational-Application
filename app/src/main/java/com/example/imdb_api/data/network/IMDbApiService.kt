package com.example.imdb_api.data.network

import com.example.imdb_api.data.dto.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {
    @GET("/en/API/SearchMovie/k_3ph8h7kw/{expression}")
    fun findMovies(@Path("expression") expression:String): Call<MoviesSearchResponse>
}