package com.example.imdb_api.di

import com.example.imdb_api.data.MoviesRepositoryImpl
import com.example.imdb_api.data.NetworkClient
import com.example.imdb_api.data.storage.LocalStorage
import com.example.imdb_api.domain.api.MoviesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MoviesRepository> {
        MoviesRepositoryImpl(
            networkClient = get(),
            localStorage = get(),
            movieCastConverter = get())
    }
}