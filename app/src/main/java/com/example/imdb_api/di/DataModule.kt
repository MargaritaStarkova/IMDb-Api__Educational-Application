package com.example.imdb_api.di

import android.content.Context
import androidx.room.Room
import com.example.imdb_api.data.NetworkClient
import com.example.imdb_api.data.converts.MovieCastConverter
import com.example.imdb_api.data.converts.MovieDbConvertor
import com.example.imdb_api.data.db.AppDatabase
import com.example.imdb_api.data.network.IMDbApiService
import com.example.imdb_api.data.network.RetrofitNetworkClient
import com.example.imdb_api.data.storage.LocalStorage
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
        androidContext().getSharedPreferences("local_storage", Context.MODE_PRIVATE)
    }
    
    single {
        Room
            .databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .build()
    }
    
    single<LocalStorage> {
        LocalStorage(get())
    }
    
    single<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
    }
    
}