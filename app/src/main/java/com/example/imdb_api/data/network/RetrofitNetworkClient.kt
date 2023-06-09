package com.example.imdb_api.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.imdb_api.data.NetworkClient
import com.example.imdb_api.data.dto.cast.MovieCastRequest
import com.example.imdb_api.data.dto.details.MovieDetailsRequest
import com.example.imdb_api.data.dto.search.MoviesSearchRequest
import com.example.imdb_api.data.dto.Response
import com.example.imdb_api.data.dto.persons.PersonsSearchRequest

class RetrofitNetworkClient(
    private val imdbService: IMDbApiService,
    private val context: Context,
) : NetworkClient {

    override fun doRequest(dto: Any): Response {
    
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        
        val response = when(dto) {
            is MoviesSearchRequest -> imdbService.findMovies(dto.expression).execute()
            is MovieDetailsRequest -> imdbService.getMovieDetails(dto.movieId).execute()
            is MovieCastRequest -> imdbService.getMovieCast(dto.movieId).execute()
            is PersonsSearchRequest -> imdbService.findPersons(dto.expression).execute()
            else -> return Response().apply { resultCode = 400 }
        }
        
        val body = response.body()
        return body?.apply { resultCode = response.code() } ?: Response().apply { resultCode = response.code() }
    
    }
    
    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true

            }
        }
        return false
    }
}