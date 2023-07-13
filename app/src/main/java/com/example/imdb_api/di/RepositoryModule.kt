package com.example.imdb_api.di

import com.example.imdb_api.data.HistoryRepositoryImpl
import com.example.imdb_api.data.MoviesRepositoryImpl
import com.example.imdb_api.data.converts.MovieCastConverter
import com.example.imdb_api.data.converts.MovieDbConvertor
import com.example.imdb_api.domain.api.MoviesRepository
import com.example.imdb_api.domain.db.HistoryRepository
import org.koin.dsl.module

val repositoryModule = module {
    
    factory<MovieCastConverter> {
        MovieCastConverter()
    }
    
    factory<MovieDbConvertor> {
        MovieDbConvertor()
    }
    
    single<MoviesRepository> {
        MoviesRepositoryImpl(
            networkClient = get(),
            localStorage = get(),
            movieCastConverter = get(),
            appDatabase = get(),
            movieDbConvertor = get(),
        )
    }
    
    single<HistoryRepository> {
        HistoryRepositoryImpl(
            appDatabase = get(), movieDbConvertor = get()
        )
    }
}