package com.example.imdb_api.data.network

import com.example.imdb_api.data.dto.cast.MoviesFullCastResponse


import com.example.imdb_api.data.dto.details.MovieDetailsResponse
import com.example.imdb_api.data.dto.persons.PersonsSearchResponse
import com.example.imdb_api.data.dto.search.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {
    @GET("/en/API/SearchMovie/k_3ph8h7kw/{expression}")
    fun findMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>
    
    @GET("/en/API/Title/k_3ph8h7kw/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MovieDetailsResponse>
    
    @GET("/en/API/FullCast/k_3ph8h7kw/{movie_id}")
    fun getMovieCast(@Path("movie_id") movieId: String): Call<MoviesFullCastResponse>
    
    @GET("/en/API/SearchName/k_3ph8h7kw/{expression}")
    fun findPersons(@Path("expression") expression: String): Call<PersonsSearchResponse>
}