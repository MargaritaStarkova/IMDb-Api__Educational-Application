package com.example.imdb_api.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.imdb_api.data.NetworkClient
import com.example.imdb_api.data.dto.MoviesSearchRequest
import com.example.imdb_api.data.dto.Response
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient(
    private val imdbService: IMDbApiService,
    private val context: Context,
) : NetworkClient {

/*     private val imdbBaseUrl = "https://imdb-api.com"

    private val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private val okHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(imdbBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    private val imdbService = retrofit.create(IMDbApiService::class.java) */

    override fun doRequest(dto: Any): Response {

        return when {

            !isConnected() -> Response().apply { resultCode = -1 }

            dto is MoviesSearchRequest -> {
                val response = imdbService.findMovies(dto.expression).execute()
                val body = response.body() ?: Response()
                body.apply { resultCode = response.code() }
            }

            else -> Response().apply { resultCode = 400 }

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