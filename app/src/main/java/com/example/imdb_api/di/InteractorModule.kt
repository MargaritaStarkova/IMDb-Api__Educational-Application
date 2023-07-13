package com.example.imdb_api.di

import com.example.imdb_api.domain.api.MoviesInteractor
import com.example.imdb_api.domain.api.MoviesRepository
import com.example.imdb_api.domain.db.HistoryInteractor
import com.example.imdb_api.domain.impl.HistoryInteractorImpl
import com.example.imdb_api.domain.impl.MoviesInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    
    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }
    
    single<HistoryInteractor> {
        HistoryInteractorImpl(get())
    }
}