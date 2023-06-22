package com.example.imdb_api.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.imdb_api.data.NetworkClient
import com.example.imdb_api.data.dto.Response
import com.example.imdb_api.domain.models.SearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitNetworkClient(
    private val imdbService: IMDbApiService,
    private val context: Context,
) : NetworkClient {
    
    override suspend fun doRequest(dto: Any): Response {
        
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        return withContext(Dispatchers.IO) {
            
            val response = when (dto) {
                is SearchRequest.MoviesSearchRequest -> imdbService.findMovies(dto.expression)
                is SearchRequest.MovieDetailsRequest -> imdbService.getMovieDetails(dto.expression)
                is SearchRequest.MovieCastRequest -> imdbService.getMovieCast(dto.expression)
                is SearchRequest.PersonsSearchRequest -> imdbService.findPersons(dto.expression)
                else -> return@withContext Response().apply { resultCode = 400 }
            }
            
            try {
                return@withContext response.apply { resultCode = 200 }
            } catch (e: Throwable) {
                return@withContext Response().apply { resultCode = 500 }
            }
        }
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