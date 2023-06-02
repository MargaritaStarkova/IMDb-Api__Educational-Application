package com.example.imdb_api.di

import android.content.Context
import android.content.SharedPreferences
import com.example.imdb_api.data.NetworkClient
import com.example.imdb_api.data.network.IMDbApiService
import com.example.imdb_api.data.network.RetrofitNetworkClient
import com.example.imdb_api.data.storage.LocalStorage
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single<IMDbApiService> {
        val imdbBaseUrl = "http://imdb-api.com"
        
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val okHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()
        
        Retrofit.Builder()
                .baseUrl(imdbBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(IMDbApiService::class.java)
    }
    
    single {
        androidContext()
                .getSharedPreferences("local_storage", Context.MODE_PRIVATE )
    }
    
    
    single<LocalStorage> {
        LocalStorage(get())
    }
    
    single<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
    }
}